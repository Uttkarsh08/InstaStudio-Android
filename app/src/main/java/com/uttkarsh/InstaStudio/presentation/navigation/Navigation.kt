package com.uttkarsh.InstaStudio.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uttkarsh.InstaStudio.presentation.ui.DashBoardPages.DashBoardScreen
import com.uttkarsh.InstaStudio.presentation.ui.LoginTypeScreen
import com.uttkarsh.InstaStudio.presentation.ui.OnBoardingScreen
import com.uttkarsh.InstaStudio.presentation.ui.ProfileCompletionScreen
import com.uttkarsh.InstaStudio.presentation.ui.SignInScreen
import com.uttkarsh.InstaStudio.presentation.ui.SplashScreen
import com.uttkarsh.InstaStudio.presentation.viewmodel.AuthViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.DashBoardViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.ProfileViewModel

@Composable
fun Navigation(
    authViewModel: AuthViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
    dashBoardViewModel: DashBoardViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = Screens.SplashScreen.route
    ){
        composable(Screens.SplashScreen.route) {
            SplashScreen(authViewModel, navController)
        }
        composable(Screens.OnBoardingScreen.route) {
            OnBoardingScreen(authViewModel, navController)
        }
        composable(Screens.LoginTypeScreen.route) {
            LoginTypeScreen(authViewModel, navController)
        }
        composable(Screens.SignInScreen.route) {
            SignInScreen(authViewModel, navController)
        }

        composable(Screens.ProfileCompletionScreen.route) {
            ProfileCompletionScreen(profileViewModel, navController)
        }
        composable(Screens.DashBoardScreen.route) {
            DashBoardScreen(authViewModel, profileViewModel, dashBoardViewModel, navController)

        }

    }
}