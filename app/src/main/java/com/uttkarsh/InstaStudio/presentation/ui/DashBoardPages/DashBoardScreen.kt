package com.uttkarsh.InstaStudio.presentation.ui.DashBoardPages

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
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.uttkarsh.InstaStudio.R
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.uttkarsh.InstaStudio.presentation.navigation.Screens
import com.uttkarsh.InstaStudio.presentation.ui.utils.AppTopBar
import com.uttkarsh.InstaStudio.presentation.ui.utils.SearchBar
import com.uttkarsh.InstaStudio.presentation.viewmodel.AddEventViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.DashBoardViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.EventViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardScreen(
    eventViewModel: EventViewModel = hiltViewModel(),
    addEventViewModel: AddEventViewModel = hiltViewModel(),
    dashBoardViewModel: DashBoardViewModel = hiltViewModel(),
    navController: NavController
){
    val scrollState = rememberScrollState()

    val nextEvenState by eventViewModel.nextEventState.collectAsState()

    LaunchedEffect(Unit) {
        dashBoardViewModel.loadUserProfileIfNeeded()
        delay(1000L)
        eventViewModel.loadNextUpcomingEventIfNeeded()
    }

    Scaffold(
        topBar = {
            AppTopBar(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                title = "InstaStudio",
                isNavIcon = true,
                navIcon = R.drawable.studiologo,
                isRightIcon = false,
                rightIcon = null,
                onNavClick = { /* TODO: Open Drawer */ },
                onRightIconClicked = {}
            )
        },
        bottomBar = { BottomBar(addEventViewModel, navController) }
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
                        BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary),
                        RoundedCornerShape(10.dp)
                    ),

                onSearchChanged = { /* TODO: Update viewModel with new search query */ }
            )

            Spacer(Modifier.height(16.dp))

            NextEventSection(
                eventViewModel, nextEvenState
            ) {
                navController.navigate(Screens.EventDetailScreen.route)
            }

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
                        {
                            eventViewModel.resetHasLoadedFlags()
                            navController.navigate(Screens.EventScreen.route)
                        },
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
