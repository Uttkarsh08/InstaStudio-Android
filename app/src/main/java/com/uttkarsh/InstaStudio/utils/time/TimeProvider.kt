package com.uttkarsh.InstaStudio.utils.time

import java.time.LocalDateTime
import java.util.Date

interface TimeProvider {
    fun nowDate(): String
    fun nowTime(): String
    fun nowDateTime(): String
    fun parseDateTime(dateTimeStr: String): Date?
    fun nowLocalDateTime(): LocalDateTime?
    fun formatDate(rawDate: String, pattern: String = "dd MMM, yyyy, E"): String?
    fun formatTime(rawDate: String, pattern: String = "HH:mm"): String?
}