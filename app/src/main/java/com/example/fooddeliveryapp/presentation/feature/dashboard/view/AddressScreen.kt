package com.example.fooddeliveryapp.presentation.feature.dashboard.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fooddeliveryapp.presentation.common.component.TopCurvedView
import com.example.fooddeliveryapp.ui.spacer.HorizontalSpacer
import com.example.fooddeliveryapp.ui.spacer.VerticalSpacer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressScreen(
    onBackClick: () -> Unit = {},
    onAddressSelected: (String) -> Unit = {}
) {
    var searchText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    // Mock address suggestions (in a real app, these would come from a location API)
    val addressSuggestions = remember {
        listOf(
            "123 Main Street, Downtown, City 12345",
            "456 Oak Avenue, Residential Area, City 12346",
            "789 Pine Road, Business District, City 12347",
            "321 Elm Street, Shopping Center, City 12348",
            "654 Maple Drive, Suburb Area, City 12349",
            "987 Cedar Lane, University District, City 12350"
        )
    }

    // Filter suggestions based on search text
    val filteredSuggestions = if (searchText.isBlank()) {
        addressSuggestions
    } else {
        addressSuggestions.filter {
            it.lowercase().contains(searchText.lowercase())
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        // Header with orange background
        TopCurvedView{
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            )
            {
                VerticalSpacer(10.dp)
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { onBackClick() }
                )

                VerticalSpacer(16.dp)

                Row(
                    modifier = Modifier.fillMaxWidth().height(90.dp).padding(horizontal = 10.dp)
                ) {
                    // Left side - Dots with connecting line
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxHeight().padding(end = 12.dp)
                    ) {
                        // First dot
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .background(
                                    color = Color.White,
                                    shape = CircleShape
                                )
                        )

                        VerticalSpacer(5.dp)

                        repeat(5) {
                            Spacer(modifier = Modifier.height(3.dp))
                            Box(
                                modifier = Modifier
                                    .width(2.dp)
                                    .height(6.dp)
                                    .background(Color.White.copy(alpha = 0.6f))
                            )
                        }

                        VerticalSpacer(5.dp)

                        // Second dot
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .background(
                                    color = Color.White,
                                    shape = CircleShape
                                )
                        )
                    }

                    // Right side - Location fields with divider
                    Column(
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // First location field
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Location",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )

                            HorizontalSpacer(8.dp)

                            BasicTextField(
                                value = "",
                                onValueChange = {},
                                modifier = Modifier.weight(1f),
                                textStyle = TextStyle(
                                    color = Color.White,
                                    fontSize = 16.sp
                                ),
                                decorationBox = { innerTextField ->
                                    if (true) {
                                        Text(
                                            text = "Lorem ipsum dolor sit",
                                            color = Color.White,
                                            fontSize = 16.sp
                                        )
                                    }
                                    innerTextField()
                                }
                            )
                        }

                        // Horizontal divider
                        HorizontalDivider(color = Color.White, thickness = 1.dp)

                        // Second location field
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Location",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )

                            HorizontalSpacer(8.dp)
                            BasicTextField(
                                value = "",
                                onValueChange = {},
                                modifier = Modifier.weight(1f),
                                textStyle = TextStyle(
                                    color = Color.White,
                                    fontSize = 16.sp
                                ),
                                decorationBox = { innerTextField ->
                                    if (true) {
                                        Text(
                                            text = "Lorem ipsum dolor sit",
                                            color = Color.White,
                                            fontSize = 16.sp
                                        )
                                    }
                                    innerTextField()
                                }
                            )
                        }
                    }
                }
            }

            /*Column(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
            )
            {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp).clickable{ onBackClick() }
                )
                Row {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(
                                    color = Color.White,
                                    shape = CircleShape
                                )
                        )

                        repeat(6) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .width(2.dp)
                                    .height(6.dp)
                                    .background(Color.White.copy(alpha = 0.5f))
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(
                                    color = Color.White,
                                    shape = CircleShape
                                )
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        // transparent field

                        HorizontalDivider(color = Color.White)

                        // transparent field
                    }
                }
            }*/
        }

        // Content area
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Address suggestions
            if (filteredSuggestions.isNotEmpty()) {
                Text(
                    text = "Suggestions",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredSuggestions) { address ->
                        AddressSuggestionCard(
                            address = address,
                            onClick = {
                                onAddressSelected(address)
                            }
                        )
                    }
                }
            } else if (searchText.isNotBlank()) {
                // No results found
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No addresses found",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun AddressSuggestionCard(
    address: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location",
                tint = Color(0xFFFF9800),
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.size(12.dp))

            Text(
                text = address,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun CommonRow(){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(
                    color = Color.White,
                    shape = CircleShape
                )
        )

        HorizontalSpacer(20.dp)

        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Location",
            tint = Color.White,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        BasicTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.weight(1f),
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 14.sp
            ),
            decorationBox = { innerTextField ->
                if (true) {
                    Text(
                        text = "Lorem ipsum dolor sit",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
                innerTextField()
            }
        )
    }
}