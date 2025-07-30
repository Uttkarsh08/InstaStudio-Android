package com.uttkarsh.InstaStudio.presentation.ui.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit,
    initialDate: String,
    alatsiFont: FontFamily
) {

    val timeProvider = rememberTimeProvider()
    val initialMillis = remember(initialDate) {
        timeProvider.parseToMillis(initialDate, "yyyy-MM-dd")
    }
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialMillis)


    DatePickerDialog(
        modifier = Modifier.padding(16.dp),
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis?.let { millis ->
                    val dateStr = timeProvider.formatMillis(millis, "yyyy-MM-dd")
                    onDateSelected(dateStr)
                }
                onDismiss()
            }) {
                Text(
                    text = "Ok",
                    fontFamily = alatsiFont,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(
                    text = "Cancel",
                    fontFamily = alatsiFont,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {


        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                headlineContentColor = MaterialTheme.colorScheme.onPrimary,
                weekdayContentColor = MaterialTheme.colorScheme.onPrimary,
                subheadContentColor = MaterialTheme.colorScheme.onPrimary,
                selectedDayContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                selectedDayContainerColor = MaterialTheme.colorScheme.onPrimary,
                dayContentColor = MaterialTheme.colorScheme.onPrimary,
                disabledDayContentColor = MaterialTheme.colorScheme.onPrimary,
                todayContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
}

