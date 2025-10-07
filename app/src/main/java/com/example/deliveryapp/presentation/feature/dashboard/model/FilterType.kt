package com.example.deliveryapp.presentation.feature.dashboard.model

sealed class FilterType {
    object All : FilterType()
    data class Rating(val minRating: Float) : FilterType()
    data class Price(val range: PriceRange) : FilterType()
    data class Diet(val isVeg: Boolean) : FilterType()
    data class Hotel(val hotelName: String) : FilterType()
}

sealed class PriceRange {
    object Under10 : PriceRange()
    object TenToFifteen : PriceRange()
    object Above15 : PriceRange()
}