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
    object EventScreen: Screens("event_screen")

    object AddEventNavGraph : Screens("add_event_nav_graph")
    object AddEventDetailsScreen : Screens("add_event_details")
    object AddSubEventDetailsScreen : Screens("add_sub_event")
}