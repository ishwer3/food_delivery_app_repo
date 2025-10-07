package com.example.deliveryapp.presentation.feature.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.data.local.model.PopularItem
import com.example.deliveryapp.domain.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FoodDetailsState(
    val foodItem: PopularItem? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class FoodDetailsViewModel @Inject constructor(
    private val mealRepository: MealRepository
) : ViewModel() {

    private val _state = MutableStateFlow(FoodDetailsState())
    val state: StateFlow<FoodDetailsState> = _state.asStateFlow()

    fun loadFoodDetails(foodId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            try {
                // Try to get from API first
                val result = mealRepository.getMealById(foodId)
                result.onSuccess { meal ->
                    _state.value = _state.value.copy(
                        foodItem = meal,
                        isLoading = false,
                        error = null
                    )
                }.onFailure { exception ->
                    // Fallback to local data
                    val localItem = PopularItem.getPopularItems().find { it.id == foodId }
                    _state.value = _state.value.copy(
                        foodItem = localItem,
                        isLoading = false,
                        error = if (localItem == null) exception.message else null
                    )
                }
            } catch (e: Exception) {
                // Fallback to local data
                val localItem = PopularItem.getPopularItems().find { it.id == foodId }
                _state.value = _state.value.copy(
                    foodItem = localItem,
                    isLoading = false,
                    error = if (localItem == null) e.message else null
                )
            }
        }
    }
}