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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
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
import com.uttkarsh.InstaStudio.utils.states.AddSubEventState

@Composable
fun AddSubEventScreen(
    addSubEventViewModel: AddSubEventViewModel = hiltViewModel(),
    addEventsViewModel: AddEventViewModel = hiltViewModel(),
    navController: NavController
) {

    val scrollState = rememberScrollState()
    val alatsiFont = remember { FontFamily(Font(R.font.alatsi)) }

    val subEventTypes = addSubEventViewModel.subEventTypes
    val selectedSubEventType by addSubEventViewModel.selectedSubEventType
    val dropdownExpanded by addSubEventViewModel.subEventTypeDropdownExpanded

    val subEventStartDate = addSubEventViewModel.subEventStartDate
    val subEventEndDate = addSubEventViewModel.subEventEndDate
    val subEventStartTime = addSubEventViewModel.subEventStartTime
    val subEventEndTime = addSubEventViewModel.subEventEndTime

    val subEveLocation = addSubEventViewModel.subEventLocation
    val subEventCity = addSubEventViewModel.subEventCity
    val subEventState = addSubEventViewModel.subEventState

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
                        text = selectedSubEventType.displayName,
                        onValueChange = {},
                        label = "Event Type",
                        hint = selectedSubEventType.displayName,
                        isNumberType = false,
                        haveTrailingIcon = true,
                        trailingIconConfig = TrailingIconConfig.ImageVectorIcon(Icons.Default.KeyboardArrowDown),
                        readOnly = true,
                        onClick = { addSubEventViewModel.toggleSubEventTypeDropdown() }
                    )

                    DropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = { addSubEventViewModel.closeSubEventTypeDropdown() },
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
                        subEventTypes.forEach { type ->
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
                                    addSubEventViewModel.onSubEventTypeSelected(type)
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
                                    text = subEventStartDate,
                                    onValueChange = {},
                                    label = "From",
                                    hint = subEventStartDate,
                                    isNumberType = false,
                                    haveTrailingIcon = true,
                                    trailingIconConfig = TrailingIconConfig.ResourceIcon(R.drawable.calender),
                                    readOnly = true,
                                    onClick = { addSubEventViewModel.onDateBoxClick(DatePickerTarget.START_DATE) }
                                )

                                Spacer(Modifier.height(8.dp))

                                NoteMarkTextField(
                                    text = subEventStartTime,
                                    onValueChange = {},
                                    hint = subEventStartTime,
                                    label = null,
                                    isNumberType = false,
                                    haveTrailingIcon = true,
                                    trailingIconConfig = TrailingIconConfig.ImageVectorIcon(Icons.Default.KeyboardArrowDown),
                                    readOnly = true,
                                    onClick = { addSubEventViewModel.onTimeBoxClick(TimePickerTarget.START_TIME) }
                                )
                            }

                            Column(
                                modifier = Modifier.weight(1f),
                            ) {

                                NoteMarkTextField(
                                    text = subEventEndDate,
                                    onValueChange = {},
                                    label = "To",
                                    hint = subEventEndDate,
                                    isNumberType = false,
                                    haveTrailingIcon = true,
                                    trailingIconConfig = TrailingIconConfig.ResourceIcon(R.drawable.calender),
                                    readOnly = true,
                                    onClick = { addSubEventViewModel.onDateBoxClick(DatePickerTarget.END_DATE) }
                                )

                                Spacer(Modifier.height(8.dp))

                                NoteMarkTextField(
                                    text = subEventEndTime,
                                    onValueChange = {},
                                    hint = subEventEndTime,
                                    label = null,
                                    isNumberType = false,
                                    haveTrailingIcon = true,
                                    trailingIconConfig = TrailingIconConfig.ImageVectorIcon(Icons.Default.KeyboardArrowDown),
                                    readOnly = true,
                                    onClick = { addSubEventViewModel.onTimeBoxClick(TimePickerTarget.END_TIME) }
                                )
                            }
                        }
                    }

                    NoteMarkTextField(
                        text = subEveLocation,
                        onValueChange = { addSubEventViewModel.updateSubEventLocation(it) },
                        label = "Location",
                        hint = "LPU",
                        isNumberType = false,
                        haveTrailingIcon = false,
                        trailingIconConfig = null
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                        ) {
                            NoteMarkTextField(
                                text = subEventCity,
                                onValueChange = { addSubEventViewModel.updateSubEventCity(it) },
                                label = "City",
                                hint = "Phagwara",
                                isNumberType = false,
                                haveTrailingIcon = false,
                                trailingIconConfig = null
                            )
                        }

                        Column(
                            modifier = Modifier.weight(1f),
                        ) {

                            NoteMarkTextField(
                                text = subEventState,
                                onValueChange = { addSubEventViewModel.updateSubEventState(it) },
                                label = "State",
                                hint = "Punjab",
                                isNumberType = false,
                                haveTrailingIcon = false,
                                trailingIconConfig = null
                            )
                        }
                    }

                    NoteMarkTextField(
                        text = selectedSubEventType.displayName,
                        onValueChange = {},
                        label = "Members",
                        hint = selectedSubEventType.displayName,
                        isNumberType = false,
                        haveTrailingIcon = true,
                        trailingIconConfig = TrailingIconConfig.ImageVectorIcon(Icons.Default.KeyboardArrowDown),
                        readOnly = true,
                        onClick = { addSubEventViewModel.toggleSubEventTypeDropdown() }
                    )

                    NoteMarkTextField(
                        text = selectedSubEventType.displayName,
                        onValueChange = {},
                        label = "Resources",
                        hint = selectedSubEventType.displayName,
                        isNumberType = false,
                        haveTrailingIcon = true,
                        trailingIconConfig = TrailingIconConfig.ImageVectorIcon(Icons.Default.KeyboardArrowDown),
                        readOnly = true,
                        onClick = { addSubEventViewModel.toggleSubEventTypeDropdown() }
                    )

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
