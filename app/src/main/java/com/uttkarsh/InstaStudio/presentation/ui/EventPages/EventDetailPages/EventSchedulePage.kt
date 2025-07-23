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
                            subEventViewModel.setSubEvents(subEvents)
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
        EventType.BIRTHDAY -> painterResource(R.drawable.wedding)
        EventType.OTHER -> painterResource(R.drawable.wedding)
        EventType.ANNIVERSARY -> TODO()
        EventType.MATERNITY -> TODO()
        EventType.BABY_SHOWER -> TODO()
        EventType.BABY_MILESTONE -> TODO()
        EventType.KIDS_BIRTHDAY -> TODO()
        EventType.FAMILY_PORTRAIT -> TODO()
        EventType.COUPLE_SHOOT -> TODO()
        EventType.INDIVIDUAL_PORTRAIT -> TODO()
        EventType.CORPORATE_EVENT -> TODO()
        EventType.CONFERENCE -> TODO()
        EventType.FESTIVAL -> TODO()
        EventType.RELIGIOUS_CEREMONY -> TODO()
        EventType.CULTURAL_EVENT -> TODO()
        EventType.GRADUATION -> TODO()
        EventType.SCHOOL_FUNCTION -> TODO()
        EventType.COLLEGE_EVENT -> TODO()
        EventType.PRODUCT_SHOOT -> TODO()
        EventType.FOOD_PHOTOGRAPHY -> TODO()
        EventType.FASHION_SHOOT -> TODO()
        EventType.JEWELRY_SHOOT -> TODO()
        EventType.ECOMMERCE_SHOOT -> TODO()
        EventType.MODELING_PORTFOLIO -> TODO()
        EventType.ACTING_PORTFOLIO -> TODO()
        EventType.PET_PHOTOGRAPHY -> TODO()
        EventType.REAL_ESTATE -> TODO()
        EventType.TRAVEL_EVENT -> TODO()
        EventType.YOUTUBE_CONTENT -> TODO()
    }
}