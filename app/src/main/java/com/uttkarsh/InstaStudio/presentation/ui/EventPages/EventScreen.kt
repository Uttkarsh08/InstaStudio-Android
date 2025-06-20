package com.uttkarsh.InstaStudio.presentation.ui.EventPages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.uttkarsh.InstaStudio.R
import com.uttkarsh.InstaStudio.domain.model.dto.event.EventResponseDTO
import com.uttkarsh.InstaStudio.presentation.ui.utils.AppTopBar
import com.uttkarsh.InstaStudio.presentation.viewmodel.EventViewModel
import com.uttkarsh.InstaStudio.utils.states.EventState
import kotlinx.coroutines.flow.Flow

@Composable
fun EventScreen(
    eventViewModel: EventViewModel = hiltViewModel(),
    navController: NavController
){

    LaunchedEffect(Unit) {
        eventViewModel.getUpcomingEvents()
    }

    val state by eventViewModel.eventState.collectAsState()

    val events = if (state is EventState.PagingSuccess) {
        (state as EventState.PagingSuccess).data.collectAsLazyPagingItems()
    }else null


    Scaffold(
        topBar = {
            AppTopBar(
                title = "Add Event",
                isNavIcon = true,
                navIcon = R.drawable.back,
                onNavClick = {navController.navigateUp()},
            )
        }
    ){
        when(state){
            is EventState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Failed to load resources.")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { eventViewModel.getUpcomingEvents() }) {
                            Text("Retry")
                        }
                    }
                }
            }
            EventState.Idle -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No Events loaded yet.")
                }
            }
            EventState.Loading -> {
                CircularProgressIndicator()
            }
            is EventState.PagingSuccess -> {
                LazyColumn(
                    modifier = Modifier.padding(it).fillMaxSize()
                ){
                    items(
                        count = events?.itemCount ?:0,
                        key = {events?.get(it)?.eventId?: it}
                    ){
                        events?.get(it)?.let { event->
                            EventItem(event.clientName)
                        }
                    }
                }

            }
            is EventState.Success -> TODO()
        }
        
    }

}

@Composable
fun EventItem(eventName: String){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = eventName)
    }
}