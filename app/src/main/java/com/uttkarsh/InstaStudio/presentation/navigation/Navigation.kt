package com.uttkarsh.InstaStudio.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uttkarsh.InstaStudio.presentation.ui.LogOutScreen
import com.uttkarsh.InstaStudio.presentation.ui.LoginTypeScreen
import com.uttkarsh.InstaStudio.presentation.ui.OnBoardingScreen
import com.uttkarsh.InstaStudio.presentation.ui.SignInScreen
import com.uttkarsh.InstaStudio.presentation.viewmodel.AuthViewModel

@Composable
fun Navigation(
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    var startDestination by remember { mutableStateOf<String>(Screens.OnBoardingScreen.route) }

    LaunchedEffect(Unit) {
        val shown = viewModel.isOnboardingShown()
        val loggedIn = viewModel.isUserLoggedIn()

        startDestination = when {
            !shown -> Screens.OnBoardingScreen.route
            !loggedIn -> Screens.LoginTypeScreen.route
            else -> Screens.LogOutScreen.route
        }
    }

    NavHost(navController = navController,
        startDestination = startDestination
    ){

        composable(Screens.OnBoardingScreen.route) {
            OnBoardingScreen(viewModel, navController)
        }
        composable(Screens.LoginTypeScreen.route) {
            LoginTypeScreen(viewModel, navController)
        }
        composable(Screens.SignInScreen.route) {
            SignInScreen(viewModel, navController)
        }
        composable(Screens.LogOutScreen.route) {
            LogOutScreen(viewModel, navController)
        }

    }
}