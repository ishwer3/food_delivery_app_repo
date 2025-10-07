package com.example.deliveryapp.presentation.feature.dashboard.state

import com.example.deliveryapp.data.local.model.PopularItem
import com.example.deliveryapp.presentation.feature.dashboard.model.FilterType

data class FilterState(
    val popularItems: List<PopularItem> = PopularItem.getPopularItems(),
    val selectedFilter: FilterType = FilterType.All,
    val searchQuery: String = ""
)