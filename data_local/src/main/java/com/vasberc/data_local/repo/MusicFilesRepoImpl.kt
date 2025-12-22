package com.vasberc.data_local.repo

import android.content.Context
import android.provider.MediaStore
import com.vasberc.data_local.dao.ListedItemDao
import com.vasberc.domain.model.FolderModel
import com.vasberc.domain.model.MusicModel
import com.vasberc.domain.repo.MusicFilesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single
import java.io.File


@Single
class MusicFilesRepoImpl(
    private val context: Context,
    listedItemDao: ListedItemDao,
) : MusicFilesRepo {

    private val _musicFileFlow = MutableStateFlow<List<FolderModel>?>(null)
    override val allMusicFilesFlow: Flow<List<FolderModel>?> = combine(
        _musicFileFlow,
        listedItemDao.getListedItems()
        ) { folders, list ->
        when {
            folders == null -> {
                // if folders are null, refresh and return null for now
                refreshAllMusicFiles()
                null
            }

            else -> {
                // map the lists to the music files
                folders.map { folderModel ->
                    folderModel.copy(
                        files = folderModel.files.map { file ->
                            // get all lists where this file is added
                            val listsAdded = list.filter { it.itemPath == file.filePath }.map { it.list }
                            file.copy(
                                listsAdded = listsAdded
                            )
                        }
                    )
                }
            }
        }
    }.flowOn(Dispatchers.Default)

    override suspend fun refreshAllMusicFiles() {
        coroutineScope {
            launch(Dispatchers.IO) {
                val folderFiles: MutableMap<String, MutableList<MusicModel>> = mutableMapOf()
                val foldersPath: MutableMap<String, String> = mutableMapOf()
                getMusicFiles(folderFiles, foldersPath)

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
                }
            }
        }
    }

    override fun getFilesOfFolderFlow(folderPath: String): Flow<FolderModel?> = allMusicFilesFlow.map {
        it?.find { folderModel -> folderModel.path == folderPath }
    }

    override fun getFilesOfListFlow(listName: String): Flow<FolderModel> = allMusicFilesFlow.map {
        val list = mutableListOf<MusicModel>()
        it?.forEach { folderModel ->
            folderModel.files.forEach { musicModel ->
                if (musicModel.listsAdded.contains(listName)) {
                    list.add(musicModel)
                }
            }
        }
        FolderModel(name = listName, path = "", files = list.toList())

    }.flowOn(Dispatchers.Default)

    private fun getMusicFiles(
        folderFiles: MutableMap<String, MutableList<MusicModel>>,
        foldersPath: MutableMap<String, String>
    ) {
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