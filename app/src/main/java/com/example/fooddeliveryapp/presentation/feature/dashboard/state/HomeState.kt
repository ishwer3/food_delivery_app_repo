package com.example.fooddeliveryapp.presentation.feature.dashboard.state

import com.example.fooddeliveryapp.data.local.model.PopularItem

data class HomeState(
    val isLoading: Boolean = false,
    val popularItems: List<PopularItem> = emptyList(),
    val error: String? = null,
    val isRefreshing: Boolean = false
)