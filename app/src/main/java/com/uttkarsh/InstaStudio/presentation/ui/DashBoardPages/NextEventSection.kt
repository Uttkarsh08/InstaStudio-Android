package com.uttkarsh.InstaStudio.presentation.ui.DashBoardPages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uttkarsh.InstaStudio.R
import com.uttkarsh.InstaStudio.domain.model.dto.event.EventResponseDTO
import com.uttkarsh.InstaStudio.presentation.ui.EventPages.EventListPages.EventShimmerShow
import com.uttkarsh.InstaStudio.presentation.ui.utils.rememberTimeProvider
import com.uttkarsh.InstaStudio.presentation.viewmodel.EventViewModel
import com.uttkarsh.InstaStudio.utils.states.EventState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NextEventSection(
    eventViewModel: EventViewModel,
    eventState: EventState,
    onEventClick: () -> Unit
){

    val alatsiFont = FontFamily(Font(R.font.alatsi))

    when (eventState) {
        is EventState.NextEventSuccess -> {
            val event = eventState.response
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(70.dp),
                shape = RoundedCornerShape(27.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                onClick = {
                    eventViewModel.updateEventId(event.eventId)
                    onEventClick()
                },
            ) {
                EventCard(event)

            }

        }

        is EventState.Loading -> {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
        }

        is EventState.Error -> {
            val error = eventState.message
            val noUpcoming = (error.contains("404"))
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(70.dp),
                shape = RoundedCornerShape(27.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                onClick = onEventClick,
            ) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = if(noUpcoming) "No Upcoming Event" else "Some Error occurred, \nPlease try again Later",
                        fontFamily = alatsiFont,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }


        }

        is EventState.Idle -> {
            EventShimmerShow()
        }

        is EventState.Success -> {
            //Not to handle here
        }
        is EventState.CompletedPagingSuccess -> {
            //Not to handle here
        }
        is EventState.UpcomingPagingSuccess -> {
            //Not to handle here
        }
    }
}

@Composable
fun EventCard(
    event: EventResponseDTO?
){
    val alatsiFont = FontFamily(Font(R.font.alatsi))
    Column(
        modifier = Modifier
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = event?.clientName ?: "All Clear",
                    fontFamily = alatsiFont,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                val timeProvider = rememberTimeProvider()
                Text(
                    text = timeProvider.formatDate(event?.eventStartDate.toString()).toString(),
                    fontFamily = alatsiFont,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Column(
                modifier = Modifier
                    .size(width = 100.dp, height = 25.dp)
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.onPrimaryContainer)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Upcoming",
                        fontFamily = alatsiFont,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Column(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(shape = RoundedCornerShape(100.dp))
                            .background(MaterialTheme.colorScheme.tertiary)
                    ) { null }
                }
            }
        }
    }
}