package com.example.fooddeliveryapp.presentation.feature.dashboard.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.fooddeliveryapp.data.local.model.CategoryModel
import com.example.fooddeliveryapp.data.local.model.CategoryModel.Companion.getSampleCategories
import com.example.fooddeliveryapp.data.local.model.PopularItem
import com.example.fooddeliveryapp.presentation.common.component.FoodBottomSheet
import com.example.fooddeliveryapp.presentation.common.component.SearchBarField
import com.example.fooddeliveryapp.presentation.common.component.TopCurvedView
import com.example.fooddeliveryapp.presentation.feature.dashboard.intent.HomeIntent
import com.example.fooddeliveryapp.presentation.feature.dashboard.viewmodel.HomeViewModel
import com.example.fooddeliveryapp.presentation.state.CartViewModel
import com.example.fooddeliveryapp.ui.theme.blackStyle
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    cartViewModel: CartViewModel,
    onFoodItemClick: (String) -> Unit = {},
    onCategoryClick: (String) -> Unit = {},
    onSearchClick: () -> Unit = {},
    onSeeAllClick: () -> Unit = {},
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val sheetState = rememberModalBottomSheetState()  // Allow full expansion
    val coroutineScope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf<PopularItem?>(null) }

    var searchText by remember { mutableStateOf("") }

    // Collect state from HomeViewModel
    val homeState by homeViewModel.state.collectAsState()

    // Handle search functionality
    val handleSearch = {
        if (searchText.isNotBlank()) {
            homeViewModel.handleIntent(HomeIntent.SearchMeals(searchText))
        } else {
            homeViewModel.handleIntent(HomeIntent.LoadPopularItems)
        }
    }

    selectedItem?.let { item ->
        FoodBottomSheet(
            item = item,
            sheetState = sheetState,
            onDismiss = { selectedItem = null },
            onAddToCart = { popularItem, quantity ->
                cartViewModel.addToCart(popularItem, quantity)
            }
        )
    }

    // Remember scroll state for collapsing toolbar effect
    val listState = rememberLazyListState()

    // Check if the header is scrolled past
    val isScrolled by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 200
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        // Main scrollable content
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState
        ) {
            // Header with curved background
            item {
                TopCurvedView {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 50.dp, bottom = 20.dp)
                    ) {
                        Text(
                            text = "Good Morning 👋",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                        )
                        Text(
                            text = "What would you like to eat?",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        SearchBarField(
                            value = searchText,
                            onValueChange = { searchText = it },
                            hint = "Search for meals...",
                            onSearchClick = handleSearch,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .padding(top = 8.dp)
                        )
                    }
                }
            }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Categories", style = blackStyle)
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "See All",
                        tint = Color.Black,
                        modifier = Modifier.clickable(onClick = {
                            onSeeAllClick()
                        })
                    )
                }

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(getSampleCategories()) { item ->
                        CategoryItem(item) { categoryItem ->
                            // Filter meals by category when category is clicked
                            homeViewModel.handleIntent(HomeIntent.FilterByCategory(categoryItem.title))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Popular items",
                    style = blackStyle,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }
        }

        // Show loading indicator
        if (homeState.isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFFFF9800),
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }

        // Show error message if any
        homeState.error?.let { error ->
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFEBEE)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = error,
                        color = Color(0xFFD32F2F),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        // Show popular items from API or fallback data
        val popularItems = homeState.popularItems
        if (popularItems.isNotEmpty()) {
            items(popularItems.chunked(2)) { rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowItems.forEach { item ->
                        PopularItemCard(
                            item = item,
                            onItemClick = {
                                selectedItem = item
                                coroutineScope.launch {
                                    sheetState.show()
                                }
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        } else if (!homeState.isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No meals found",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            }
        }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        // Sticky SearchBarField that appears when scrolled
        if (isScrolled) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFF9800))
                    .statusBarsPadding()
                    .align(Alignment.TopCenter)
            ) {
                SearchBarField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    hint = "Search for meals...",
                    onSearchClick = handleSearch,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun CategoryItem(item: CategoryModel, onItemClick: (item: CategoryModel) -> Unit) {
    Card(
        modifier = Modifier
            .size(100.dp)
            .clickable { onItemClick(item) },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF1D431)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = item.title,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                maxLines = 1
            )
        }
    }
}

@Composable
fun PopularItemCard(
    item: PopularItem,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Image from URL or fallback to local resource
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(
                        color = Color(0xFFF5F5F5),
                        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (item.imageUrl != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item.imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = item.title,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = Color(0xFFF5F5F5),
                                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                            ),
                        contentScale = ContentScale.Crop,
                        onError = {
                            // Fallback to icon if image fails to load
                        }
                    )
                } else {
                    Icon(
                        painter = painterResource(id = item.imageRes),
                        contentDescription = item.title,
                        modifier = Modifier.size(50.dp),
                        tint = Color.Gray
                    )
                }
            }

            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Title
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // Description
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 12.sp
                )

                // Hotel name and distance
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.hotelName,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF666666),
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Distance",
                            modifier = Modifier.size(12.dp),
                            tint = Color(0xFF666666)
                        )
                        Text(
                            text = item.distance,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF666666),
                            fontSize = 11.sp
                        )
                    }
                }

                // Rating and Price
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            modifier = Modifier.size(14.dp),
                            tint = Color(0xFFFFB300)
                        )
                        Text(
                            text = item.rating.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            fontSize = 12.sp
                        )

                        if (item.isVegetarian) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .background(
                                        color = Color(0xFF4CAF50),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "V",
                                    color = Color.White,
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Text(
                        text = "$${item.price}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF9800),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}