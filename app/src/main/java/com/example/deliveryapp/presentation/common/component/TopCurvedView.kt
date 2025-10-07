package com.example.deliveryapp.presentation.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.deliveryapp.ui.theme.CurvedBgColor

@Composable
fun TopCurvedView(
    includeStatusBarPadding: Boolean = false,
    content: @Composable BoxScope.() -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (includeStatusBarPadding) Modifier.statusBarsPadding()
                else Modifier
            )
            .clip(RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp))
            .background(color = CurvedBgColor)
            .padding(horizontal = 10.dp, vertical = 20.dp)
        ,
        contentAlignment = Alignment.Center
    ){
        content()
    }
}