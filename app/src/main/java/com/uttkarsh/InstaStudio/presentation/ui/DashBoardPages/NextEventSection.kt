package com.uttkarsh.InstaStudio.presentation.ui.DashBoardPages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.uttkarsh.InstaStudio.R
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NextEventSection(
    clientName: String,
    eventStartDate: String,
    onEventClick: () -> Unit
){

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(78.dp),
        shape = RoundedCornerShape(27.dp),
        color = colorResource(R.color.darkGrey),
        onClick = onEventClick,
    ) {
        EventCard(clientName = clientName, eventStartDate = eventStartDate)

    }
}

@Composable
fun EventCard(
    clientName: String,
    eventStartDate: String
){
    val alatsiFont = FontFamily(Font(R.font.alatsi))
    Column(
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 8.dp
        ).fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier
                .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = clientName, fontFamily = alatsiFont, fontSize = 20.sp, color = Color.White)
                Text(text = eventStartDate, fontFamily = alatsiFont, fontSize = 20.sp, color = Color.White)
            }

            Column(
                modifier = Modifier
                    .size(width = 100.dp, height = 40.dp)
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(color = colorResource(R.color.mainGreen))
            ){
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Upcoming", fontFamily = alatsiFont, fontSize = 14.sp, color = Color.Black)
                    Column(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(shape = RoundedCornerShape(100.dp))
                            .background(color = colorResource(R.color.darkGreen))
                    ){null}
                }
            }
        }


    }

}