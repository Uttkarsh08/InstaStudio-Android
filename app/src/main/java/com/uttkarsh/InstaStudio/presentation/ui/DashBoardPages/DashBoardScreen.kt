package com.uttkarsh.InstaStudio.presentation.ui.DashBoardPages

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import com.uttkarsh.InstaStudio.R
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.uttkarsh.InstaStudio.presentation.navigation.Screens
import com.uttkarsh.InstaStudio.presentation.ui.utils.AppTopBar
import com.uttkarsh.InstaStudio.presentation.ui.utils.SearchBar
import com.uttkarsh.InstaStudio.presentation.viewmodel.AuthViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.DashBoardViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
    dashBoardViewModel: DashBoardViewModel = hiltViewModel(),
    navController: NavController
){
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        dashBoardViewModel.getUserProfile()
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "DashBoard",
                isNavIcon = true,
                navIcon = R.drawable.studiologo,
                onNavClick = { /* TODO: Open Drawer */ }
            )
        },
        bottomBar = { BottomBar(navController) }
    ){ paddingValues ->
        Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
                    .border(
                        BorderStroke(2.dp, colorResource(R.color.searchBarBorder)),
                        RoundedCornerShape(14.dp)
                    ),

                onSearchChanged = { /* TODO: Update viewModel with new search query */ }
            )

            Spacer(Modifier.height(16.dp))

            NextEventSection("Mayank &  Ranjani", "1 Jan, 2025, Wed", {})

            Row(
                modifier = Modifier.fillMaxWidth()
                    .heightIn(max = 400.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    CommonSectionUi(R.drawable.events,
                        "Events",
                        "Manager your Events",
                        {navController.navigate(Screens.EventScreen.route)},
                        true,
                        false,
                        modifier = Modifier.weight(.45f)
                    )
                    CommonSectionUi(R.drawable.teamwork,
                        "Team Members",
                        "Add  New or See your work Partners",
                        {navController.navigate(Screens.MemberScreen.route)},
                        true,
                        true,
                        modifier = Modifier.weight(.55f)
                    )
                }

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    CommonSectionUi(R.drawable.customers,
                        "Customers",
                        "Add  New or See your Customers",
                        {},
                        false,
                        true,
                        modifier = Modifier.weight(.55f)
                    )
                    CommonSectionUi(R.drawable.resources,
                        "Resources",
                        "Manager your Resources",
                        {navController.navigate(Screens.ResourceScreen.route)},
                        false,
                        false,
                        modifier = Modifier.weight(.45f)
                    )
                }
            }

        Spacer(Modifier.height(16.dp))
        }
    }



}

@Preview(showBackground = true)
@Composable
fun GetPreview(){
    DashBoardScreen(navController = NavController(LocalContext.current))
}

//Button(onClick = {
//    authViewModel.logout()
//    profileViewModel.resetProfileState()
//    dashBoardViewModel.resetDashBoardState()
//    navController.navigate(Screens.LoginTypeScreen.route){
//        popUpTo(0) { inclusive = true }
//        launchSingleTop = true
//    }
//
//}) {
//    Text("Logout")
//}
