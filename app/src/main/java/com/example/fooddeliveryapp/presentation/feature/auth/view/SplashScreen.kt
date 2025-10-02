package com.example.fooddeliveryapp.presentation.feature.auth.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.presentation.common.component.ButtonBgFilled
import com.example.fooddeliveryapp.presentation.feature.auth.viewmodel.AuthViewModel
import com.example.fooddeliveryapp.ui.theme.blackStyle
import com.example.fooddeliveryapp.ui.theme.titleLarge
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val authState by viewModel.state.collectAsStateWithLifecycle()
    var showContent by remember { mutableStateOf(false) }

    // Check authentication state on startup
    LaunchedEffect(Unit) {
        viewModel.checkAuthenticationState()
        delay(2000) // Minimum splash display time
        showContent = true
    }

    // Navigate based on auth state once content is ready to show
    LaunchedEffect(showContent, authState.isLoggedIn) {
        if (showContent) {
            if (authState.isLoggedIn) {
                onNavigateToHome()
            }
        }
    }

    if (!showContent) {
        // Loading splash screen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "DELIVERY FOOD",
                fontWeight = FontWeight.Bold,
                style = titleLarge.copy(fontSize = 40.sp, color = Color.Black)
            )

            CircularProgressIndicator(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .size(32.dp),
                color = Color(0xFFFF9800)
            )
        }
    } else if (!authState.isLoggedIn) {
        // Show welcome screen for new users
        WelcomeContent(onGetStartedClick = onNavigateToLogin)
    }
}

@Composable
private fun WelcomeContent(onGetStartedClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 90.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "DELIVERY FOOD",
            fontWeight = FontWeight.Bold,
            style = titleLarge.copy(fontSize = 40.sp, color = Color.Black)
        )
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Image(
                modifier = Modifier.fillMaxWidth().weight(1f).padding(vertical = 20.dp),
                painter = painterResource(R.drawable.pizza),
                contentDescription = "Pizza Slice Image"
            )
            //description
            Text(
                text = stringResource(R.string.splash_description),
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                style = blackStyle.copy(fontSize = 16.sp)
            )
            ButtonBgFilled(
                text = "GET STARTED",
                onClick = onGetStartedClick,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Lorem ipsum",
                style = titleLarge.copy(color = Color.Black, fontWeight = FontWeight.Bold)
            )
        }
    }
}