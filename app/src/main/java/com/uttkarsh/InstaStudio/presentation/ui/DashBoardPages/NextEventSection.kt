package com.uttkarsh.InstaStudio.presentation.ui.DashBoardPages

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uttkarsh.InstaStudio.R
import com.uttkarsh.InstaStudio.domain.model.dto.event.EventResponseDTO
import com.uttkarsh.InstaStudio.presentation.ui.EventPages.EventShimmerShow
import com.uttkarsh.InstaStudio.presentation.ui.utils.dateFormatter
import com.uttkarsh.InstaStudio.utils.states.EventState

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NextEventSection(
    eventState: EventState,
    onEventClick: () -> Unit
){

    val alatsiFont = FontFamily(Font(R.font.alatsi))

    when (eventState) {
        is EventState.Success -> {
            val event = eventState.response
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(78.dp),
                shape = RoundedCornerShape(27.dp),
                color = colorResource(R.color.buttons),
                onClick = onEventClick,
            ) {
                EventCard(event)

            }

        }

        is EventState.Loading -> {
            CircularProgressIndicator(color = Color.Black)
        }

        is EventState.Error -> {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(78.dp),
                shape = RoundedCornerShape(27.dp),
                color = colorResource(R.color.buttons),
                onClick = onEventClick,
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = eventState.message,
                        fontFamily = alatsiFont,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
            }


        }

        is EventState.Idle -> {
            EventShimmerShow()
        }

        is EventState.CompletedPagingSuccess -> {}
        is EventState.UpcomingPagingSuccess -> {}
    }
}

@RequiresApi(Build.VERSION_CODES.O)
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
                    color = Color.White
                )
                val formattedDate = dateFormatter(event?.eventStartDate ?: "Invalid Date")

                Text(
                    text = formattedDate.toString(),
                    fontFamily = alatsiFont,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }

            Column(
                modifier = Modifier
                    .size(width = 100.dp, height = 30.dp)
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(color = colorResource(R.color.mainGreen))
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
                        color = Color.Black
                    )
                    Column(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(shape = RoundedCornerShape(100.dp))
                            .background(color = colorResource(R.color.darkGreen))
                    ) { null }
                }
            }
        }
    }
}