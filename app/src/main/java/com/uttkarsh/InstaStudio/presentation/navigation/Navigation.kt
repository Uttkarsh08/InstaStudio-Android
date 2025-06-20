package com.uttkarsh.InstaStudio.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.uttkarsh.InstaStudio.presentation.ui.DashBoardPages.DashBoardScreen
import com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventScreen
import com.uttkarsh.InstaStudio.presentation.ui.EventPages.EventScreen
import com.uttkarsh.InstaStudio.presentation.ui.LoginTypeScreen
import com.uttkarsh.InstaStudio.presentation.ui.MemberPages.MemberScreen
import com.uttkarsh.InstaStudio.presentation.ui.OnBoardingScreen
import com.uttkarsh.InstaStudio.presentation.ui.ProfileCompletionScreen
import com.uttkarsh.InstaStudio.presentation.ui.ResourcePages.ResourceScreen
import com.uttkarsh.InstaStudio.presentation.ui.SignInScreen
import com.uttkarsh.InstaStudio.presentation.ui.SplashScreen
import com.uttkarsh.InstaStudio.presentation.viewmodel.AddEventViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.AuthViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.DashBoardViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.EventViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.MemberViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.ProfileViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.ResourceViewModel

@Composable
fun Navigation(
    authViewModel: AuthViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
    dashBoardViewModel: DashBoardViewModel = hiltViewModel(),
    resourceViewModel: ResourceViewModel = hiltViewModel(),
    memberViewModel: MemberViewModel = hiltViewModel(),
    eventViewModel: EventViewModel = hiltViewModel(),
    addEventViewModel: AddEventViewModel = hiltViewModel()
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
        composable(Screens.ResourceScreen.route) {
            ResourceScreen(resourceViewModel, navController)
        }
        composable(Screens.MemberScreen.route) {
            MemberScreen(memberViewModel, navController)
        }
        composable(Screens.EventScreen.route) {
            EventScreen(eventViewModel, navController)
        }

        navigation(
            startDestination = Screens.AddEventDetailsScreen.route,
            route = Screens.AddEventNavGraph.route

        ) {
            composable(Screens.AddEventDetailsScreen.route) {
                AddEventScreen(addEventViewModel, navController)
            }
        }
    }
}