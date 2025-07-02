package com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventPages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.uttkarsh.InstaStudio.R
import com.uttkarsh.InstaStudio.domain.model.dto.event.SubEventResponseDTO
import com.uttkarsh.InstaStudio.presentation.ui.utils.rememberTimeProvider


@Composable
fun SubEventCard(
    subEvent: SubEventResponseDTO,
    onDeleteClick: () -> Unit
) {

    val alatsiFont = FontFamily(Font(R.font.alatsi))

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(15.dp),
        color = MaterialTheme.colorScheme.tertiaryContainer
    ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                val timeProvider = rememberTimeProvider()

                Column(
                    modifier = Modifier.fillMaxHeight()
                        .weight(0.3f)
                        .wrapContentWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                    timeProvider.formatDate(subEvent.eventStartDate).toString().substring(0, 6),
                        fontFamily = alatsiFont,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                    timeProvider.formatTime(subEvent.eventStartDate).toString(),
                        fontFamily = alatsiFont,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier.weight(0.1f),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    Icon(painterResource(R.drawable.verticalline), contentDescription = null)
                }


                Row(
                    modifier = Modifier.weight(0.7f),
                    horizontalArrangement = Arrangement.SpaceEvenly

                ) {
                    Row(
                        modifier = Modifier.wrapContentWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Image(
                            painter = rememberAsyncImagePainter("https://img.icons8.com/ios/50/marker--v1.png"),
                            contentDescription = "Marker Icon",
                            modifier = Modifier.size(15.dp),
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                        )

                        Text(
                            subEvent.eventType,
                            fontFamily = alatsiFont,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Thin
                        )
                    }


                    Row(
                        modifier = Modifier.wrapContentWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter("https://img.icons8.com/ios-glyphs/30/sorting-options.png"),
                            contentDescription = "Marker Icon",
                            modifier = Modifier.size(15.dp),
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                        )
                        Text(
                            subEvent.eventLocation,
                            fontFamily = alatsiFont,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Thin
                        )
                    }
                }


            }
    }

}