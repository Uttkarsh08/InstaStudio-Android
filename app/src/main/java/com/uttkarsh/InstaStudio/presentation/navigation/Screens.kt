package com.uttkarsh.InstaStudio.presentation.navigation

sealed class Screens(val route: String) {
    object SplashScreen: Screens("splash_screen")
    object OnBoardingScreen: Screens("onboarding_screen")
    object LoginTypeScreen: Screens("login_type_screen")
    object SignInScreen : Screens("sign_in_screen")
    object ProfileCompletionScreen: Screens("profile_completion_screen")
    object DashBoardScreen: Screens("dashboard_screen")
    object ResourceScreen: Screens("resource_screen")
    object MemberScreen: Screens("member_screen")
}