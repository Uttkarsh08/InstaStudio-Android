package com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventPages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MenuAnchorType
import androidx.navigation.NavController
import com.uttkarsh.InstaStudio.presentation.navigation.Screens
import com.uttkarsh.InstaStudio.presentation.ui.utils.NoteMarkTextField
import com.uttkarsh.InstaStudio.presentation.ui.utils.TrailingIconConfig
import com.uttkarsh.InstaStudio.presentation.viewmodel.AddEventViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.MemberViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.ResourceViewModel
import com.uttkarsh.InstaStudio.utils.states.MemberState
import com.uttkarsh.InstaStudio.utils.states.ResourceState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubEventSection(
    addEventViewModel: AddEventViewModel,
    resourceViewModel: ResourceViewModel,
    memberViewModel: MemberViewModel,
    alatsiFont: FontFamily,
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
    val availableResources = when (availableResourcesState) {
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
            Row(
                modifier = Modifier.weight(1f),
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

        if (isSubEventEnabled) {
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
        } else {

            val scrollState = rememberScrollState()

            Column(modifier = Modifier.fillMaxWidth()) {

                ExposedDropdownMenuBox(
                    expanded = eventResourcesDropDownExpanded,
                    onExpandedChange = {
                        addEventViewModel.toggleResourceDropdown()
                    }
                ) {
                    NoteMarkTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(type = MenuAnchorType.PrimaryNotEditable),
                        text = "Select Resources",
                        onValueChange = {},
                        label = "Resources",
                        hint = "Select Resources",
                        isNumberType = false,
                        readOnly = true,
                        trailingIconConfig = TrailingIconConfig.ImageVectorIcon(
                            if (eventResourcesDropDownExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown
                        ),
                        haveTrailingIcon = true,
                        onClick = {}
                    )

                    ExposedDropdownMenu(
                        expanded = eventResourcesDropDownExpanded,
                        onDismissRequest = {
                            addEventViewModel.closeResourceDropdown()
                        },
                        modifier = Modifier
                            .exposedDropdownSize(true)
                            .background(MaterialTheme.colorScheme.surfaceContainerLow)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .heightIn(max = 300.dp)
                                .verticalScroll(scrollState)
                        ) {
                            availableResources.forEach { resource ->
                                val isSelected = selectedResources.contains(resource.resourceId)
                                DropdownMenuItem(
                                    text = {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = resource.resourceName,
                                                fontFamily = alatsiFont,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = MaterialTheme.colorScheme.onPrimary,
                                                modifier = Modifier.weight(1f)
                                            )
                                            Text(
                                                text = "₹${resource.resourcePrice}",
                                                fontFamily = alatsiFont,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                                                modifier = Modifier.padding(horizontal = 8.dp)
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
                                    },
                                    contentPadding = PaddingValues(horizontal = 16.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = eventMembersDropDownExpanded,
                    onExpandedChange = { addEventViewModel.toggleMemberDropdown() }
                ) {
                    NoteMarkTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(type = MenuAnchorType.PrimaryNotEditable),
                        text = "Select Members",
                        onValueChange = {},
                        label = "Members",
                        hint = "Select Members",
                        readOnly = true,
                        isNumberType = false,
                        trailingIconConfig = TrailingIconConfig.ImageVectorIcon(
                            if (eventMembersDropDownExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown
                        ),
                        haveTrailingIcon = true,
                        onClick = { /* handled above */ }
                    )

                    ExposedDropdownMenu(
                        expanded = eventMembersDropDownExpanded,
                        onDismissRequest = { addEventViewModel.closeMemberDropdown() },
                        modifier = Modifier
                            .exposedDropdownSize(true)
                            .background(MaterialTheme.colorScheme.surfaceContainerLow)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .heightIn(max = 300.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            availableMembers.forEach { member ->
                                val isSelected = selectedMembers.contains(member.memberId)
                                DropdownMenuItem(
                                    text = {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = member.memberName,
                                                fontFamily = alatsiFont,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = MaterialTheme.colorScheme.onPrimary,
                                                modifier = Modifier.weight(1f)
                                            )
                                            Text(
                                                text = "₹${member.salary}",
                                                fontFamily = alatsiFont,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                                                modifier = Modifier.padding(horizontal = 8.dp)
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
                                    },
                                    contentPadding = PaddingValues(horizontal = 16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
