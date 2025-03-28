package com.vasberc.domain.model

data class FolderModel(
    val name: String,
    val path: String,
    val files: List<MusicModel>
) {
    val count: Int
        get() = files.size
}