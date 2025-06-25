package com.uttkarsh.InstaStudio.presentation.ui.EventPages.EventDetailPages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.uttkarsh.InstaStudio.R
import com.uttkarsh.InstaStudio.domain.model.EventType
import com.uttkarsh.InstaStudio.domain.model.dto.event.EventResponseDTO
import com.uttkarsh.InstaStudio.presentation.navigation.Screens
import com.uttkarsh.InstaStudio.presentation.ui.utils.rememberTimeProvider
import com.uttkarsh.InstaStudio.presentation.viewmodel.SubEventViewModel
import com.uttkarsh.InstaStudio.utils.time.TimeProvider

@Composable
fun EventSchedulePage(
    subEventViewModel: SubEventViewModel,
    event: EventResponseDTO,
    navController: NavController
){
    val subEvents = event.subEvents.toList()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(
                count = subEvents.size,
                key = { subEvents[it].eventId }
            ) {
                subEvents[it].let { event ->
                    SubEventCardInEventDetails(
                        event, {
                            //on SubEventClicked
                            subEventViewModel.updateSubEventId(event.eventId)
                            navController.navigate(Screens.SubEventDetailScreen.route)
                        })
                }
            }

        }
    }
}
@Composable
fun SubEventCardInEventDetails(
    event: EventResponseDTO,
    onclick: () -> Unit
) {
    val alatsiFont = FontFamily(Font(R.font.alatsi))

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp),
        shape = RoundedCornerShape(27.dp),
        color = Color.Transparent,
        onClick = onclick
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val eventTypeEnum = EventType.entries.find { it.name == event.eventType } ?: EventType.OTHER

                Image(
                    painter = subEventInEventDetail_Image(eventTypeEnum),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(70.dp)
                        .weight(1f),
                    contentScale = ContentScale.Fit
                )

                Image(
                    painter = painterResource(R.drawable.arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(34.dp)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1.5f)
                    .fillMaxHeight()
                    .padding(start = 16.dp, top = 8.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = event.eventType,
                    fontFamily = alatsiFont,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                val timeProvider = rememberTimeProvider()
                Text(
                    text = timeProvider.formatDate(event.eventStartDate).toString(),
                    fontFamily = alatsiFont,
                    fontSize = 16.sp,
                    color = colorResource(R.color.grey)
                )
                Text(
                    text = event.eventLocation,
                    fontFamily = alatsiFont,
                    fontSize = 16.sp,
                    color = colorResource(R.color.grey)
                )
            }
        }
    }
}


@Composable
fun subEventInEventDetail_Image(eventType: EventType): Painter{
    return when(eventType){
        EventType.WEDDING -> painterResource(R.drawable.wedding)
        EventType.HALDI -> painterResource(R.drawable.haldi)
        EventType.BIRTHDAY -> painterResource(R.drawable.wedding)
        EventType.CORPORATE -> painterResource(R.drawable.wedding)
        EventType.OTHER -> painterResource(R.drawable.wedding)
        EventType.MAHENDI -> painterResource(R.drawable.mahendi)
        EventType.RING_CEREMONY -> painterResource(R.drawable.ringceremony)
        EventType.VIDAI -> painterResource(R.drawable.wedding)
    }
}