package com.vasberc.data_local.repo

import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import com.vasberc.domain.model.FolderModel
import com.vasberc.domain.model.MusicModel
import com.vasberc.domain.repo.MusicFilesRepo
import org.koin.core.annotation.Factory
import java.io.File
import java.util.Locale


@Factory
class MusicFilesRepoImpl(
    private val context: Context
) : MusicFilesRepo {
    override suspend fun getAllMusicFiles(): List<FolderModel> {

        folderFiles.clear()
        foldersPath.clear()
        getMusicFiles(null, false)

        return folderFiles.mapNotNull { (folder, files) ->
            if (files.isEmpty()) {
                null
            } else {
                FolderModel(
                    name = folder,
                    files = files,
                    path = foldersPath[folder] ?: ""
                )
            }
        }.also {
            foldersPath.clear()
            folderFiles.clear()
        }
    }

    override suspend fun getFilesOfFolder(folderPath: String): FolderModel {
        folderFiles.clear()
        foldersPath.clear()
        val folder = File(folderPath)
        folderFiles[folder.name] = mutableListOf()
        foldersPath[folder.name] = folder.absolutePath
        getMusicFiles(folder, true)
        val filesFound = folderFiles[folder.name]
        return FolderModel(
            name = folder.name,
            path = folder.path,
            files = filesFound ?: listOf()
        ).also {
            folderFiles.clear()
            foldersPath.clear()
        }

    }

    private val folderFiles: MutableMap<String, MutableList<MusicModel>> = mutableMapOf()
    private val foldersPath: MutableMap<String, String> = mutableMapOf()

    private fun getMusicFiles(dir: File?, ignoreSubFolders: Boolean) {
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Audio.Media.DATA)

        context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            val dataIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
            while (cursor.moveToNext()) {
                val filePath = cursor.getString(dataIndex)
                val folderFile = File(filePath).parentFile // Get parent folder
                if (ignoreSubFolders && folderFile?.name != dir?.name) {
                    continue
                }
                val file = File(filePath)
                if (folderFile != null) {
                    val folderPath = folderFile.absolutePath
                    val folderName = folderFile.name
                    if (!folderFiles.containsKey(folderName)) {
                        folderFiles[folderName] = mutableListOf()
                        foldersPath[folderName] = folderPath
                    }
                    folderFiles[folderName]?.add(
                        MusicModel(
                            fileName = file.name,
                            filePath = file.absolutePath
                        )
                    )
                }
            }
        }

    }

}