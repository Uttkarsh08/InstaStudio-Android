package com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventPages.utils.event

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uttkarsh.InstaStudio.presentation.navigation.Screens
import com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventPages.utils.MemberSelector
import com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventPages.utils.ResourceSelector
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
    navController: NavController
) {
    val isSubEventEnabled by addEventViewModel::isSubEventEnabled
    val subEventsMap by addEventViewModel.subEventsMap.collectAsState()
    val selectedMembers by addEventViewModel.selectedEventMembers.collectAsState()
    val selectedResources by addEventViewModel.selectedEventResources.collectAsState()

    val availableMembersState by memberViewModel.memberState.collectAsState()
    val availableResourcesState by resourceViewModel.resourceState.collectAsState()

    val availableMembers = (availableMembersState as? MemberState.ListSuccess)?.response ?: emptyList()
    val availableResources = (availableResourcesState as? ResourceState.ListSuccess)?.response ?: emptyList()

    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {

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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 160.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.surfaceContainerHigh,
                        RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                subEventsMap.values.forEach { subEvent ->
                    SubEventCard(
                        subEvent = subEvent,
                        onDeleteClick = { addEventViewModel.removeSubEvent(subEvent) }
                    )
                }
            }
        } else {
            ResourceSelector(
                availableResources = availableResources,
                selectedResources = selectedResources,
                onResourceSelected = addEventViewModel::onResourceSelected,
                expanded = addEventViewModel.resourcesDropdownExpanded.value,
                onToggleExpand = addEventViewModel::toggleResourceDropdown,
                onDismiss = addEventViewModel::closeResourceDropdown,
                alatsiFont = alatsiFont
            )

            Spacer(modifier = Modifier.height(16.dp))

            MemberSelector(
                availableMembers = availableMembers,
                selectedMembers = selectedMembers,
                onMemberSelected = addEventViewModel::onMemberSelected,
                expanded = addEventViewModel.membersDropdownExpanded.value,
                onToggleExpand = addEventViewModel::toggleMemberDropdown,
                onDismiss = addEventViewModel::closeMemberDropdown,
                alatsiFont = alatsiFont
            )
        }
    }
}
