package com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventPages

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.uttkarsh.InstaStudio.presentation.ui.utils.NoteMarkTextField
import com.uttkarsh.InstaStudio.presentation.ui.utils.TrailingIconConfig
import com.uttkarsh.InstaStudio.presentation.viewmodel.AddEventViewModel

@Composable
fun EventTypeSelector(
    addEventViewModel: AddEventViewModel,
    alatsiFont: FontFamily,
    dropdownWidth: MutableIntState,
    dropdownHeight: MutableIntState
) {
    val eventTypes = addEventViewModel.eventTypes
    val selectedEventType by addEventViewModel.selectedEventType
    val eventTypeDropdownExpanded by addEventViewModel.eventTypeDropdownExpanded

    val buttonClicked = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                dropdownWidth.intValue = coordinates.size.width
                dropdownHeight.intValue = coordinates.size.height
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
            onClick = {
                buttonClicked.value = true
                addEventViewModel.toggleEventTypeDropdown()
            }
        )

        DropdownMenu(
            expanded = eventTypeDropdownExpanded,
            onDismissRequest = {
                if (buttonClicked.value) {
                    buttonClicked.value = false
                }
                addEventViewModel.closeEventTypeDropdown()
            },
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .width(with(LocalDensity.current) { dropdownWidth.intValue.toDp() })
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
