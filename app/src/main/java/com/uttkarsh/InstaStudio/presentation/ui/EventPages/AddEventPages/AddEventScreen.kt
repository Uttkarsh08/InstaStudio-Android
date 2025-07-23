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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.uttkarsh.InstaStudio.domain.model.DatePickerTarget
import com.uttkarsh.InstaStudio.domain.model.EventType
import com.uttkarsh.InstaStudio.domain.model.TimePickerTarget
import com.uttkarsh.InstaStudio.presentation.navigation.Screens
import com.uttkarsh.InstaStudio.presentation.ui.utils.NoteMarkTextField
import com.uttkarsh.InstaStudio.presentation.ui.utils.ShowDatePickerDialog
import com.uttkarsh.InstaStudio.presentation.ui.utils.ShowTimePickerDialog
import com.uttkarsh.InstaStudio.presentation.ui.utils.TrailingIconConfig
import com.uttkarsh.InstaStudio.presentation.viewmodel.AddEventViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.EventViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.MemberViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.ResourceViewModel
import com.uttkarsh.InstaStudio.utils.states.AddEventState
import com.uttkarsh.InstaStudio.utils.states.MemberState
import com.uttkarsh.InstaStudio.utils.states.ResourceState

@Composable
fun AddEventScreen(
    addEventViewModel: AddEventViewModel = hiltViewModel(),
    eventViewModel: EventViewModel = hiltViewModel(),
    memberViewModel: MemberViewModel = hiltViewModel(),
    resourceViewModel: ResourceViewModel = hiltViewModel(),
    navController: NavController
) {

    val scrollState = rememberScrollState()
    val alatsiFont = remember { FontFamily(Font(R.font.alatsi)) }

    val eventTypes = addEventViewModel.eventTypes
    val selectedEventType by addEventViewModel.selectedEventType
    val eventTypeDropdownExpanded by addEventViewModel.eventTypeDropdownExpanded
    val eventResourcesDropDownExpanded by addEventViewModel.resourcesDropdownExpanded
    val eventMembersDropDownExpanded by addEventViewModel.membersDropdownExpanded

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
    val isSubEventEnabled = addEventViewModel.isSubEventEnabled

    val state by addEventViewModel.addEventState.collectAsState()
    val availableMembersState by memberViewModel.memberState.collectAsState()
    val availableResourcesState by resourceViewModel.resourceState.collectAsState()

    val availableMembers = when (availableMembersState) {
        is MemberState.ListSuccess -> (availableMembersState as MemberState.ListSuccess).response
        else -> emptyList()
    }
    val availableResources = when(availableResourcesState){
        is ResourceState.ListSuccess -> (availableResourcesState as ResourceState.ListSuccess).response
        else -> emptyList()
    }

    val selectedMembers by addEventViewModel.selectedEventMembers.collectAsState()
    val selectedResources by addEventViewModel.selectedEventResources.collectAsState()

    val subEventsMap by addEventViewModel.subEventsMap.collectAsState()

    val shouldReset = addEventViewModel.shouldResetAddEventScreen
    val errorMessage = (state as? AddEventState.Error)?.message
    val dropdownWidth = remember { mutableStateOf(0) }
    val dropdownHeight = remember { mutableStateOf(0) }

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

    LaunchedEffect(shouldReset) {
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

                    Column(modifier = Modifier.fillMaxWidth()) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    dropdownWidth.value = coordinates.size.width
                                    dropdownHeight.value = coordinates.size.height
                                }
                        ) {
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
                                expanded = eventTypeDropdownExpanded,
                                onDismissRequest = { addEventViewModel.closeEventTypeDropdown() },
                                shape = RoundedCornerShape(15.dp),
                                modifier = Modifier
                                    .width(with(LocalDensity.current) { dropdownWidth.value.toDp() })
                                    .heightIn(max = 300.dp)
                                    .background(MaterialTheme.colorScheme.surfaceContainerLow)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(max = 300.dp)
                                        .verticalScroll(rememberScrollState())
                                ) {
                                    Column {
                                        eventTypes.forEach { type ->
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        text = type.displayName,
                                                        fontFamily = alatsiFont,
                                                        style = MaterialTheme.typography.bodyMedium,
                                                        color = MaterialTheme.colorScheme.onPrimary
                                                    )
                                                },
                                                onClick = {
                                                    addEventViewModel.onEventTypeSelected(type)
                                                    addEventViewModel.closeEventTypeDropdown()
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (selectedEventType == EventType.WEDDING || selectedEventType == EventType.ANNIVERSARY ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            NoteMarkTextField(
                                text = addEventViewModel.groomName,
                                onValueChange = addEventViewModel::updateGroomName,
                                label = "Groom Name",
                                hint = "Harish",
                                isNumberType = false,
                                haveTrailingIcon = false,
                                trailingIconConfig = null,
                                modifier = Modifier.weight(1f)
                            )

                            Text(
                                text = "weds",
                                fontFamily = alatsiFont,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier
                                    .align(Alignment.Bottom)
                                    .padding(bottom = 16.dp)
                            )

                            NoteMarkTextField(
                                text = addEventViewModel.brideName,
                                onValueChange = addEventViewModel::updateBrideName,
                                label = "Bride Name",
                                hint = "Jyoti",
                                isNumberType = false,
                                haveTrailingIcon = false,
                                trailingIconConfig = null,
                                modifier = Modifier.weight(1f)
                            )

                        }
                    } else {
                        NoteMarkTextField(
                            text = clientName,
                            onValueChange = addEventViewModel::updateClientName,
                            label = "Client Name",
                            hint = "Harish",
                            isNumberType = false,
                            haveTrailingIcon = false,
                            trailingIconConfig = null
                        )
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
                            Row(modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Sub-Events",
                                    fontFamily = alatsiFont,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                Switch(
                                    modifier = Modifier.scale(0.7f),
                                    checked = isSubEventEnabled,
                                    onCheckedChange = { addEventViewModel.toggleSubEventEnabled() },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                        uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                        checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                                        uncheckedTrackColor = MaterialTheme.colorScheme.surfaceContainerLowest
                                    )
                                )
                            }

                            if (isSubEventEnabled) {
                                Button(
                                    onClick = {
                                        navController.navigate(Screens.AddSubEventDetailsScreen.route)
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                                        contentColor = MaterialTheme.colorScheme.onPrimary
                                    ),
                                    modifier = Modifier
                                        .height(30.dp)
                                        .wrapContentWidth()
                                        .align(Alignment.CenterVertically)
                                ) {
                                    Text(
                                        text = "Add New",
                                        fontFamily = alatsiFont,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }

                        if(isSubEventEnabled){
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

                    }
                    if(!isSubEventEnabled){

                        Column(modifier = Modifier.fillMaxWidth()) {

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .onGloballyPositioned { coordinates ->
                                        dropdownWidth.value = coordinates.size.width
                                        dropdownHeight.value = coordinates.size.height
                                    }
                            ) {
                                NoteMarkTextField(
                                    text = "Select Resources",
                                    onValueChange = {},
                                    label = "Resources",
                                    hint = "Select Resources",
                                    isNumberType = false,
                                    haveTrailingIcon = true,
                                    trailingIconConfig = TrailingIconConfig.ImageVectorIcon(Icons.Default.KeyboardArrowDown),
                                    readOnly = true,
                                    onClick = { addEventViewModel.toggleResourceDropdown() }
                                )

                                DropdownMenu(
                                    expanded = eventResourcesDropDownExpanded,
                                    onDismissRequest = { addEventViewModel.closeResourceDropdown() },
                                    shape = RoundedCornerShape(15.dp),
                                    offset = with(LocalDensity.current) {
                                        DpOffset(x = 0.dp, y = with(LocalDensity.current) { dropdownHeight.value.toDp() })
                                    },
                                    modifier = Modifier
                                        .width(with(LocalDensity.current) { dropdownWidth.value.toDp() })
                                        .heightIn(max = 300.dp)
                                        .background(MaterialTheme.colorScheme.surfaceContainerLow)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .heightIn(max = 300.dp)
                                            .verticalScroll(rememberScrollState())
                                    ) {
                                        Column {
                                            availableResources.forEach { resource ->
                                                val isSelected = selectedResources.contains(resource.resourceId)
                                                DropdownMenuItem(
                                                    text = {
                                                        Row(
                                                            modifier = Modifier.fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.SpaceBetween
                                                        ) {
                                                            Text(
                                                                text = resource.resourceName,
                                                                fontFamily = alatsiFont,
                                                                style = MaterialTheme.typography.bodyMedium,
                                                                color = MaterialTheme.colorScheme.onPrimary
                                                            )
                                                            Text(
                                                                text = "₹${resource.resourcePrice}",
                                                                fontFamily = alatsiFont,
                                                                style = MaterialTheme.typography.bodySmall,
                                                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                                                            )

                                                            Checkbox(
                                                                checked = isSelected,
                                                                onCheckedChange = {
                                                                    addEventViewModel.onResourceSelected(resource)
                                                                },
                                                                colors = CheckboxDefaults.colors(
                                                                    checkedColor = MaterialTheme.colorScheme.primaryContainer,
                                                                    uncheckedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                                                    checkmarkColor = MaterialTheme.colorScheme.onPrimary
                                                                )
                                                            )
                                                        }
                                                    },
                                                    onClick = {
                                                        addEventViewModel.onResourceSelected(resource)
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        Column(modifier = Modifier.fillMaxWidth()) {

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .onGloballyPositioned { coordinates ->
                                        dropdownWidth.value = coordinates.size.width
                                        dropdownHeight.value = coordinates.size.height
                                    }
                            ) {
                                NoteMarkTextField(
                                    text = "Select Members",
                                    onValueChange = {},
                                    label = "Members",
                                    hint = "Select Members",
                                    isNumberType = false,
                                    haveTrailingIcon = true,
                                    trailingIconConfig = TrailingIconConfig.ImageVectorIcon(Icons.Default.KeyboardArrowDown),
                                    readOnly = true,
                                    onClick = { addEventViewModel.toggleMemberDropdown() }
                                )

                                DropdownMenu(
                                    expanded = eventMembersDropDownExpanded,
                                    onDismissRequest = { addEventViewModel.closeMemberDropdown() },
                                    shape = RoundedCornerShape(15.dp),
                                    offset = with(LocalDensity.current) {
                                        DpOffset(x = 0.dp, y = with(LocalDensity.current) { dropdownHeight.value.toDp() })
                                    },
                                    modifier = Modifier
                                        .width(with(LocalDensity.current) { dropdownWidth.value.toDp() })
                                        .heightIn(max = 300.dp)
                                        .background(MaterialTheme.colorScheme.surfaceContainerLow)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .heightIn(max = 300.dp)
                                            .verticalScroll(rememberScrollState())
                                    ) {
                                        Column {
                                            availableMembers.forEach { member ->
                                                val isSelected = selectedMembers.contains(member.memberId)
                                                DropdownMenuItem(
                                                    text = {
                                                        Row(
                                                            modifier = Modifier.fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.SpaceBetween
                                                        ) {
                                                            Text(
                                                                text = member.memberName,
                                                                fontFamily = alatsiFont,
                                                                style = MaterialTheme.typography.bodyMedium,
                                                                color = MaterialTheme.colorScheme.onPrimary
                                                            )
                                                            Text(
                                                                text = "₹${member.salary}",
                                                                fontFamily = alatsiFont,
                                                                style = MaterialTheme.typography.bodySmall,
                                                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                                                            )

                                                            Checkbox(
                                                                checked = isSelected,
                                                                onCheckedChange = {
                                                                    addEventViewModel.onMemberSelected(member)
                                                                },
                                                                colors = CheckboxDefaults.colors(
                                                                    checkedColor = MaterialTheme.colorScheme.primaryContainer,
                                                                    uncheckedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                                                    checkmarkColor = MaterialTheme.colorScheme.onPrimary
                                                                )
                                                            )
                                                        }
                                                    },
                                                    onClick = {
                                                        addEventViewModel.onMemberSelected(member)
                                                    }
                                                )
                                            }
                                        }
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
            addEventViewModel.markAddEventScreenForReset()

            navController.navigate(Screens.EventScreen.route) {
                popUpTo(Screens.AddEventDetailsScreen.route) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    LaunchedEffect(eventStartDate, eventStartTime, eventEndDate, eventEndTime) {
        memberViewModel.getAvailableMembers(eventStartDate, eventStartTime, eventEndDate, eventEndTime)
        resourceViewModel.getAvailableResources(eventStartDate, eventStartTime, eventEndDate, eventEndTime)
    }
}

