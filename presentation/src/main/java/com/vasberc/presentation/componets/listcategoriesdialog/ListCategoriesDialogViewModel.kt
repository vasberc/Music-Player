package com.vasberc.presentation.componets.listcategoriesdialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasberc.domain.model.MusicModel
import com.vasberc.domain.usecase.AddListedItemUseCase
import com.vasberc.domain.usecase.GetCategoriesUseCase
import com.vasberc.domain.usecase.RemoveListedItemUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ListCategoriesDialogViewModel(
    private val addListedItemUseCase: AddListedItemUseCase,
    getCategoriesUseCase: GetCategoriesUseCase,
    private val removeListedItemUseCase: RemoveListedItemUseCase
): ViewModel() {
    fun addCategory(category: String, model: MusicModel?) {
        model?.let {
            viewModelScope.launch {
                addListedItemUseCase(category, it.filePath)
            }
        }
    }

    fun removeCategory(category: String, model: MusicModel?) {
        model?.let {
            viewModelScope.launch {
                removeListedItemUseCase(category, it.filePath)
            }
        }
    }

    private val _categories = getCategoriesUseCase()
    val categories = _categories.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = null
    )
}