package com.vasberc.domain.repo

import com.vasberc.domain.model.FolderModel

interface MusicFilesRepo {
    suspend fun getAllMusicFiles(): List<FolderModel>

    suspend fun getFilesOfFolder(folderPath: String): FolderModel
}