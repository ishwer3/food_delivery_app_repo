package com.example.deliveryapp.presentation.feature.dashboard.state

import com.example.deliveryapp.data.local.model.PopularItem
import com.example.deliveryapp.presentation.feature.dashboard.model.FilterType

data class HomeState(
    val isLoading: Boolean = false,
    val popularItems: List<PopularItem> = emptyList(),
    val allItems: List<PopularItem> = emptyList(), // Store all items before filtering
    val selectedFilter: FilterType = FilterType.All,
    val error: String? = null,
    val isRefreshing: Boolean = false
)