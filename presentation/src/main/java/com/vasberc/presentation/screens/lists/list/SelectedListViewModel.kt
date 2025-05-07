package com.vasberc.presentation.screens.lists.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.vasberc.domain.model.FolderModel
import com.vasberc.domain.usecase.GetFilesOfListUseCase
import com.vasberc.presentation.navigation.listsnavigation.ListsRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SelectedListViewModel(
    private val getFilesOfListUseCase: GetFilesOfListUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val listName = savedStateHandle.toRoute<ListsRoute.List>().listName
    private val _folder = MutableStateFlow<FolderModel?>(null)
    val folder = _folder.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = null
    )

    fun loadList() {
        viewModelScope.launch {
            _folder.update { getFilesOfListUseCase(listName) }
        }
    }

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
        if (category == listName) {
            _folder.update {
                it?.copy(
                    files = it.files.filterIndexed { i, musicModel ->
                        i != index
                    }
                )
            }
        } else {
            _folder.update {
                it?.copy(
                    files = it.files.mapIndexed { i, musicModel ->
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
    }
}