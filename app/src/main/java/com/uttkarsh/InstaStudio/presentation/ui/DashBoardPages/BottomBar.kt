package com.uttkarsh.InstaStudio.presentation.ui.DashBoardPages

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.uttkarsh.InstaStudio.presentation.navigation.Screens
import com.uttkarsh.InstaStudio.R
import com.uttkarsh.InstaStudio.presentation.viewmodel.AddEventViewModel

data class NavigationItem(
    val route: String,
    @DrawableRes val icon: Int,
    @DrawableRes val selectedIcon: Int,
    val iconContentDescription: String
)

@Composable
fun BottomBar(
    addEventViewModel: AddEventViewModel = hiltViewModel(),
    navController: NavController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    LaunchedEffect(currentDestination) {
        Log.d("DEBUG", "Current route: ${currentDestination?.route}")
    }

    val navigationItems = listOf<NavigationItem>(
        NavigationItem(
            route = Screens.DashBoardScreen.route,
            icon = R.drawable.home,
            selectedIcon = R.drawable.selectedhome,
            iconContentDescription = "dashboard_Screen"
        ),
        NavigationItem(
            route = Screens.EventScreen.route,
            icon = R.drawable.calender,
            selectedIcon = R.drawable.selectedcalender,
            iconContentDescription = "Calender_screen"
        ),
        NavigationItem(
            route = Screens.SignInScreen.route,
            icon = R.drawable.payments,
            selectedIcon = R.drawable.selectedpayments,
            iconContentDescription = "Payments_screen"
        ),
        NavigationItem(
            route = Screens.OnBoardingScreen.route,
            icon = R.drawable.account,
            selectedIcon = R.drawable.selectedaccount,
            iconContentDescription = "Profile_screen"
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .padding(bottom = 8.dp)
            .navigationBarsPadding()
            .statusBarsPadding()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(27.dp),
                clip = true
            )
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(27.dp))
            .height(60.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceContainerLowest),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            navigationItems.take(2).forEach { item ->
                BottomBarItem(
                    item = item,
                    selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                    onClick = {
                        navController.navigate(item.route) {
                            launchSingleTop = true
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            restoreState = true
                        }
                    },
                    currentDestination = currentDestination
                )

            }
            FloatingActionButton(
                onClick = {
                    addEventViewModel.markAddEventScreenForReset()
                    navController.navigate(Screens.AddEventDetailsScreen.route) {
                        launchSingleTop = true
                    }
                },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .size(50.dp)
                    .border(2.dp, MaterialTheme.colorScheme.tertiaryContainer, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(25.dp)
                )
            }
            navigationItems.drop(2).forEach { item ->
                BottomBarItem(
                    item = item,
                    selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                    onClick = {
                        navController.navigate(item.route) {
                            launchSingleTop = true
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            restoreState = true
                        }
                    },
                    currentDestination = currentDestination
                )

            }
        }
    }
}