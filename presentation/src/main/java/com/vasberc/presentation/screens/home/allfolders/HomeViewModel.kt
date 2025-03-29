package com.vasberc.presentation.screens.home.allfolders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasberc.domain.model.FolderModel
import com.vasberc.domain.usecase.GetAllMusicFilesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val getAllMusicFilesUseCase: GetAllMusicFilesUseCase
): ViewModel() {
    private val _files = MutableStateFlow<List<FolderModel>?>(null)
    val files = _files.asStateFlow()

    fun getFiles() {
        viewModelScope.launch {
            _files.update { getAllMusicFilesUseCase() }
        }
    }

    fun refreshFolders() {
        _files.update { null }
        getFiles()
    }
}