package com.uttkarsh.InstaStudio.presentation.ui.EventPages

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.uttkarsh.InstaStudio.R
import com.uttkarsh.InstaStudio.presentation.ui.utils.AppTopBar
import com.uttkarsh.InstaStudio.presentation.viewmodel.SubEventViewModel
import com.uttkarsh.InstaStudio.utils.states.SubEventState

@Composable
fun SubEventDetailScreen(
    subEventViewModel: SubEventViewModel = hiltViewModel(),
    navController: NavController
){

    val subEventState by subEventViewModel.subEventState.collectAsState()

    LaunchedEffect(Unit) {
        subEventViewModel.getSubEventById()
    }

    val scrollState = rememberScrollState()

    val alatsiFont = FontFamily(Font(R.font.alatsi))

    Scaffold(
        topBar = {
            AppTopBar(
                title = "SubEvent Details",
                isNavIcon = true,
                navIcon = R.drawable.back,
                onNavClick = { navController.navigateUp() }
            )
        }
    ){pv->
        when(subEventState){
            is SubEventState.Success -> {
                val subEvent = (subEventState as SubEventState.Success).response
                Column(
                    modifier = Modifier.fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(pv),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = subEvent.eventType)
                    Text(text = subEvent.eventState)
                    Text(text = subEvent.eventLocation)
                    Text(text = subEvent.eventStartDate)
                    Text(text = subEvent.eventEndDate)
                    Text(text = subEvent.parentEventId.toString())
                }
            }
            is SubEventState.Error -> {}
            SubEventState.Idle -> {}
            SubEventState.Loading -> {}

        }

    }

}