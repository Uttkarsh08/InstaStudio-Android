package com.uttkarsh.InstaStudio.presentation.ui.EventPages.EventListPages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.uttkarsh.InstaStudio.R
import com.uttkarsh.InstaStudio.domain.model.dto.event.EventListResponseDTO
import com.uttkarsh.InstaStudio.presentation.ui.utils.rememberTimeProvider
import com.uttkarsh.InstaStudio.ui.theme.InstaStudioTheme

@Composable
fun EventCard(
    event: EventListResponseDTO,
    isCompleted: Boolean,
    onclick: () -> Unit,
    onButtonClicked: () -> Unit
) {

    val alatsiFont = FontFamily(Font(R.font.alatsi))

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp),
        shape = RoundedCornerShape(15.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        onClick = onclick,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            val timeProvider = rememberTimeProvider()

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.2f)
                    .wrapContentWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    timeProvider.formatDate(event.eventStartDate).toString().substring(0, 6),
                    fontFamily = alatsiFont,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    timeProvider.formatTime(event.eventStartDate).toString(),
                    fontFamily = alatsiFont,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier.weight(0.1f).padding(vertical = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(0.3f))
                )
            }

            Column(
                modifier = Modifier.weight(0.6f),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    event.clientName,
                    fontFamily = alatsiFont,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Light
                )

                Column(
                    modifier = Modifier.wrapContentSize(),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    Row(
                        modifier = Modifier.wrapContentWidth().padding(0.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter("https://img.icons8.com/ios/50/marker--v1.png"),
                            contentDescription = "Marker Icon",
                            modifier = Modifier.size(10.dp).padding(0.dp),
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                        )

                        Spacer(Modifier.width(4.dp))

                        Text(
                            text = event.eventType,
                            fontFamily = alatsiFont,
                            fontWeight = FontWeight.Thin,
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }


                    Row(
                        modifier = Modifier.wrapContentWidth().padding(0.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter("https://img.icons8.com/ios-glyphs/30/sorting-options.png"),
                            contentDescription = "Marker Icon",
                            modifier = Modifier.size(10.dp).padding(0.dp),
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                        )

                        Spacer(Modifier.width(4.dp))

                        Text(
                            event.eventLocation,
                            fontFamily = alatsiFont,
                            fontWeight = FontWeight.Thin,
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .width(80.dp)
                    .height(30.dp)
            ) {

                if(isCompleted){
                    Button(
                        onClick = onButtonClicked,
                        modifier = Modifier.fillMaxWidth()
                            .clip(RoundedCornerShape(15.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        Text(text = "Invoice", fontFamily = alatsiFont)
                    }
                }else{
                    if (event.eventIsSaved) {
                        Button(
                            onClick = onButtonClicked,
                            modifier = Modifier.fillMaxWidth()
                                .clip(RoundedCornerShape(15.dp)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            ),
                            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
                        ) {
                            Text(text = "Undo", fontFamily = alatsiFont)
                        }
                    } else {
                        Button(
                            onClick = onButtonClicked,
                            modifier = Modifier.fillMaxWidth()
                                .clip(RoundedCornerShape(15.dp)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.tertiary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
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
