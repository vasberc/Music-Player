package com.vasberc.domain.repo

import com.vasberc.domain.model.FolderModel
import kotlinx.coroutines.flow.Flow

interface MusicFilesRepo {
    fun getAllMusicFilesFlow(): Flow<List<FolderModel>?>
    suspend fun refreshAllMusicFiles()
    fun getFilesOfFolderFlow(folderPath: String): Flow<FolderModel?>
}