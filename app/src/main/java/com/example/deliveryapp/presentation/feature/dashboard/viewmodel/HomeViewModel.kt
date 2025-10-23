package com.example.deliveryapp.presentation.feature.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.data.local.model.PopularItem
import com.example.deliveryapp.data.mapper.CategoryMapper
import com.example.deliveryapp.domain.repository.MealRepository
import com.example.deliveryapp.presentation.feature.dashboard.intent.HomeIntent
import com.example.deliveryapp.presentation.feature.dashboard.model.FilterType
import com.example.deliveryapp.presentation.feature.dashboard.model.PriceRange
import com.example.deliveryapp.presentation.feature.dashboard.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mealRepository: MealRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        loadPopularItems()
    }

    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadPopularItems -> loadPopularItems()
            is HomeIntent.RefreshItems -> refreshItems()
            is HomeIntent.SearchMeals -> searchMeals(intent.query)
            is HomeIntent.FilterByCategory -> filterByCategory(intent.category)
            is HomeIntent.ApplyFilter -> applyFilter(intent.filter)
        }
    }

    private fun loadPopularItems() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null, selectedCategory = null)

            try {
                // Fetch multiple random meals to populate the popular items
                val results = mutableListOf<PopularItem>()

                // Get 10 random meals by calling the API multiple times
                repeat(10) {
                    mealRepository.getRandomMeals().fold(
                        onSuccess = { meals ->
                            results.addAll(meals)
                        },
                        onFailure = { throwable ->
                            // Log error but continue trying to get other meals
                            println("Error fetching random meal: ${throwable.message}")
                        }
                    )
                }

                // Remove duplicates and take only first 10 items
                val uniqueMeals = results.distinctBy { it.id }.take(10)

                // If we have some meals, show them, otherwise show static data as fallback
                val finalMeals = if (uniqueMeals.isNotEmpty()) {
                    uniqueMeals
                } else {
                    // Fallback to static data if API fails completely
                    PopularItem.getPopularItems()
                }

                _state.value = _state.value.copy(
                    isLoading = false,
                    popularItems = finalMeals,
                    error = null,
                    selectedCategory = null
                )
            } catch (e: Exception) {
                // Fallback to static data on any error
                _state.value = _state.value.copy(
                    isLoading = false,
                    popularItems = PopularItem.getPopularItems(),
                    error = "Failed to load meals from API. Showing local data.",
                    selectedCategory = null
                )
            }
        }
    }

    private fun refreshItems() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isRefreshing = true, error = null)
            loadPopularItems()
            _state.value = _state.value.copy(isRefreshing = false)
        }
    }

    private fun searchMeals(query: String) {
        if (query.isBlank()) {
            loadPopularItems()
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            mealRepository.searchMeals(query).fold(
                onSuccess = { meals ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        popularItems = meals,
                        error = null
                    )
                },
                onFailure = { throwable ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "Failed to search meals: ${throwable.message}"
                    )
                }
            )
        }
    }

    private fun filterByCategory(category: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null, selectedCategory = category)
            val apiCategory = CategoryMapper.mapUIToAPICategory(category)

            mealRepository.getMealsByCategory(apiCategory).fold(
                onSuccess = { meals ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        popularItems = meals,
                        error = null,
                        selectedCategory = category
                    )
                },
                onFailure = { throwable ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "Failed to filter meals: ${throwable.message}",
                        selectedCategory = category
                    )
                }
            )
        }
    }

    private fun applyFilter(filter: FilterType) {
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