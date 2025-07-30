package com.uttkarsh.InstaStudio.presentation.ui.utils


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowTimePickerDialog(
    onTimeSelected: (String) -> Unit,
    onDismiss: () -> Unit,
    initialTime: String,
    alatsiFont: FontFamily
) {
    val hourMinute = initialTime.split(":")
    val initialHour = hourMinute.getOrNull(0)?.toIntOrNull() ?: 0
    val initialMinute = hourMinute.getOrNull(1)?.toIntOrNull() ?: 0

    val timePickerState = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = true
    )

    TimePickerDialog(
        onDismiss = onDismiss,
        onConfirm = {
            val selectedHour = timePickerState.hour
            val selectedMinute = timePickerState.minute
            val currentSecond = Calendar.getInstance().get(Calendar.SECOND)
            val formattedTime = String.format(Locale.ROOT, "%02d:%02d:%02d", selectedHour, selectedMinute, currentSecond)
            onTimeSelected(formattedTime)
        },
        alatsiFont = alatsiFont
    ) {
        TimePicker(
            state = timePickerState,
            colors = TimePickerDefaults.colors(
                clockDialColor = MaterialTheme.colorScheme.onPrimaryContainer,
                clockDialSelectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                clockDialUnselectedContentColor = MaterialTheme.colorScheme.onPrimary,
                selectorColor = MaterialTheme.colorScheme.onPrimary,
                periodSelectorBorderColor = MaterialTheme.colorScheme.onPrimary,
                periodSelectorSelectedContainerColor = MaterialTheme.colorScheme.onPrimary,
                periodSelectorUnselectedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                periodSelectorSelectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                periodSelectorUnselectedContentColor = MaterialTheme.colorScheme.onPrimary,
                timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.onPrimary,
                timeSelectorUnselectedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                timeSelectorSelectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                timeSelectorUnselectedContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
}

@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    alatsiFont: FontFamily,
    content: @Composable () -> Unit
) {
    AlertDialog(
        modifier = Modifier.padding(16.dp),
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = "Ok",
                    fontFamily = alatsiFont,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Cancel",
                    fontFamily = alatsiFont,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        text = content
    )
}
