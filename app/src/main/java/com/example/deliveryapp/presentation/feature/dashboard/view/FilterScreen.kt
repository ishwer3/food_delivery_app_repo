package com.example.deliveryapp.presentation.feature.dashboard.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.deliveryapp.data.local.model.PopularItem
import com.example.deliveryapp.presentation.common.component.TopCurvedView
import com.example.deliveryapp.presentation.feature.dashboard.model.FilterType
import com.example.deliveryapp.presentation.feature.dashboard.model.PriceRange
import com.example.deliveryapp.presentation.feature.dashboard.state.FilterState
import com.example.deliveryapp.presentation.feature.dashboard.viewmodel.FilterViewModel
import com.example.deliveryapp.ui.spacer.VerticalSpacer

@Composable
fun SearchScreen(
    filterViewModel: FilterViewModel = hiltViewModel()
) {
    val state by filterViewModel.state.collectAsState()
    val hotelNames = state.allItems.map { it.hotelName }.distinct()

    Column(modifier = Modifier.fillMaxSize()) {
        TopCurvedView {
            Box(
                modifier = Modifier.height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Search & Filter",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        VerticalSpacer(10.dp)

        // Search Bar
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = { filterViewModel.onSearchQueryChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            placeholder = { Text("Search food or restaurant...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFFF9800),
                unfocusedBorderColor = Color.Gray
            ),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        VerticalSpacer(10.dp)

        // Filter Tabs
        FilterTabsRow(
            state = state,
            onFilterClick = { filter -> filterViewModel.applyFilter(filter) },
            hotelNames = hotelNames
        )

        // Loading and content
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp),
                    color = Color(0xFFFF9800)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(state.popularItems) { item ->
                    PopularItemCard(
                        item = item,
                        onItemClick = {
                            // Navigate to details
                        },
                        onOrderNowClick = {
                            // Open order bottom sheet
                        }
                    )
                }
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