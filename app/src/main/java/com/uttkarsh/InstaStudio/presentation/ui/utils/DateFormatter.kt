package com.uttkarsh.InstaStudio.presentation.ui.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun dateFormatter(
    rawDate: String
): String? {
    val formatter = DateTimeFormatter.ofPattern("d MMM, yyyy, E", Locale.ENGLISH)

    val formattedDate = rawDate.let {
        return try {
            val dateTime = LocalDateTime.parse(it)
            dateTime.format(formatter)
        } catch (e: Exception) {
            "Invalid Date"
        }
    }
}