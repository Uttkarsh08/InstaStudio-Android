package com.uttkarsh.InstaStudio.presentation.ui.EventPages.SubEventDetailPages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uttkarsh.InstaStudio.R
import com.uttkarsh.InstaStudio.presentation.ui.utils.AppTopBar
import com.uttkarsh.InstaStudio.presentation.ui.utils.rememberTimeProvider
import com.uttkarsh.InstaStudio.presentation.viewmodel.SubEventViewModel
import com.uttkarsh.InstaStudio.utils.states.SubEventState

@Composable
fun SubEventDetailScreen(
    subEventViewModel: SubEventViewModel = hiltViewModel(),
    navController: NavController
) {

    val subEventState by subEventViewModel.subEventState.collectAsState()

    LaunchedEffect(Unit) {
        subEventViewModel.getSubEventById()
    }

    val scrollState = rememberScrollState()
    val timeProvider = rememberTimeProvider()

    val alatsiFont = FontFamily(Font(R.font.alatsi))

    Scaffold(
        topBar = {
            AppTopBar(
                color = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                title = "SubEvent Details",
                isNavIcon = true,
                navIcon = R.drawable.back,
                isRightIcon = false,
                rightIcon = null,
                onRightIconClicked = {},
                onNavClick = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        when (subEventState) {
            is SubEventState.Success -> {
                val subEvent = (subEventState as SubEventState.Success).response
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .imePadding()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 10.dp)
                            .clip(
                                RoundedCornerShape(
                                    topStart = 27.dp,
                                    topEnd = 27.dp
                                )
                            )
                            .background(MaterialTheme.colorScheme.onPrimaryContainer)

                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(
                                    horizontal = 32.dp,
                                    vertical = 24.dp
                                )
                                .verticalScroll(scrollState),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = subEvent.parentEventId.toString(),
                                fontFamily = alatsiFont,
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.displaySmall
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                IconButton(
                                    onClick = {}
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.back),
                                        contentDescription = null,
                                        modifier = Modifier.size(25.dp)
                                    )
                                }

                                Column(
                                    modifier = Modifier.wrapContentSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Surface(
                                        modifier = Modifier
                                            .wrapContentSize()
                                            .size(150.dp),
                                        shape = RoundedCornerShape(100.dp),
                                        border = BorderStroke(
                                            1.dp,
                                            MaterialTheme.colorScheme.surfaceContainerHigh
                                        )
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.haldi),
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.padding(4.dp)
                                        )

                                    }
                                }

                                IconButton(
                                    onClick = {}
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowForwardIos,
                                        contentDescription = null,
                                        modifier = Modifier.size(30.dp)
                                    )
                                }

                            }
                            Spacer(Modifier.height(16.dp))
                            Text(
                                text = subEvent.eventType,
                                fontFamily = alatsiFont,
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.titleLarge
                            )


                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(90.dp),
                                shape = RoundedCornerShape(15.dp),
                                color = MaterialTheme.colorScheme.tertiaryContainer,
                                border = BorderStroke(
                                    1.dp,
                                    MaterialTheme.colorScheme.secondaryContainer
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.Top,
                                    horizontalArrangement = Arrangement.Start
                                ) {

                                    Icon(
                                        painter = painterResource(R.drawable.location),
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                            .align(Alignment.CenterVertically),
                                        tint = MaterialTheme.colorScheme.primaryContainer
                                    )

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Box(
                                        modifier = Modifier
                                            .width(2.dp)
                                            .fillMaxHeight()
                                            .background(
                                                MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                                    0.3f
                                                )
                                            )
                                    )

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Column(
                                        modifier = Modifier.fillMaxHeight(),
                                        verticalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        Text(
                                            text = "Address",
                                            fontFamily = alatsiFont,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            style = MaterialTheme.typography.bodyLarge
                                        )

                                        Column(
                                            modifier = Modifier.wrapContentSize()
                                        ) {
                                            Text(
                                                text = "Location",
                                                fontFamily = alatsiFont,
                                                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                            Row {
                                                Text(
                                                    text = subEvent.eventLocation + ",",
                                                    fontFamily = alatsiFont,
                                                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                                Text(
                                                    text = subEvent.eventCity,
                                                    fontFamily = alatsiFont,
                                                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                                Text(
                                                    text = subEvent.eventState,
                                                    fontFamily = alatsiFont,
                                                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                            }
                                        }

                                    }
                                }
                            }

                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(160.dp),
                                shape = RoundedCornerShape(15.dp),
                                color = MaterialTheme.colorScheme.tertiaryContainer,
                                border = BorderStroke(
                                    1.dp,
                                    MaterialTheme.colorScheme.secondaryContainer
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.Top,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.calender),
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                            .align(Alignment.CenterVertically),
                                        tint = MaterialTheme.colorScheme.primaryContainer
                                    )

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Box(
                                        modifier = Modifier
                                            .width(2.dp)
                                            .fillMaxHeight()
                                            .background(
                                                MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                                    0.3f
                                                )
                                            )
                                    )

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth().padding(end = 8.dp),
                                        horizontalArrangement = Arrangement.Start
                                    ) {
                                        Column(
                                            modifier = Modifier.fillMaxHeight().weight(1f),
                                            verticalArrangement = Arrangement.SpaceEvenly
                                        ) {
                                            Text(
                                                text = "Schedule",
                                                fontFamily = alatsiFont,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onPrimary,
                                                style = MaterialTheme.typography.bodyLarge
                                            )

                                            Column(
                                                modifier = Modifier.wrapContentSize(),
                                                verticalArrangement = Arrangement.SpaceEvenly
                                            ) {
                                                Text(
                                                    text = "Start Date",
                                                    fontFamily = alatsiFont,
                                                    color = MaterialTheme.colorScheme.onPrimary,
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                                Text(
                                                    text = timeProvider.formatDate(subEvent.eventStartDate)
                                                        .toString(),
                                                    fontFamily = alatsiFont,
                                                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                            }

                                            Column(
                                                modifier = Modifier.wrapContentSize(),
                                            ) {
                                                Text(
                                                    text = "Start Time",
                                                    fontFamily = alatsiFont,
                                                    color = MaterialTheme.colorScheme.onPrimary,
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                                Text(
                                                    text = timeProvider.formatTime(subEvent.eventStartDate)
                                                        .toString(),
                                                    fontFamily = alatsiFont,
                                                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                                                    style = MaterialTheme.typography.bodyMedium
                                                )

                                            }

                                        }

                                        Column(
                                            modifier = Modifier.fillMaxHeight().weight(1f),
                                            verticalArrangement = Arrangement.SpaceEvenly
                                        ) {
                                            Box(
                                                modifier = Modifier.wrapContentHeight()
                                                    .background(Color.Transparent),

                                                ){
                                                Text(text = "",
                                                    fontFamily = alatsiFont,
                                                    fontWeight = FontWeight.Bold,
                                                    style = MaterialTheme.typography.bodyLarge
                                                )
                                            }


                                            Column(
                                                modifier = Modifier.wrapContentSize(),
                                                verticalArrangement = Arrangement.SpaceEvenly
                                            ) {
                                                Text(
                                                    text = "End Date",
                                                    fontFamily = alatsiFont,
                                                    color = MaterialTheme.colorScheme.onPrimary,
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                                Text(
                                                    text = timeProvider.formatDate(subEvent.eventEndDate)
                                                        .toString(),
                                                    fontFamily = alatsiFont,
                                                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                            }

                                            Column(
                                                modifier = Modifier.wrapContentSize(),
                                            ) {
                                                Text(
                                                    text = "End Time",
                                                    fontFamily = alatsiFont,
                                                    color = MaterialTheme.colorScheme.onPrimary,
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                                Text(
                                                    text = timeProvider.formatTime(subEvent.eventEndDate)
                                                        .toString(),
                                                    fontFamily = alatsiFont,
                                                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                                                    style = MaterialTheme.typography.bodyMedium
                                                )

                                            }

                                        }
                                    }

                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(80.dp),
                                    shape = RoundedCornerShape(15.dp),
                                    color = MaterialTheme.colorScheme.tertiaryContainer,
                                    border = BorderStroke(
                                        1.dp,
                                        MaterialTheme.colorScheme.secondaryContainer
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "Members",
                                            fontFamily = alatsiFont,
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            style = MaterialTheme.typography.titleMedium,
                                        )

                                    }
                                }

                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(80.dp),
                                    shape = RoundedCornerShape(15.dp),
                                    color = MaterialTheme.colorScheme.tertiaryContainer,
                                    border = BorderStroke(
                                        1.dp,
                                        MaterialTheme.colorScheme.secondaryContainer
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "Resources",
                                            fontFamily = alatsiFont,
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            style = MaterialTheme.typography.titleMedium,
                                        )

                                    }

                                }
                            }
                            Spacer(Modifier.height(20.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Button(
                                    onClick = {},
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.errorContainer,
                                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                    modifier = Modifier
                                        .height(35.dp)
                                        .width(100.dp)
                                ) {
                                    Text(text = "Delete", fontFamily = alatsiFont, fontSize = 15.sp)
                                }

                                Button(
                                    onClick = {},
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                                        contentColor = MaterialTheme.colorScheme.onPrimary
                                    ),
                                    modifier = Modifier
                                        .height(35.dp)
                                        .width(100.dp)
                                ) {
                                    Text(text = "Edit", fontFamily = alatsiFont, fontSize = 15.sp)
                                }

                            }
                        }
                    }
                }
            }


            is SubEventState.Error -> {}
            SubEventState.Idle -> {}
            SubEventState.Loading -> {}

        }

    }

}