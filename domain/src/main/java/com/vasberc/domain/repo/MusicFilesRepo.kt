package com.vasberc.domain.repo

import com.vasberc.domain.model.FolderModel
import kotlinx.coroutines.flow.Flow

interface MusicFilesRepo {
    val allMusicFilesFlow: Flow<List<FolderModel>?>
    suspend fun refreshAllMusicFiles()
    fun getFilesOfFolderFlow(folderPath: String): Flow<FolderModel?>
    fun getFilesOfListFlow(listName: String): Flow<FolderModel>
}