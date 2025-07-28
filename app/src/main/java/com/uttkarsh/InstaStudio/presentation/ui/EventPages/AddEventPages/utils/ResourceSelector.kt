package com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventPages.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontFamily
import com.uttkarsh.InstaStudio.domain.model.dto.resource.ResourceResponseDTO
import com.uttkarsh.InstaStudio.presentation.ui.utils.NoteMarkTextField
import com.uttkarsh.InstaStudio.presentation.ui.utils.TrailingIconConfig

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResourceSelector(
    availableResources: List<ResourceResponseDTO>,
    selectedResources: Set<Long>,
    onResourceSelected: (ResourceResponseDTO) -> Unit,
    expanded: Boolean,
    onToggleExpand: () -> Unit,
    onDismiss: () -> Unit,
    alatsiFont: FontFamily
) {
    val scrollState = rememberScrollState()

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { onToggleExpand() }
    ) {
        NoteMarkTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryNotEditable),
            text = "Select Resources",
            onValueChange = {},
            label = "Resources",
            hint = "Select Resources",
            isNumberType = false,
            readOnly = true,
            trailingIconConfig = TrailingIconConfig.ImageVectorIcon(
                if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown
            ),
            haveTrailingIcon = true,
            onClick = {}
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onDismiss() },
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
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = resource.resourceName,
                                    modifier = Modifier.weight(1f),
                                    fontFamily = alatsiFont,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                                Text(
                                    text = "₹${resource.resourcePrice}",
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    fontFamily = alatsiFont,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                                )
                                Checkbox(
                                    checked = isSelected,
                                    onCheckedChange = { onResourceSelected(resource) },
                                    colors = CheckboxDefaults.colors()
                                )
                            }
                        },
                        onClick = { onResourceSelected(resource) },
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}
