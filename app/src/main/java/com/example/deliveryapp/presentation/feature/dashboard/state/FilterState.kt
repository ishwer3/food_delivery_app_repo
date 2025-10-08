package com.example.deliveryapp.presentation.feature.dashboard.state

import com.example.deliveryapp.data.local.model.PopularItem
import com.example.deliveryapp.presentation.feature.dashboard.model.FilterType

data class FilterState(
    val popularItems: List<PopularItem> = emptyList(),
    val allItems: List<PopularItem> = emptyList(), // Store all items for filtering
    val selectedFilter: FilterType = FilterType.All,
    val searchQuery: String = "",
    val isLoading: Boolean = false
)