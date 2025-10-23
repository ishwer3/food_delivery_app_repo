package com.example.deliveryapp.presentation.feature.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import com.example.deliveryapp.data.local.model.CategoryType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CategoryViewModel: ViewModel(){
    private val _selectedCategory = MutableStateFlow<CategoryType?>(null)
    val selectedCategory: StateFlow<CategoryType?> = _selectedCategory.asStateFlow()

    fun selectCategory(categoryType: CategoryType?){
        _selectedCategory.value = categoryType
    }
}