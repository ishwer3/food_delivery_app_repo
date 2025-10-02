package com.example.fooddeliveryapp.presentation.feature.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import com.example.fooddeliveryapp.data.local.model.PopularItem
import com.example.fooddeliveryapp.presentation.feature.dashboard.model.FilterType
import com.example.fooddeliveryapp.presentation.feature.dashboard.model.PriceRange
import com.example.fooddeliveryapp.presentation.feature.dashboard.state.FilterState
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

        _state.value = _state.value.copy(
            popularItems = filtered,
            selectedFilter = filter
        )
    }
}