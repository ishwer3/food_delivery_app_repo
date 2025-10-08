package com.example.deliveryapp.presentation.feature.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.data.local.model.PopularItem
import com.example.deliveryapp.domain.repository.MealRepository
import com.example.deliveryapp.presentation.feature.dashboard.model.FilterType
import com.example.deliveryapp.presentation.feature.dashboard.model.PriceRange
import com.example.deliveryapp.presentation.feature.dashboard.state.FilterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val mealRepository: MealRepository
) : ViewModel() {

    private val _state = MutableStateFlow(FilterState())
    val state: StateFlow<FilterState> = _state

    init {
        loadAllMeals()
    }

    private fun loadAllMeals() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            try {
                // Fetch multiple random meals to populate the list
                val results = mutableListOf<PopularItem>()

                // Get 15 random meals
                repeat(15) {
                    mealRepository.getRandomMeals().fold(
                        onSuccess = { meals ->
                            results.addAll(meals)
                        },
                        onFailure = { throwable ->
                            println("Error fetching random meal: ${throwable.message}")
                        }
                    )
                }

                // Remove duplicates
                val uniqueMeals = results.distinctBy { it.id }

                // If we have meals from API, use them, otherwise use fallback
                val finalMeals = if (uniqueMeals.isNotEmpty()) {
                    uniqueMeals
                } else {
                    PopularItem.getPopularItems()
                }

                _state.value = _state.value.copy(
                    popularItems = finalMeals,
                    allItems = finalMeals, // Store all items for filtering
                    isLoading = false
                )
            } catch (e: Exception) {
                // Fallback to static data
                val staticItems = PopularItem.getPopularItems()
                _state.value = _state.value.copy(
                    popularItems = staticItems,
                    allItems = staticItems,
                    isLoading = false
                )
            }
        }
    }

    fun applyFilter(filter: FilterType) {
        val allItems = _state.value.allItems

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
        val allItems = _state.value.allItems
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