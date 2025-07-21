package com.uttkarsh.InstaStudio.presentation.ui.EventPages.SubEventDetailPages

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.uttkarsh.InstaStudio.R
import com.uttkarsh.InstaStudio.domain.model.EventType
import com.uttkarsh.InstaStudio.presentation.ui.EventPages.EventDetailPages.subEventInEventDetail_Image
import com.uttkarsh.InstaStudio.presentation.ui.utils.AppTopBar
import com.uttkarsh.InstaStudio.presentation.ui.utils.rememberTimeProvider
import com.uttkarsh.InstaStudio.presentation.viewmodel.SubEventViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SubEventDetailScreen(
    subEventViewModel: SubEventViewModel = hiltViewModel(),
    navController: NavController
) {
    val subEvents by subEventViewModel.subEvents.collectAsState()
    val selectedSubEventId by subEventViewModel.selectedSubEventId.collectAsState()
    val pagerState = rememberPagerState(
        initialPage = subEvents.indexOfFirst { it.eventId == selectedSubEventId }.coerceAtLeast(0),
        pageCount = {subEvents.size}
    )
    val coroutineScope = rememberCoroutineScope()
    val timeProvider = rememberTimeProvider()
    val alatsiFont = FontFamily(Font(R.font.alatsi))

    LaunchedEffect(pagerState.currentPage) {
        if (subEvents.isNotEmpty()) {
            val newId = subEvents[pagerState.currentPage].eventId
            subEventViewModel.updateSubEventId(newId)
            subEventViewModel.getSubEventById()
        }
    }

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
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) { page ->
            val subEvent = subEvents[page]
            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .imePadding()
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
                            .verticalScroll(scrollState)
                            .padding(horizontal = 32.dp, vertical = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = subEvent.eventId.toString(),
                            fontFamily = alatsiFont,
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.displaySmall
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        val targetPage =
                                            (pagerState.currentPage - 1).coerceAtLeast(0)

                                        pagerState.animateScrollToPage(
                                            page = targetPage,
                                            animationSpec = tween(
                                                durationMillis = 800,
                                                easing = LinearOutSlowInEasing
                                            )
                                        )
                                    }
                                }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.back),
                                    contentDescription = "Previous",
                                    modifier = Modifier.size(25.dp)
                                )
                            }

                            Surface(
                                modifier = Modifier.size(140.dp),
                                shape = RoundedCornerShape(100.dp),
                                border = BorderStroke(
                                    1.dp,
                                    MaterialTheme.colorScheme.surfaceContainerHigh
                                )
                            ) {
                                val eventTypeEnum =
                                    EventType.entries.find { it.name == subEvent.eventType } ?:
                                    EventType.OTHER

                                Image(
                                    painter = subEventInEventDetail_Image(eventTypeEnum),
                                    contentDescription = "SubEvent Image",
                                    alignment = Alignment.Center,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.padding(4.dp)
                                )
                            }

                            IconButton(
                                onClick = {
                                    coroutineScope.launch {

                                        val targetPage = (pagerState.currentPage + 1)
                                            .coerceAtMost(subEvents.lastIndex)

                                        pagerState.animateScrollToPage(
                                            page = targetPage,
                                            animationSpec = tween(
                                                durationMillis = 800,
                                                easing = LinearOutSlowInEasing
                                            )
                                        )
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                    contentDescription = "Next",
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
                                    modifier = Modifier
                                        .size(20.dp)
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
                                            color = MaterialTheme.colorScheme
                                                .surfaceContainerHigh,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Row {
                                            Text(
                                                text = subEvent.eventLocation + ",",
                                                fontFamily = alatsiFont,
                                                color = MaterialTheme.colorScheme
                                                    .surfaceContainerHigh,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                            Text(
                                                text = subEvent.eventCity,
                                                fontFamily = alatsiFont,
                                                color = MaterialTheme.colorScheme
                                                    .surfaceContainerHigh,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                            Text(
                                                text = subEvent.eventState,
                                                fontFamily = alatsiFont,
                                                color = MaterialTheme.colorScheme
                                                    .surfaceContainerHigh,
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
                                    modifier = Modifier
                                        .size(20.dp)
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
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(end = 8.dp),
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .weight(1f),
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
                                                text = timeProvider
                                                    .formatDate(subEvent.eventStartDate)
                                                    .toString(),
                                                fontFamily = alatsiFont,
                                                color = MaterialTheme.colorScheme
                                                    .surfaceContainerHigh,
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
                                                text = timeProvider
                                                    .formatTime(subEvent.eventStartDate)
                                                    .toString(),
                                                fontFamily = alatsiFont,
                                                color = MaterialTheme.colorScheme
                                                    .surfaceContainerHigh,
                                                style = MaterialTheme.typography.bodyMedium
                                            )

                                        }

                                    }

                                    Column(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .weight(1f),
                                        verticalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .wrapContentHeight()
                                                .background(Color.Transparent),

                                            ) {
                                            Text(
                                                text = "",
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
                                                text = timeProvider
                                                    .formatDate(subEvent.eventEndDate)
                                                    .toString(),
                                                fontFamily = alatsiFont,
                                                color = MaterialTheme.colorScheme
                                                    .surfaceContainerHigh,
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
                                                text = timeProvider
                                                    .formatTime(subEvent.eventEndDate)
                                                    .toString(),
                                                fontFamily = alatsiFont,
                                                color = MaterialTheme.colorScheme
                                                    .surfaceContainerHigh,
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
    }


}
