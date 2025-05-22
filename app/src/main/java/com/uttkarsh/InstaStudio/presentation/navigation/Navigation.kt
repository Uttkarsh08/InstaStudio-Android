package com.uttkarsh.InstaStudio.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uttkarsh.InstaStudio.presentation.ui.DashBoardScreen
import com.uttkarsh.InstaStudio.presentation.ui.LogOutScreen
import com.uttkarsh.InstaStudio.presentation.ui.LoginTypeScreen
import com.uttkarsh.InstaStudio.presentation.ui.OnBoardingScreen
import com.uttkarsh.InstaStudio.presentation.ui.ProfileCompletionScreen
import com.uttkarsh.InstaStudio.presentation.ui.SignInScreen
import com.uttkarsh.InstaStudio.presentation.viewmodel.AuthViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.ProfileViewModel
import com.uttkarsh.InstaStudio.utils.SharedPref.TokenStore

@Composable
fun Navigation(
    authViewModel: AuthViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val startDestination by authViewModel.startDestination.collectAsState()

    if (startDestination == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Loading...")
        }
        return
    }

    NavHost(navController = navController,
        startDestination = startDestination!!
    ){

        composable(Screens.OnBoardingScreen.route) {
            OnBoardingScreen(authViewModel, navController)
        }
        composable(Screens.LoginTypeScreen.route) {
            LoginTypeScreen(authViewModel, navController)
        }
        composable(Screens.SignInScreen.route) {
            SignInScreen(authViewModel, navController)
        }
        composable(Screens.LogOutScreen.route) {
            LogOutScreen(authViewModel, navController)
        }
        composable(Screens.ProfileCompletionScreen.route) {
            ProfileCompletionScreen(profileViewModel, navController)
        }
        composable(Screens.DashBoardScreen.route) {
            DashBoardScreen(navController)

        }

    }
}