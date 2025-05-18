package com.uttkarsh.InstaStudio.presentation.navigation

sealed class Screens(val route: String) {
    object SignInScreen : Screens("sign_in_screen")
    object LogOutScreen : Screens("log_out_screen")

}