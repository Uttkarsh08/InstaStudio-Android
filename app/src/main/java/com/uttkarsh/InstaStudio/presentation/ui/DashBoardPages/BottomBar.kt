package com.uttkarsh.InstaStudio.presentation.ui.DashBoardPages

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.uttkarsh.InstaStudio.presentation.navigation.Screens
import com.uttkarsh.InstaStudio.R

data class NavigationItem(
    val route: String,
    @DrawableRes val icon: Int,
    @DrawableRes val selectedIcon: Int,
    val iconContentDescription: String
)

@Composable
fun BottomBar(
    navController: NavController,
){
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
            route = Screens.DashBoardScreen.route,
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
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
            .height(90.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight()
                .background(
                    color = colorResource(id = R.color.mainGreen),
                    shape = RoundedCornerShape(topEnd = 100.dp)
                )
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, end = 48.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
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
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight()
                .background(
                    color = colorResource(id = R.color.mainGreen),
                    shape = RoundedCornerShape(topStart = 100.dp)
                )
                .align(Alignment.BottomEnd)
                .padding(start = 48.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
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

        FloatingActionButton(
            onClick = { navController.navigate(Screens.AddEventNavGraph.route) },
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(6.dp),
            containerColor = colorResource(id = R.color.dashBoardContainer),
            contentColor = Color.Black,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(70.dp)
                .border(3.dp, Color.White, CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.Black,
                modifier = Modifier.size(32.dp)
            )
        }
    }

}