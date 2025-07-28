package com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventPages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.uttkarsh.InstaStudio.presentation.ui.utils.NoteMarkTextField
import com.uttkarsh.InstaStudio.presentation.ui.utils.TrailingIconConfig
import com.uttkarsh.InstaStudio.presentation.viewmodel.AddEventViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventTypeSelector(
    addEventViewModel: AddEventViewModel,
    alatsiFont: FontFamily
) {
    val eventTypes = addEventViewModel.eventTypes
    val selectedEventType by addEventViewModel.selectedEventType
    val eventTypeDropdownExpanded by addEventViewModel.eventTypeDropdownExpanded

    ExposedDropdownMenuBox(
        expanded = eventTypeDropdownExpanded,
        onExpandedChange = {
            addEventViewModel.toggleEventTypeDropdown()
        }
    ) {
        NoteMarkTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(type = MenuAnchorType.PrimaryNotEditable),
            text = selectedEventType.displayName,
            onValueChange = {},
            label = "Event Type",
            hint = selectedEventType.displayName,
            isNumberType = false,
            haveTrailingIcon = true,
            trailingIconConfig = TrailingIconConfig.ImageVectorIcon(
                if (eventTypeDropdownExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown
            ),
            readOnly = true
        )

        ExposedDropdownMenu(
            expanded = eventTypeDropdownExpanded,
            onDismissRequest = {
                addEventViewModel.closeEventTypeDropdown()
            },
            modifier = Modifier.exposedDropdownSize()
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp)
                    .verticalScroll(rememberScrollState())
            ) {
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