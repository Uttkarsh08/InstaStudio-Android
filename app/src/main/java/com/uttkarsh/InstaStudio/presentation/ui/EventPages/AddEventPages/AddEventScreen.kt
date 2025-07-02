package com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventPages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uttkarsh.InstaStudio.R
import com.uttkarsh.InstaStudio.presentation.ui.utils.AppTopBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.uttkarsh.InstaStudio.domain.model.DatePickerTarget
import com.uttkarsh.InstaStudio.domain.model.TimePickerTarget
import com.uttkarsh.InstaStudio.presentation.navigation.Screens
import com.uttkarsh.InstaStudio.presentation.ui.utils.NoteMarkTextField
import com.uttkarsh.InstaStudio.presentation.ui.utils.ShowDatePickerDialog
import com.uttkarsh.InstaStudio.presentation.ui.utils.ShowTimePickerDialog
import com.uttkarsh.InstaStudio.presentation.ui.utils.TrailingIconConfig
import com.uttkarsh.InstaStudio.presentation.viewmodel.AddEventViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.AddSubEventViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.EventViewModel
import com.uttkarsh.InstaStudio.utils.states.AddEventState

@Composable
fun AddEventScreen(
    addEventViewModel: AddEventViewModel = hiltViewModel(),
    eventViewModel: EventViewModel = hiltViewModel(),
    addSubEventViewModel: AddSubEventViewModel = hiltViewModel(),
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
        modifier = Modifier
            .fillMaxSize(),
        contentWindowInsets = WindowInsets.statusBars,
        topBar = {
            AppTopBar(
                color = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                title = "Add Event",
                isNavIcon = true,
                navIcon = R.drawable.back,
                isRightIcon = false,
                rightIcon = null,
                onRightIconClicked = {},
                onNavClick = {navController.navigateUp()}
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .navigationBarsPadding()
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
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    NoteMarkTextField(
                        text = clientName,
                        onValueChange = addEventViewModel::updateClientName,
                        label = "Client Name",
                        hint = "Harish & Jyoti",
                        isNumberType = false,
                        haveTrailingIcon = false,
                        trailingIconConfig = null
                    )

                    NoteMarkTextField(
                        text = selectedEventType.displayName,
                        onValueChange = {},
                        label = "Event Type",
                        hint = selectedEventType.displayName,
                        isNumberType = false,
                        haveTrailingIcon = true,
                        trailingIconConfig = TrailingIconConfig.ImageVectorIcon(Icons.Default.KeyboardArrowDown),
                        readOnly = true,
                        onClick = { addEventViewModel.toggleEventTypeDropdown() }
                    )

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
                                        color = MaterialTheme.colorScheme.onPrimary,
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


                    Column {
                        Text(
                            text = "Date & Time",
                            fontFamily = alatsiFont,
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                            ) {

                                NoteMarkTextField(
                                    text = eventStartDate,
                                    onValueChange = {},
                                    label = "From",
                                    hint = eventStartDate,
                                    isNumberType = false,
                                    haveTrailingIcon = true,
                                    trailingIconConfig = TrailingIconConfig.ResourceIcon(R.drawable.calender),
                                    readOnly = true,
                                    onClick = { addEventViewModel.onDateBoxClick(DatePickerTarget.START_DATE) }
                                )

                                Spacer(Modifier.height(8.dp))

                                NoteMarkTextField(
                                    text = eventStartTime,
                                    onValueChange = {},
                                    hint = eventStartTime,
                                    label = null,
                                    isNumberType = false,
                                    haveTrailingIcon = true,
                                    trailingIconConfig = TrailingIconConfig.ImageVectorIcon(Icons.Default.KeyboardArrowDown),
                                    readOnly = true,
                                    onClick = { addEventViewModel.onTimeBoxClick(TimePickerTarget.START_TIME) }
                                )
                            }

                            Column(
                                modifier = Modifier.weight(1f),
                            ) {

                                NoteMarkTextField(
                                    text = eventEndDate,
                                    onValueChange = {},
                                    label = "To",
                                    hint = eventEndDate,
                                    isNumberType = false,
                                    haveTrailingIcon = true,
                                    trailingIconConfig = TrailingIconConfig.ResourceIcon(R.drawable.calender),
                                    readOnly = true,
                                    onClick = { addEventViewModel.onDateBoxClick(DatePickerTarget.END_DATE) }
                                )

                                Spacer(Modifier.height(8.dp))

                                NoteMarkTextField(
                                    text = eventEndTime,
                                    onValueChange = {},
                                    hint = eventEndTime,
                                    label = null,
                                    isNumberType = false,
                                    haveTrailingIcon = true,
                                    trailingIconConfig = TrailingIconConfig.ImageVectorIcon(Icons.Default.KeyboardArrowDown),
                                    readOnly = true,
                                    onClick = { addEventViewModel.onTimeBoxClick(TimePickerTarget.END_TIME) }
                                )
                            }
                        }
                    }

                    NoteMarkTextField(
                        text = eveLocation,
                        onValueChange = addEventViewModel::updateEventLocation,
                        label = "Location",
                        hint = "LPU",
                        isNumberType = false,
                        haveTrailingIcon = false,
                        trailingIconConfig = null
                    )

                    NoteMarkTextField(
                        text = eventCity,
                        onValueChange = addEventViewModel::updateEventCity,
                        label = "City",
                        hint = "Phagwara",
                        isNumberType = false,
                        haveTrailingIcon = false,
                        trailingIconConfig = null
                    )

                    NoteMarkTextField(
                        text = eventState,
                        onValueChange = addEventViewModel::updateEventState,
                        label = "State",
                        hint = "Punjab",
                        isNumberType = false,
                        haveTrailingIcon = false,
                        trailingIconConfig = null
                    )

                    NoteMarkTextField(
                        text = clientPhoneNo,
                        onValueChange = addEventViewModel::updateClientPhoneNo,
                        label = "Contact No.",
                        hint = "7088XXXXXX",
                        isNumberType = true,
                        haveTrailingIcon = false,
                        trailingIconConfig = null
                    )


                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Sub-Events",
                                fontFamily = alatsiFont,
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Button(
                                onClick = {
                                    addSubEventViewModel.markAddSubEventScreenForReset()
                                    navController.navigate(Screens.AddSubEventDetailsScreen.route)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                ),
                                modifier = Modifier
                                    .height(30.dp)
                                    .wrapContentWidth()
                                    .align(Alignment.Bottom)
                            ) {
                                Text(text = "Add New", fontFamily = alatsiFont, fontSize = 13.sp)
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.surfaceContainerHigh,
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 12.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .defaultMinSize(minHeight = 160.dp)
                                    .padding(top = 8.dp, bottom = 8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {

                                subEventsMap.toList().map { cur ->
                                    val subEvent = cur.second
                                    if (true) {
                                        SubEventCard(
                                            subEvent = subEvent,
                                            onDeleteClick = {
                                                addEventViewModel.removeSubEvent(
                                                    subEvent
                                                )
                                            }
                                        )
                                    } else {
                                        Text("Loading sub event...", fontFamily = alatsiFont)
                                    }

                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        errorMessage?.let {
                            Text(
                                text = it,
                                color = MaterialTheme.colorScheme.error,
                            )
                        }

                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Button(
                            onClick = {
                                addEventViewModel.updateEventIsSaved(true)
                                addEventViewModel.createNewEvent()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.tertiary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            modifier = Modifier
                                .height(35.dp)
                                .width(100.dp)
                        ) {
                            Text(text = "Save", fontFamily = alatsiFont, fontSize = 15.sp)
                        }

                        Button(
                            onClick = {
                                addEventViewModel.updateEventIsSaved(false)
                                addEventViewModel.createNewEvent()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            modifier = Modifier
                                .height(35.dp)
                                .width(100.dp)
                        ) {
                            Text(text = "Draft", fontFamily = alatsiFont, fontSize = 15.sp)
                        }

                    }
                }
            }
        }

        when (state) {

            AddEventState.Idle -> {
                Text(
                    text = "Events Not Loaded Yet",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontFamily = alatsiFont,
                    fontSize = 16.sp
                )
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

