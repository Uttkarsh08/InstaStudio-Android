package com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventPages

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import com.uttkarsh.InstaStudio.presentation.navigation.Screens
import com.uttkarsh.InstaStudio.presentation.ui.utils.NoteMarkTextField
import com.uttkarsh.InstaStudio.presentation.ui.utils.TrailingIconConfig
import com.uttkarsh.InstaStudio.presentation.viewmodel.AddEventViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.MemberViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.ResourceViewModel
import com.uttkarsh.InstaStudio.utils.states.MemberState
import com.uttkarsh.InstaStudio.utils.states.ResourceState

@Composable
fun SubEventSection(
    addEventViewModel: AddEventViewModel,
    resourceViewModel: ResourceViewModel,
    memberViewModel: MemberViewModel,
    alatsiFont: FontFamily,
    dropdownWidth: MutableIntState,
    dropdownHeight: MutableIntState,
    navController: NavController
) {

    val isSubEventEnabled by addEventViewModel::isSubEventEnabled
    val subEventsMap by addEventViewModel.subEventsMap.collectAsState()
    val eventResourcesDropDownExpanded by addEventViewModel.resourcesDropdownExpanded
    val eventMembersDropDownExpanded by addEventViewModel.membersDropdownExpanded
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
        }else{

            var offsetY by remember { mutableStateOf(0.dp) }
            val density = LocalDensity.current
            val scrollState = rememberScrollState()

            Column(modifier = Modifier.fillMaxWidth()) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            dropdownWidth.intValue = coordinates.size.width
                            dropdownHeight.intValue = coordinates.size.height
                            offsetY = with(density) { coordinates.size.height.toDp() }
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
                        onClick = {
                            addEventViewModel.toggleResourceDropdown()
                        }
                    )

                    if (eventResourcesDropDownExpanded) {
                        Popup(
                            alignment = Alignment.TopStart,
                            offset = IntOffset(0, with(density) { offsetY.roundToPx() }),
                            onDismissRequest = {
                                addEventViewModel.closeResourceDropdown()
                            }
                        ) {
                            Surface(
                                shape = RoundedCornerShape(15.dp),
                                color = MaterialTheme.colorScheme.surfaceContainerLow,
                                tonalElevation = 2.dp,
                                shadowElevation = 2.dp,
                                modifier = Modifier
                                    .width(with(density) { dropdownWidth.intValue.toDp() })
                                    .shadow(
                                        elevation = 2.dp,
                                        shape = RoundedCornerShape(15.dp),
                                        ambientColor = Color.Black.copy(alpha = 0.15f),
                                        spotColor = Color.Black.copy(alpha = 0.2f)
                                    )
                                    .heightIn(max = 300.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .verticalScroll(scrollState)
                                        .padding(vertical = 8.dp)
                                ) {
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

            }
            Spacer(Modifier.height(16.dp))
            Column(modifier = Modifier.fillMaxWidth()) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            dropdownWidth.intValue = coordinates.size.width
                            dropdownHeight.intValue = coordinates.size.height
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
                        onClick = {
                            addEventViewModel.toggleMemberDropdown()
                        }
                    )

                    if (eventMembersDropDownExpanded) {
                        Popup(
                            alignment = Alignment.TopStart,
                            offset = IntOffset(0, with(density) { dropdownHeight.intValue }),
                            onDismissRequest = {
                                addEventViewModel.closeMemberDropdown()
                            }
                        ) {
                            Surface(
                                shape = RoundedCornerShape(15.dp),
                                color = MaterialTheme.colorScheme.surfaceContainerLow,
                                tonalElevation = 2.dp,
                                shadowElevation = 2.dp,
                                modifier = Modifier
                                    .width(with(density) { dropdownWidth.intValue.toDp() })
                                    .shadow(
                                        elevation = 2.dp,
                                        shape = RoundedCornerShape(15.dp),
                                        ambientColor = Color.Black.copy(alpha = 0.15f),
                                        spotColor = Color.Black.copy(alpha = 0.2f)
                                    )
                                    .heightIn(max = 300.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .verticalScroll(scrollState)
                                        .padding(vertical = 8.dp)
                                ) {
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
        }

    }
}
