package com.vasberc.presentation.screens.home.folder

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.vasberc.domain.model.FolderModel
import com.vasberc.domain.usecase.GetFilesOfFolderUseCase
import com.vasberc.presentation.navigation.homenavigation.HomeRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class FolderViewModel(
    getFilesOfFolderUseCase: GetFilesOfFolderUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val folderPath = savedStateHandle.toRoute<HomeRoute.Folder>().folderPath
    private val _folder = MutableStateFlow<FolderModel?>(null)
    val folder = _folder.asStateFlow()
    init {
        viewModelScope.launch {
            _folder.update { getFilesOfFolderUseCase(folderPath) }
        }
    }
}