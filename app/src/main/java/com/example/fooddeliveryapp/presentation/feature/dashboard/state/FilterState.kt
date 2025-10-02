package com.example.fooddeliveryapp.presentation.feature.dashboard.state

import com.example.fooddeliveryapp.data.local.model.PopularItem
import com.example.fooddeliveryapp.presentation.feature.dashboard.model.FilterType

data class FilterState(
    val popularItems: List<PopularItem> = PopularItem.getPopularItems(),
    val selectedFilter: FilterType = FilterType.All
)