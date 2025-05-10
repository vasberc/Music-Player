package com.vasberc.data_local.repo

import android.content.Context
import android.provider.MediaStore
import com.vasberc.domain.model.FolderModel
import com.vasberc.domain.model.MusicModel
import com.vasberc.domain.repo.MusicFilesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single
import java.io.File


@Single
class MusicFilesRepoImpl(
    private val context: Context
) : MusicFilesRepo {
    private val _musicFileFlow = MutableStateFlow<List<FolderModel>?>(null)
    private val musicFileFlow = _musicFileFlow.asStateFlow()
    override fun getAllMusicFilesFlow(): Flow<List<FolderModel>?> = flow {
        musicFileFlow.collect {
            emit(it)
        }
    }

    override suspend fun refreshAllMusicFiles() {
        coroutineScope {
            launch(Dispatchers.IO) {
                folderFiles.clear()
                foldersPath.clear()
                getMusicFiles(null, false)

                folderFiles.mapNotNull { (folder, files) ->
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
                    _musicFileFlow.value = it
                    foldersPath.clear()
                    folderFiles.clear()
                }
            }
        }
    }

    override fun getFilesOfFolderFlow(folderPath: String): Flow<FolderModel?> = flow {
        if (musicFileFlow.value == null) {
            refreshAllMusicFiles()
        }
        musicFileFlow.collect {
            emit(it?.find { it.path == folderPath })
        }
    }

    private val folderFiles: MutableMap<String, MutableList<MusicModel>> = mutableMapOf()
    private val foldersPath: MutableMap<String, String> = mutableMapOf()

    private fun getMusicFiles(dir: File?, ignoreSubFolders: Boolean) {
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media.DATA,         // File path
            MediaStore.Audio.Media.TITLE,        // Song title
            MediaStore.Audio.Media.ARTIST,       // Artist name
            MediaStore.Audio.Media.ALBUM,        // Album name
            MediaStore.Audio.Media.DURATION,     // Duration (in ms)
            MediaStore.Audio.Media.SIZE          // File size (bytes)
        )

        context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            val dataIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
            val titleIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val albumIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
            val durationIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val sizeIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)
            while (cursor.moveToNext()) {
                val filePath = cursor.getString(dataIndex)
                val title = cursor.getString(titleIndex) ?: "Unknown"
                val artist = cursor.getString(artistIndex) ?: "Unknown Artist"
                val album = cursor.getString(albumIndex) ?: "Unknown Album"
                val duration = cursor.getLong(durationIndex) // Duration in milliseconds
                val size = cursor.getLong(sizeIndex) // File size in bytes
                val file = File(filePath)
                val folderFile = file.parentFile // Get parent folder
                if (ignoreSubFolders && folderFile?.name != dir?.name) {
                    continue
                }

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
                            filePath = file.absolutePath,
                            title = title,
                            artist = artist,
                            album = album,
                            duration = duration,
                            size = size,
                            listsAdded = listOf()//will handled on domain layer
                        )
                    )
                }
            }
        }

    }

}