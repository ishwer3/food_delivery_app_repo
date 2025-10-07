package com.example.deliveryapp.presentation.feature.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import com.example.deliveryapp.data.local.model.PopularItem
import com.example.deliveryapp.presentation.feature.dashboard.model.FilterType
import com.example.deliveryapp.presentation.feature.dashboard.model.PriceRange
import com.example.deliveryapp.presentation.feature.dashboard.state.FilterState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FilterViewModel : ViewModel() {

    private val _state = MutableStateFlow(FilterState())
    val state: StateFlow<FilterState> = _state

    fun applyFilter(filter: FilterType) {
        val allItems = PopularItem.getPopularItems()

        val filtered = when (filter) {
            is FilterType.All -> allItems
            is FilterType.Rating -> allItems.filter { it.rating >= filter.minRating }
            is FilterType.Price -> when (filter.range) {
                PriceRange.Under10 -> allItems.filter { it.price < 10 }
                PriceRange.TenToFifteen -> allItems.filter { it.price in 10.0..15.0 }
                PriceRange.Above15 -> allItems.filter { it.price > 15 }
            }
            is FilterType.Diet -> allItems.filter { it.isVegetarian == filter.isVeg }
            is FilterType.Hotel -> allItems.filter { it.hotelName == filter.hotelName }
        }

        // Apply search query if present
        val searchFiltered = if (_state.value.searchQuery.isNotBlank()) {
            filtered.filter { item ->
                item.title.contains(_state.value.searchQuery, ignoreCase = true) ||
                item.hotelName.contains(_state.value.searchQuery, ignoreCase = true)
            }
        } else {
            filtered
        }

        _state.value = _state.value.copy(
            popularItems = searchFiltered,
            selectedFilter = filter
        )
    }

    fun onSearchQueryChange(query: String) {
        _state.value = _state.value.copy(searchQuery = query)

        // Re-apply current filter with new search query
        val allItems = PopularItem.getPopularItems()
        val currentFilter = _state.value.selectedFilter

        val filtered = when (currentFilter) {
            is FilterType.All -> allItems
            is FilterType.Rating -> allItems.filter { it.rating >= currentFilter.minRating }
            is FilterType.Price -> when (currentFilter.range) {
                PriceRange.Under10 -> allItems.filter { it.price < 10 }
                PriceRange.TenToFifteen -> allItems.filter { it.price in 10.0..15.0 }
                PriceRange.Above15 -> allItems.filter { it.price > 15 }
            }
            is FilterType.Diet -> allItems.filter { it.isVegetarian == currentFilter.isVeg }
            is FilterType.Hotel -> allItems.filter { it.hotelName == currentFilter.hotelName }
        }

        // Apply search query
        val searchFiltered = if (query.isNotBlank()) {
            filtered.filter { item ->
                item.title.contains(query, ignoreCase = true) ||
                item.hotelName.contains(query, ignoreCase = true)
            }
        } else {
            filtered
        }

        _state.value = _state.value.copy(popularItems = searchFiltered)
    }
}