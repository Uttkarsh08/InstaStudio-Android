package com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventPages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.uttkarsh.InstaStudio.domain.model.DatePickerTarget
import com.uttkarsh.InstaStudio.domain.model.TimePickerTarget
import com.uttkarsh.InstaStudio.presentation.navigation.Screens
import com.uttkarsh.InstaStudio.presentation.ui.utils.ShowDatePickerDialog
import com.uttkarsh.InstaStudio.presentation.ui.utils.ShowTimePickerDialog
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
        addSubEventViewModel.resetAddSubEventState()
        addSubEventViewModel.resetSubEventDetails()
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Add Event",
                isNavIcon = true,
                navIcon = R.drawable.back,
                isRightIcon = false,
                rightIcon = null,
                onRightIconClicked = {},
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
                            onClick = { addSubEventViewModel.toggleSubEventTypeDropdown() },
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
                                    text = selectedSubEventType.displayName,
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
                                            color = Color.Black,
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
                                        addSubEventViewModel.onDateBoxClick(DatePickerTarget.START_DATE)
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

                                    Text(text = subEventStartDate)

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
                                        addSubEventViewModel.onTimeBoxClick(TimePickerTarget.START_TIME)
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

                                    Text(text = subEventStartTime)

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
                                        addSubEventViewModel.onDateBoxClick(DatePickerTarget.END_DATE)
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

                                    Text(text = subEventEndDate)

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
                                        addSubEventViewModel.onTimeBoxClick(TimePickerTarget.END_TIME)
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

                                    Text(text = subEventEndTime)

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
                    value = subEveLocation,
                    onValueChange = { addSubEventViewModel.updateSubEventLocation(it) },
                    label = "Location"
                )

                FloatingLabelBasicTextField(
                    value = subEventCity,
                    onValueChange = { addSubEventViewModel.updateSubEventCity(it) },
                    label = "City"
                )

                FloatingLabelBasicTextField(
                    value = subEventState,
                    onValueChange = { addSubEventViewModel.updateSubEventState(it) },
                    label = "State"
                )

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
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            addSubEventViewModel.createNewSubEvent()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.darkGreen),
                            contentColor = Color.Black
                        )
                    ) {
                        Text(text = "Add New")
                    }

                }
            }
        }

        when (state) {

            AddSubEventState.Idle -> {
                Text(text = "Events Not Loaded Yet",
                    color = Color.Black,
                    fontFamily = alatsiFont,
                    fontSize = 16.sp)
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

    LaunchedEffect(state) {
        if (state is AddSubEventState.Success) {

            val subEvent = (state as AddSubEventState.Success).response

            addEventsViewModel.addSubEvent(subEvent)
            Log.d("AddSubEventScreen", "Adding SubEvent: ${subEvent.eventId}")

            navController.navigate(Screens.AddEventDetailsScreen.route) {
                popUpTo(Screens.AddSubEventDetailsScreen.route) { inclusive = true }
                launchSingleTop = true
            }
            addSubEventViewModel.resetAddSubEventState()
            addSubEventViewModel.resetSubEventDetails()
        }
    }
}
