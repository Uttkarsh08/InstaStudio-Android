package com.uttkarsh.InstaStudio.presentation.ui.utils

import android.app.TimePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar
import java.util.Locale

@Composable
fun ShowTimePickerDialog(
    onTimeSelected: (String) -> Unit,
){
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            val timeStr = String.format(Locale.US, "%02d:%02d:%02d", hourOfDay, minute, 0)
            onTimeSelected(timeStr)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    ).show()
}