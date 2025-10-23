package com.example.deliveryapp.presentation.feature.dashboard.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.deliveryapp.data.local.model.CategoryModel
import com.example.deliveryapp.data.local.model.CategoryModel.Companion.getSampleCategories
import com.example.deliveryapp.presentation.common.component.TopCurvedView
import com.example.deliveryapp.presentation.feature.dashboard.intent.HomeIntent
import com.example.deliveryapp.presentation.feature.dashboard.viewmodel.HomeViewModel
import com.example.deliveryapp.presentation.feature.dashboard.viewmodel.CategoryViewModel
import com.example.deliveryapp.ui.spacer.VerticalSpacer
import com.example.deliveryapp.ui.theme.categoryHeadlineStyle
import com.example.deliveryapp.ui.theme.categoryTitleStyle
import com.example.deliveryapp.ui.theme.categoryBodyStyle

@Composable
fun CategoryScreen(
    categoryViewModel: CategoryViewModel,
    homeViewModel: HomeViewModel
) {
    val homeState by homeViewModel.state.collectAsState()

    // Collect selected category from CategoryViewModel
    val selectedCategory by categoryViewModel.selectedCategory.collectAsState()

    // Reorder categories to show selected first
    val categories = getSampleCategories()
    val reorderedCategories = selectedCategory?.let { selected ->
        val selectedItem = categories.find { it.categoryType == selected }
        if (selectedItem != null) {
            listOf(selectedItem) + categories.filter { it.categoryType != selected }
        } else {
            categories
        }
    } ?: categories

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopCurvedView {
            Text("Categories", style = categoryHeadlineStyle)
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(
                top = 20.dp,
                bottom = 80.dp
            )
        ) {
            items(reorderedCategories) { item ->
                CategoryCard(
                    item = item,
                    isSelected = item.categoryType == selectedCategory
                ) { category ->
                    // Toggle selection
                    if (selectedCategory == category.categoryType) {
                        // Deselect - reset to show all items
                        categoryViewModel.selectCategory(null)
                        homeViewModel.handleIntent(HomeIntent.LoadPopularItems)
                    } else {
                        // Select - filter by category
                        categoryViewModel.selectCategory(category.categoryType)
                        homeViewModel.handleIntent(HomeIntent.FilterByCategory(category.title))
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryCard(
    item: CategoryModel,
    isSelected: Boolean = false,
    onItemClick: (item: CategoryModel) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(if (isSelected) Color(0xFFFF9800) else Color(0xFFF1D431))
            .height(100.dp)
            .padding(10.dp)
            .clickable(onClick = {
                onItemClick(item)
            }),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            painter = painterResource(item.icon),
            contentDescription = item.title,
            modifier = Modifier
                .fillMaxHeight()
                .width(80.dp),
            tint = Color.White
        )

        Column(
            modifier = Modifier.weight(1f).padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = item.title,
                style = categoryTitleStyle.copy(
                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold
                )
            )

            VerticalSpacer(10.dp)
            Text(
                text = item.description,
                style = categoryBodyStyle
            )
        }

        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = "Down Arrow",
            modifier = Modifier.size(25.dp),
            tint = Color.White
        )
    }

}