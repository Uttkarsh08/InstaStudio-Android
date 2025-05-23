package com.uttkarsh.InstaStudio.presentation.ui.DashBoardPages

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import com.uttkarsh.InstaStudio.R
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.uttkarsh.InstaStudio.presentation.ui.DashBoardPages.util.BottomBar
import com.uttkarsh.InstaStudio.presentation.ui.DashBoardPages.util.CommonSectionUi
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
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .then(
                    if (isLandscape) Modifier.verticalScroll(scrollState) else Modifier
                ),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBar(
                onSearchChanged = { /* TODO: Update viewModel with new search query */ }
            )

            Spacer(Modifier.height(20.dp))

            NextEventSection("Mayank &  Ranjani", "1 Jan, 2025, Wed", {})

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    CommonSectionUi(R.drawable.events, "Events", "Manager your Events", {}, true, false)
                    CommonSectionUi(R.drawable.events, "Team Members", "Add  New or See your work Partners", {}, true, true)
                }

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    CommonSectionUi(R.drawable.events, "Team Members", "Add  New or See your work Partners", {}, false, true)
                    CommonSectionUi(R.drawable.events, "Events", "Manager your Events", {}, false, false)
                }
            }
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
