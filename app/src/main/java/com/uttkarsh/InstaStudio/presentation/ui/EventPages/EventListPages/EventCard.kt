package com.uttkarsh.InstaStudio.presentation.ui.EventPages.EventListPages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uttkarsh.InstaStudio.R
import com.uttkarsh.InstaStudio.domain.model.dto.event.EventListResponseDTO

@Composable
fun EventCard(
    event: EventListResponseDTO,
    onclick: () -> Unit
){

    val alatsiFont = FontFamily(Font(R.font.alatsi))

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(27.dp),
        color = colorResource(R.color.mediumContainer),
        onClick = onclick
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center
        ){
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(.5f),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Mr.Harish Kumar",
                        fontFamily = alatsiFont,
                        fontSize = 10.sp,
                        lineHeight = 10.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )

                    Button(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.darkGrey),
                            contentColor = Color.Black
                        ),
                        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        Text(text = "Edit", fontFamily = alatsiFont)
                    }
                }


                Column(
                    modifier = Modifier.weight(1f)
                        .fillMaxSize()
                        .padding(vertical = 8.dp)
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = event.clientName,
                        fontFamily = alatsiFont,
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .height(24.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = event.eventStartDate.substring(0, 10), fontFamily = alatsiFont, fontSize = 14.sp)

                        Text(text = event.eventLocation, fontFamily = alatsiFont, fontSize = 14.sp)
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(.5f),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "22/06/25",
                        fontFamily = alatsiFont,
                        fontSize = 10.sp,
                        lineHeight = 10.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )

                    if(event.eventIsSaved){
                        Button(
                            onClick = {},
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.darkGrey),
                                contentColor = Color.Black
                            ),
                            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
                        ) {
                            Text(text = "Undo", fontFamily = alatsiFont)
                        }
                    }else{
                        Button(
                            onClick = {},
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.darkGreen),
                                contentColor = Color.Black
                            ),
                            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
                        ) {
                            Text(text = "Save", fontFamily = alatsiFont)
                        }
                    }
                }
            }

        }
    }
}