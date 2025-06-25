package com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventPages


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uttkarsh.InstaStudio.R
import com.uttkarsh.InstaStudio.presentation.ui.utils.AppTopBar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.uttkarsh.InstaStudio.domain.model.DatePickerTarget
import com.uttkarsh.InstaStudio.domain.model.TimePickerTarget
import com.uttkarsh.InstaStudio.presentation.navigation.Screens
import com.uttkarsh.InstaStudio.presentation.ui.utils.ShowDatePickerDialog
import com.uttkarsh.InstaStudio.presentation.ui.utils.ShowTimePickerDialog
import com.uttkarsh.InstaStudio.presentation.viewmodel.AddEventViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.EventViewModel
import com.uttkarsh.InstaStudio.utils.states.AddEventState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddEventScreen(
    addEventViewModel: AddEventViewModel = hiltViewModel(),
    eventViewModel: EventViewModel = hiltViewModel(),
    navController: NavController
) {

    val scrollState = rememberScrollState()
    val alatsiFont = remember { FontFamily(Font(R.font.alatsi)) }

    val eventTypes = addEventViewModel.eventTypes
    val selectedEventType by addEventViewModel.selectedEventType
    val dropdownExpanded by addEventViewModel.eventTypeDropdownExpanded

    val clientName = addEventViewModel.clientName
    val clientPhoneNo = addEventViewModel.clientPhoneNo
    val eventStartDate = addEventViewModel.eventStartDate
    val eventEndDate = addEventViewModel.eventEndDate
    val eventStartTime = addEventViewModel.eventStartTime
    val eventEndTime = addEventViewModel.eventEndTime
    val eveLocation = addEventViewModel.eventLocation
    val eventCity = addEventViewModel.eventCity
    val eventState = addEventViewModel.eventState
    val datePickerTarget = addEventViewModel.datePickerTarget
    val timePickerTarget = addEventViewModel.timePickerTarget

    val state by addEventViewModel.addEventState.collectAsState()
    val subEventsMap by addEventViewModel.subEventsMap.collectAsState()

    val errorMessage = (state as? AddEventState.Error)?.message

    if (datePickerTarget != null) {
        ShowDatePickerDialog(
            onDateSelected = { date ->
                addEventViewModel.onDatePicked(date)
            }
        )
    }

    if (timePickerTarget != null) {
        ShowTimePickerDialog(
            onTimeSelected = { time ->
                addEventViewModel.onTimePicked(time)
            }
        )
    }

    LaunchedEffect(addEventViewModel) {
        addEventViewModel.resetAddEventScreen()
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Add Event",
                isNavIcon = true,
                navIcon = R.drawable.back,
                onNavClick = {},
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                FloatingLabelBasicTextField(
                    value = clientName,
                    onValueChange = { addEventViewModel.updateClientName(it) },
                    label = "Client Name"
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Event Type",
                        fontFamily = alatsiFont,
                        color = Color.Black,
                        fontSize = 16.sp
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                1.dp,
                                colorResource(R.color.grey),
                                RoundedCornerShape(8.dp)
                            )
                    ) {
                        TextButton(
                            onClick = { addEventViewModel.toggleEventTypeDropdown() },
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = selectedEventType.displayName,
                                    color = Color.Black,
                                    fontFamily = alatsiFont,
                                    fontSize = 16.sp
                                )
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = "Dropdown Arrow",
                                    tint = Color.Black
                                )
                            }
                        }

                        DropdownMenu(
                            expanded = dropdownExpanded,
                            onDismissRequest = { addEventViewModel.closeEventTypeDropdown() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(
                                    RoundedCornerShape(
                                        bottomStart = 8.dp,
                                        bottomEnd = 8.dp
                                    )
                                )
                                .padding(horizontal = 30.dp)
                                .background(MaterialTheme.colorScheme.background)
                        ) {
                            eventTypes.forEach { type ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = type.displayName,
                                            color = Color.Black,
                                            fontFamily = alatsiFont,
                                            fontSize = 16.sp
                                        )
                                    },
                                    onClick = {
                                        addEventViewModel.onEventTypeSelected(type)
                                    },
                                    contentPadding = PaddingValues(horizontal = 12.dp)
                                )
                            }
                        }
                    }
                }


                Column {
                    Text(
                        text = "Date & Time",
                        fontFamily = alatsiFont,
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                        ) {
                            Text(
                                text = "From",
                                fontFamily = alatsiFont,
                                color = Color.Black,
                                fontSize = 12.sp
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(35.dp)
                                    .clickable(onClick = {
                                        addEventViewModel.onDateBoxClick(DatePickerTarget.START_DATE)
                                    })
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(
                                        1.dp,
                                        colorResource(R.color.grey),
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 12.dp),
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.Center)
                                        .padding(4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Text(text = eventStartDate)

                                    Icon(
                                        painter = painterResource(R.drawable.calender),
                                        contentDescription = null,
                                        tint = colorResource(R.color.grey),
                                        modifier = Modifier.size(15.dp)
                                    )
                                }

                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(35.dp)
                                    .clickable(onClick = {
                                        addEventViewModel.onTimeBoxClick(TimePickerTarget.START_TIME)
                                    })
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(
                                        1.dp,
                                        colorResource(R.color.grey),
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 12.dp),
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.Center)
                                        .padding(4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Text(text = eventStartTime)

                                    Icon(
                                        Icons.Default.KeyboardArrowDown,
                                        contentDescription = null,
                                        modifier = Modifier.size(15.dp)
                                    )
                                }

                            }

                        }


                        Column(
                            modifier = Modifier.weight(1f),
                        ) {
                            Text(
                                text = "To",
                                fontFamily = alatsiFont,
                                color = Color.Black,
                                fontSize = 12.sp
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(35.dp)
                                    .clickable(onClick = {
                                        addEventViewModel.onDateBoxClick(DatePickerTarget.END_DATE)
                                    })
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(
                                        1.dp,
                                        colorResource(R.color.grey),
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 12.dp),
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.Center)
                                        .padding(4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Text(text = eventEndDate)

                                    Icon(
                                        painter = painterResource(R.drawable.calender),
                                        contentDescription = null,
                                        tint = colorResource(R.color.grey),
                                        modifier = Modifier.size(15.dp)
                                    )
                                }

                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(35.dp)
                                    .clickable(onClick = {
                                        addEventViewModel.onTimeBoxClick(TimePickerTarget.END_TIME)
                                    })
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(
                                        1.dp,
                                        colorResource(R.color.grey),
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 12.dp),
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.Center)
                                        .padding(4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Text(text = eventEndTime)

                                    Icon(
                                        Icons.Default.KeyboardArrowDown,
                                        contentDescription = null,
                                        modifier = Modifier.size(15.dp)
                                    )
                                }

                            }

                        }

                    }
                }

                FloatingLabelBasicTextField(
                    value = eveLocation,
                    onValueChange = { addEventViewModel.updateEventLocation(it) },
                    label = "Location"
                )

                FloatingLabelBasicTextField(
                    value = eventCity,
                    onValueChange = { addEventViewModel.updateEventCity(it) },
                    label = "City"
                )

                FloatingLabelBasicTextField(
                    value = eventState,
                    onValueChange = { addEventViewModel.updateEventState(it) },
                    label = "State"
                )

                FloatingLabelBasicTextField(
                    value = clientPhoneNo,
                    onValueChange = { addEventViewModel.updateClientPhoneNo(it) },
                    label = "Contact No.",
                    keyboardType = KeyboardType.Number

                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Text(
                        text = "Sub-Events",
                        fontFamily = alatsiFont,
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                1.dp,
                                colorResource(R.color.grey),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 12.dp)
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = 60.dp)
                                .heightIn(max = 300.dp)
                                .padding(bottom = 56.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            val subEventsList =  subEventsMap.toList()

                            items(subEventsList.size) { index ->
                                val subEventId = subEventsList[index].first
                                val subEvent = subEventsMap[subEventId]

                                Log.d("SubEvent", "SubEvent: $subEventId")

                                if (subEvent != null) {
                                    SubEventCard(
                                        subEvent = subEvent,
                                        onDeleteClick = { addEventViewModel.removeSubEvent(subEvent) }
                                    )
                                } else {
                                    Text("Loading sub event...", fontFamily = alatsiFont)
                                }
                            }
                        }

                        Button(
                            onClick = {
                                navController.navigate(Screens.AddSubEventDetailsScreen.route)
                            },
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 4.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.darkGrey),
                                contentColor = Color.Black
                            )
                        ) {
                            Text(text = "Add New", fontFamily = alatsiFont)
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    errorMessage?.let {
                        Text(
                            text = it,
                            color = Color.Red,
                        )
                    }

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            addEventViewModel.updateEventIsSaved(true)
                            addEventViewModel.createNewEvent()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.darkGreen),
                            contentColor = Color.Black
                        )
                    ) {
                        Text(text = "Add New", fontFamily = alatsiFont,)
                    }

                    Button(
                        onClick = {
                            addEventViewModel.updateEventIsSaved(false)
                            addEventViewModel.createNewEvent()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.darkGrey),
                            contentColor = Color.Black
                        )
                    ) {
                        Text(text = "Draft", fontFamily = alatsiFont,)
                    }

                }
            }
        }

        when (state) {

            AddEventState.Idle -> {
                Text(text = "Events Not Loaded Yet",
                    color = Color.Black,
                    fontFamily = alatsiFont,
                    fontSize = 16.sp)
            }

            AddEventState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is AddEventState.Success -> {
                // Success is handled via LaunchedEffect, no UI needed here
            }
            is AddEventState.Error -> {
                // Error UI handled separately
            }
        }
    }

    LaunchedEffect(state) {
        if (state is AddEventState.Success) {

            eventViewModel.resetHasLoadedFlags()
            eventViewModel.resetHasLoadedNextUpcomingEvent()

            navController.navigate(Screens.EventScreen.route) {
                popUpTo(Screens.AddEventDetailsScreen.route) { inclusive = true }
                launchSingleTop = true
            }
            addEventViewModel.resetAddEventState()
            addEventViewModel.resetEventDetails()
        }
    }
}

