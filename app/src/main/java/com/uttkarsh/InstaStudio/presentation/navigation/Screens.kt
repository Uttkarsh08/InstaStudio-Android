package com.uttkarsh.InstaStudio.presentation.navigation

sealed class Screens(val route: String) {
    object OnBoardingScreen: Screens("onboarding_screen")
    object LoginTypeScreen: Screens("login_type_screen")
    object SignInScreen : Screens("sign_in_screen")
    object LogOutScreen : Screens("log_out_screen")

}