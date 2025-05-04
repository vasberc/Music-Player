package com.vasberc.presentation.componets.listcategoriesdialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasberc.domain.model.MusicModel
import com.vasberc.domain.usecase.AddListedItemUseCase
import com.vasberc.domain.usecase.GetCategoriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ListCategoriesDialogViewModel(
    private val addListedItemUseCase: AddListedItemUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
): ViewModel() {
    fun addCategory(category: String, model: MusicModel?) {
        model?.let {
            viewModelScope.launch {
                addListedItemUseCase(category, it.filePath)
            }
        }
    }

    private val _categories = MutableStateFlow<List<String>?>(null)
    val categories = _categories.onStart {
        _categories.update { getCategoriesUseCase() }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = null
    )
}