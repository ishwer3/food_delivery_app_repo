package com.example.fooddeliveryapp.presentation.feature.dashboard.intent

sealed class HomeIntent {
    object LoadPopularItems : HomeIntent()
    object RefreshItems : HomeIntent()
    data class SearchMeals(val query: String) : HomeIntent()
    data class FilterByCategory(val category: String) : HomeIntent()
}