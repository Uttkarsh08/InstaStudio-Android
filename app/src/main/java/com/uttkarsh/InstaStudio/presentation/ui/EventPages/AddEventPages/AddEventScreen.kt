package com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventPages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
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
import com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventPages.utils.event.ClientInfoSection
import com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventPages.utils.event.EventTypeSelector
import com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventPages.utils.LocationSection
import com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventPages.utils.event.SaveDraftButtons
import com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventPages.utils.event.SubEventSection
import com.uttkarsh.InstaStudio.presentation.ui.utils.ShowDatePickerDialog
import com.uttkarsh.InstaStudio.presentation.ui.utils.ShowTimePickerDialog
import com.uttkarsh.InstaStudio.presentation.viewmodel.AddEventViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.EventViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.MemberViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.ResourceViewModel
import com.uttkarsh.InstaStudio.utils.states.AddEventState

@Composable
fun AddEventScreen(
    addEventViewModel: AddEventViewModel = hiltViewModel(),
    eventViewModel: EventViewModel = hiltViewModel(),
    memberViewModel: MemberViewModel = hiltViewModel(),
    resourceViewModel: ResourceViewModel = hiltViewModel(),
    navController: NavController
) {

    val alatsiFont = remember { FontFamily(Font(R.font.alatsi)) }
    val eventStartDate = addEventViewModel.eventStartDate
    val eventEndDate = addEventViewModel.eventEndDate
    val eventStartTime = addEventViewModel.eventStartTime
    val eventEndTime = addEventViewModel.eventEndTime

    val datePickerTarget = addEventViewModel.datePickerTarget
    val timePickerTarget = addEventViewModel.timePickerTarget
    val isSubEventEnabled by addEventViewModel::isSubEventEnabled

    val state by addEventViewModel.addEventState.collectAsState()

    val shouldReset = addEventViewModel.shouldResetAddEventScreen
    val errorMessage = (state as? AddEventState.Error)?.message

    if (datePickerTarget != null) {
        val initialDate = when (datePickerTarget) {
            DatePickerTarget.START_DATE -> eventStartDate
            DatePickerTarget.END_DATE -> eventEndDate
        }

        ShowDatePickerDialog(
            onDateSelected = { date ->
                addEventViewModel.onDatePicked(date)
            },
            onDismiss = {
                addEventViewModel.onDateDialogDismiss()
            },
            initialDate = initialDate,
            alatsiFont = alatsiFont
        )
    }

    if (timePickerTarget != null) {
        val initialTime = when (timePickerTarget) {
            TimePickerTarget.START_TIME -> eventStartTime
            TimePickerTarget.END_TIME -> eventEndTime
        }
        ShowTimePickerDialog(
            onTimeSelected = { time ->
                addEventViewModel.onTimePicked(time)
            },
            onDismiss = {
                addEventViewModel.onTimeDialogDismiss()
            },
            initialTime = initialTime,
            alatsiFont
        )
    }

    LaunchedEffect(shouldReset) {
        Log.d("AddEventScreen Before", isSubEventEnabled.toString())
        addEventViewModel.resetAddEventScreen()
        Log.d("AddEventScreen After", isSubEventEnabled.toString())
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
                onNavClick = { navController.navigateUp() }
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            horizontal = 32.dp,
                            vertical = 24.dp
                        ),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item { EventTypeSelector(addEventViewModel, alatsiFont) }
                    item { ClientInfoSection(addEventViewModel, alatsiFont) }
                    item {
                        LocationSection(
                            location = addEventViewModel.eventLocation,
                            onLocationChange = addEventViewModel::updateEventLocation,
                            city = addEventViewModel.eventCity,
                            onCityChange = addEventViewModel::updateEventCity,
                            state = addEventViewModel.eventState,
                            onStateChange = addEventViewModel::updateEventState,
                            contactNumber = addEventViewModel.clientPhoneNo,
                            onContactNumberChange = addEventViewModel::updateClientPhoneNo
                        )

                    }
                    item {
                        SubEventSection(
                            addEventViewModel,
                            resourceViewModel,
                            memberViewModel,
                            eventStartDate = eventStartDate,
                            eventStartTime = eventStartTime,
                            eventEndDate = eventEndDate,
                            eventEndTime = eventEndTime,
                            alatsiFont,
                            navController
                        )
                    }
                    item {
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
                    }
                    item { SaveDraftButtons(addEventViewModel, alatsiFont) }
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
            addEventViewModel.markAddEventScreenForReset()

            navController.navigate(Screens.EventScreen.route) {
                popUpTo(Screens.AddEventDetailsScreen.route) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    LaunchedEffect(eventStartDate, eventStartTime, eventEndDate, eventEndTime, isSubEventEnabled
    ) {
        if (!isSubEventEnabled) {
            memberViewModel.getAvailableMembers(
                eventStartDate,
                eventStartTime,
                eventEndDate,
                eventEndTime
            )
            resourceViewModel.getAvailableResources(
                eventStartDate,
                eventStartTime,
                eventEndDate,
                eventEndTime
            )
        }
    }
}

