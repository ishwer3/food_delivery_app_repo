package com.example.fooddeliveryapp.presentation.feature.dashboard.intent

import com.example.fooddeliveryapp.presentation.feature.dashboard.model.FilterType

sealed class HomeIntent {
    object LoadPopularItems : HomeIntent()
    object RefreshItems : HomeIntent()
    data class SearchMeals(val query: String) : HomeIntent()
    data class FilterByCategory(val category: String) : HomeIntent()
    data class ApplyFilter(val filter: FilterType) : HomeIntent()
}