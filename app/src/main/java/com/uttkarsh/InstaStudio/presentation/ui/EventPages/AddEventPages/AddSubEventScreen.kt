package com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventPages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventPages.utils.DateTimeSection
import com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventPages.utils.LocationSection
import com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventPages.utils.MemberSelector
import com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventPages.utils.ResourceSelector
import com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventPages.utils.subEvent.SubEventTypeSelector
import com.uttkarsh.InstaStudio.presentation.ui.utils.ShowDatePickerDialog
import com.uttkarsh.InstaStudio.presentation.ui.utils.ShowTimePickerDialog
import com.uttkarsh.InstaStudio.presentation.viewmodel.AddEventViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.AddSubEventViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.MemberViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.ResourceViewModel
import com.uttkarsh.InstaStudio.utils.states.AddSubEventState
import com.uttkarsh.InstaStudio.utils.states.MemberState
import com.uttkarsh.InstaStudio.utils.states.ResourceState

@Composable
fun AddSubEventScreen(
    addSubEventViewModel: AddSubEventViewModel = hiltViewModel(),
    addEventsViewModel: AddEventViewModel = hiltViewModel(),
    memberViewModel: MemberViewModel = hiltViewModel(),
    resourceViewModel: ResourceViewModel = hiltViewModel(),
    navController: NavController
) {

    val alatsiFont = remember { FontFamily(Font(R.font.alatsi)) }

    val subEventStartDate = addSubEventViewModel.subEventStartDate
    val subEventEndDate = addSubEventViewModel.subEventEndDate
    val subEventStartTime = addSubEventViewModel.subEventStartTime
    val subEventEndTime = addSubEventViewModel.subEventEndTime

    val selectedMembers by addSubEventViewModel.selectedSubEventMembers.collectAsState()
    val selectedResources by addSubEventViewModel.selectedSubEventResources.collectAsState()

    val availableMembersState by memberViewModel.memberState.collectAsState()
    val availableResourcesState by resourceViewModel.resourceState.collectAsState()

    val availableMembers = (availableMembersState as? MemberState.ListSuccess)?.response ?: emptyList()
    val availableResources = (availableResourcesState as? ResourceState.ListSuccess)?.response ?: emptyList()
    val datePickerTarget = addSubEventViewModel.datePickerTarget
    val timePickerTarget = addSubEventViewModel.timePickerTarget

    val state by addSubEventViewModel.addSubEventState.collectAsState()

    val errorMessage = (state as? AddSubEventState.Error)?.message

    if (datePickerTarget != null) {
        ShowDatePickerDialog(
            onDateSelected = { date ->
                addSubEventViewModel.onDatePicked(date)
            }
        )
    }

    if (timePickerTarget != null) {
        ShowTimePickerDialog(
            onTimeSelected = { time ->
                addSubEventViewModel.onTimePicked(time)
            }
        )
    }

    LaunchedEffect(Unit) {
        addSubEventViewModel.resetAddSubEventScreen()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        contentWindowInsets = WindowInsets.statusBars,
        topBar = {
            AppTopBar(
                color = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                title = "Add SubEvent",
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
                    item { SubEventTypeSelector(addSubEventViewModel, alatsiFont) }
                    item {
                        DateTimeSection(
                            eventStartDate = subEventStartDate,
                            eventStartTime = subEventStartTime,
                            eventEndDate = subEventEndDate,
                            eventEndTime = subEventEndTime,
                            onStartDateClick = {
                                addSubEventViewModel.onDateBoxClick(
                                    DatePickerTarget.START_DATE
                                )
                            },
                            onStartTimeClick = {
                                addSubEventViewModel.onTimeBoxClick(
                                    TimePickerTarget.START_TIME
                                )
                            },
                            onEndDateClick = { addSubEventViewModel.onDateBoxClick(DatePickerTarget.END_DATE) },
                            onEndTimeClick = { addSubEventViewModel.onTimeBoxClick(TimePickerTarget.END_TIME) },
                            alatsiFont = alatsiFont
                        )

                    }
                    item {
                        LocationSection(
                            location = addSubEventViewModel.subEventLocation,
                            onLocationChange = addSubEventViewModel::updateSubEventLocation,
                            city = addSubEventViewModel.subEventCity,
                            onCityChange = addSubEventViewModel::updateSubEventCity,
                            state = addSubEventViewModel.subEventState,
                            onStateChange = addSubEventViewModel::updateSubEventState,
                            cityStateInRow = true
                        )
                    }
                    item {
                        ResourceSelector(
                            availableResources = availableResources,
                            selectedResources = selectedResources,
                            onResourceSelected = addSubEventViewModel::onResourceSelected,
                            expanded = addSubEventViewModel.resourcesDropdownExpanded.value,
                            onToggleExpand = addSubEventViewModel::toggleResourceDropdown,
                            onDismiss = addSubEventViewModel::closeResourceDropdown,
                            alatsiFont = alatsiFont
                        )
                    }
                    item { 
                        MemberSelector(
                            availableMembers = availableMembers,
                            selectedMembers = selectedMembers,
                            onMemberSelected = addSubEventViewModel::onMemberSelected,
                            expanded = addSubEventViewModel.membersDropdownExpanded.value,
                            onToggleExpand = addSubEventViewModel::toggleMemberDropdown,
                            onDismiss = addSubEventViewModel::closeMemberDropdown,
                            alatsiFont = alatsiFont
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

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = {
                                    addSubEventViewModel.createNewSubEvent()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.tertiary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                ),
                                modifier = Modifier
                                    .height(35.dp)
                                    .width(150.dp)
                            ) {
                                Text(text = "Save", fontFamily = alatsiFont, fontSize = 15.sp)
                            }
                        }
                    }

                }

            }
        }

        when (state) {

            AddSubEventState.Idle -> {
                Text(
                    text = "Events Not Loaded Yet",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontFamily = alatsiFont,
                    fontSize = 16.sp
                )
            }

            AddSubEventState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is AddSubEventState.Success -> {
                // Success is handled via LaunchedEffect, no UI needed here
            }

            is AddSubEventState.Error -> {
                // Error UI handled separately
            }
        }
    }

    LaunchedEffect(key1 = state is AddSubEventState.Success) {
        if (state is AddSubEventState.Success) {

            val subEvent = (state as AddSubEventState.Success).response

            addEventsViewModel.addSubEvent(subEvent)
            Log.d("AddSubEventScreen", "Adding SubEvent: ${subEvent.eventId}")

            addSubEventViewModel.resetAddSubEventScreen()
            navController.navigate(Screens.AddEventDetailsScreen.route) {
                popUpTo(Screens.AddSubEventDetailsScreen.route) { inclusive = true }
                launchSingleTop = true
            }
        }
    }
}
