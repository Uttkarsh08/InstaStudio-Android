package com.uttkarsh.InstaStudio.presentation.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.uttkarsh.InstaStudio.presentation.navigation.Screens
import com.uttkarsh.InstaStudio.presentation.viewmodel.AuthViewModel
import kotlinx.coroutines.delay
import androidx.compose.runtime.getValue


@Composable
fun SplashScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavController
) {

    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()
    val isRegistered by authViewModel.isRegistered.collectAsState()
    val isOnBoardingShown by authViewModel.isOnBoardingShown.collectAsState()
    val isAuthRefreshed by authViewModel.isAuthRefreshed.collectAsState()

    LaunchedEffect(Unit) {
        authViewModel.refreshAuthState()
    }

    LaunchedEffect(isAuthRefreshed) {
        if (isAuthRefreshed) {
            Log.d("Splash", "LoggedIn: $isLoggedIn, Registered: $isRegistered, OnBoardingShown: $isOnBoardingShown")

            val nextDestination = when {
                !isOnBoardingShown -> Screens.OnBoardingScreen.route
                !isLoggedIn -> Screens.LoginTypeScreen.route
                !isRegistered -> Screens.ProfileCompletionScreen.route
                else -> Screens.DashBoardScreen.route
            }

            navController.navigate(nextDestination) {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }

            authViewModel.resetAuthRefreshFlag()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.Android,
                contentDescription = "Android Logo",
                tint = Color.Green,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("My App", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}
