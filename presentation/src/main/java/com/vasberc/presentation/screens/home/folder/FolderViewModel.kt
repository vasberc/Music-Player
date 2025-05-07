package com.vasberc.presentation.screens.home.folder

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.vasberc.domain.model.FolderModel
import com.vasberc.domain.usecase.GetFilesOfFolderUseCase
import com.vasberc.presentation.navigation.homenavigation.HomeRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class FolderViewModel(
    getFilesOfFolderUseCase: GetFilesOfFolderUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    fun addToPlaylist(index: Int, category: String) {
        _folder.update { prevFolder ->
            prevFolder?.copy(
                files = prevFolder.files.mapIndexed { i, musicModel ->
                    if (i == index) {
                        musicModel.copy(
                            listsAdded = musicModel.listsAdded + category
                        )
                    } else {
                        musicModel
                    }
                }
            )
        }
    }

    fun removeFromPlaylist(index: Int, category: String) {
        _folder.update { prevFolder ->
            prevFolder?.copy(
                files = prevFolder.files.mapIndexed { i, musicModel ->
                    if (i == index) {
                        musicModel.copy(
                            listsAdded = musicModel.listsAdded - category
                        )
                    } else {
                        musicModel
                    }
                }
            )
        }
    }

    val folderPath = savedStateHandle.toRoute<HomeRoute.Folder>().folderPath
    private val _folder = MutableStateFlow<FolderModel?>(null)
    val folder = _folder.onStart {
        if (_folder.value == null) {
            _folder.update { getFilesOfFolderUseCase(folderPath) }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = null
    )
}