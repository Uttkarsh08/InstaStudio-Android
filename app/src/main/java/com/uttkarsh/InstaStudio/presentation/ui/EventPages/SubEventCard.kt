package com.uttkarsh.InstaStudio.presentation.ui.EventPages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uttkarsh.InstaStudio.R
import com.uttkarsh.InstaStudio.domain.model.dto.event.SubEventResponseDTO

@Composable
fun SubEventCard(
    subEvent: SubEventResponseDTO,
    onDeleteClick: () -> Unit
){

    val alatsiFont = FontFamily(Font(R.font.alatsi))

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .height(90.dp),
        shape = RoundedCornerShape(27.dp),
        color = colorResource(R.color.mediumContainer)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ){


            Text(subEvent.eventId.toString(), fontFamily = alatsiFont, fontSize = 24.sp, color = Color.Black, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

            Row {
                Text(subEvent.eventType, fontFamily = alatsiFont, fontSize = 16.sp, color = Color.Black, modifier = Modifier.fillMaxWidth(0.5f), textAlign = TextAlign.Center)
                Text(subEvent.eventLocation, fontFamily = alatsiFont, fontSize = 16.sp, color = Color.Black, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            }

            Row {
                Text(subEvent.eventStartDate, fontFamily = alatsiFont, fontSize = 16.sp, color = Color.Black, modifier = Modifier.fillMaxWidth(0.5f), textAlign = TextAlign.Center)
                Text(subEvent.eventEndDate, fontFamily = alatsiFont, fontSize = 16.sp, color = Color.Black, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            }

            Icon(
                Icons.Default.Delete,
                contentDescription = null,
                modifier = Modifier
                    .clickable(
                        onClick = onDeleteClick
                    )
            )

        }
    }

}