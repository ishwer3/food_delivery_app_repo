package com.example.fooddeliveryapp.presentation.feature.dashboard.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fooddeliveryapp.data.local.model.PopularItem
import com.example.fooddeliveryapp.presentation.common.component.TopCurvedView
import com.example.fooddeliveryapp.presentation.feature.dashboard.model.FilterType
import com.example.fooddeliveryapp.presentation.feature.dashboard.model.PriceRange
import com.example.fooddeliveryapp.presentation.feature.dashboard.state.FilterState
import com.example.fooddeliveryapp.presentation.feature.dashboard.viewmodel.FilterViewModel
import com.example.fooddeliveryapp.ui.spacer.VerticalSpacer

@Composable
fun FilterScreen(
    filterViewModel: FilterViewModel = viewModel()
) {
    val state by filterViewModel.state.collectAsState()
    val hotelNames = PopularItem.getPopularItems().map { it.hotelName }.distinct()

    Column(modifier = Modifier.fillMaxSize()) {
        TopCurvedView {
            Box(
                modifier = Modifier.height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Filters",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        VerticalSpacer(10.dp)

        // Filter Tabs
        FilterTabsRow(
            state = state,
            onFilterClick = { filter -> filterViewModel.applyFilter(filter) },
            hotelNames = hotelNames
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(state.popularItems){  item ->
                PopularItemCard(
                    item = item,
                    onItemClick = {

                    }
                )
            }
        }


    }

}

@Composable
fun FilterTabsRow(
    state: FilterState,
    onFilterClick: (FilterType) -> Unit,
    hotelNames: List<String>
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        // Rating filters
        item { FilterChip("All", state.selectedFilter is FilterType.All) { onFilterClick(FilterType.All) } }
        item { FilterChip("4+⭐", state.selectedFilter == FilterType.Rating(4.0f)) { onFilterClick(FilterType.Rating(4.0f)) } }
        item { FilterChip("4.5+⭐", state.selectedFilter == FilterType.Rating(4.5f)) { onFilterClick(FilterType.Rating(4.5f)) } }

        // Price filters
        item { FilterChip("Under $10", state.selectedFilter == FilterType.Price(PriceRange.Under10)) { onFilterClick(FilterType.Price(PriceRange.Under10)) } }
        item { FilterChip("$10-$15", state.selectedFilter == FilterType.Price(PriceRange.TenToFifteen)) { onFilterClick(FilterType.Price(PriceRange.TenToFifteen)) } }
        item { FilterChip("Above $15", state.selectedFilter == FilterType.Price(PriceRange.Above15)) { onFilterClick(FilterType.Price(PriceRange.Above15)) } }

        // Diet filters
        item { FilterChip("Veg 🌱", state.selectedFilter == FilterType.Diet(true)) { onFilterClick(FilterType.Diet(true)) } }
        item { FilterChip("Non-Veg 🍗", state.selectedFilter == FilterType.Diet(false)) { onFilterClick(FilterType.Diet(false)) } }

        // Hotel filters
        item { FilterChip("All Restaurants", state.selectedFilter is FilterType.All) { onFilterClick(FilterType.All) } }
        /*items(hotelNames) { hotel ->
            FilterChip(
                text = hotel,
                isSelected = state.selectedFilter == FilterType.Hotel(hotel)
            ) { onFilterClick(FilterType.Hotel(hotel)) }
        }*/
    }
}

@Composable
fun FilterChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        shape = CircleShape,
        color = if (isSelected) Color(0xFFFF9800) else Color(0xFFF1D431),
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            color = Color.White,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}