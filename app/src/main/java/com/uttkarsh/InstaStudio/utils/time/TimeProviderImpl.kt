import android.os.Build
import com.uttkarsh.InstaStudio.utils.time.TimeProvider
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import javax.inject.Singleton

@Singleton
class TimeProviderImpl : TimeProvider {

    private val datePattern = "yyyy-MM-dd"
    private val timePattern = "HH:mm:ss"
    private val dateTimePattern = "yyyy-MM-dd'T'HH:mm:ss"

    override fun nowDate(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.now().format(DateTimeFormatter.ofPattern(datePattern, Locale.US))
        } else {
            SimpleDateFormat(datePattern, Locale.US).format(Date())
        }
    }

    override fun nowTime(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalTime.now().format(DateTimeFormatter.ofPattern(timePattern, Locale.US))
        } else {
            SimpleDateFormat(timePattern, Locale.US).format(Date())
        }
    }

    override fun nowDateTime(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateTimePattern, Locale.US))
        } else {
            SimpleDateFormat(dateTimePattern, Locale.US).format(Date())
        }
    }

    override fun parseDateTime(dateTimeStr: String): Date? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val ldt = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(dateTimePattern, Locale.US))
                Date.from(ldt.atZone(java.time.ZoneId.systemDefault()).toInstant())
            } else {
                SimpleDateFormat(dateTimePattern, Locale.US).parse(dateTimeStr)
            }
        } catch (e: Exception) {
            null
        }
    }

    override fun nowLocalDateTime(): LocalDateTime? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now()
        } else {
            null
        }
    }

    override fun formatDate(rawDate: String, pattern: String): String? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val dateTime = LocalDateTime.parse(rawDate)
                val formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH)
                dateTime.format(formatter)
            } else {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
                val outputFormat = SimpleDateFormat(pattern, Locale.ENGLISH)
                val date = inputFormat.parse(rawDate)
                outputFormat.format(date!!)
            }
        } catch (e: Exception) {
            "Invalid Date"
        }
    }

    override fun formatTime(rawDate: String, pattern: String): String? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val dateTime = LocalDateTime.parse(rawDate)
                val formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH)
                dateTime.format(formatter)
            } else {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
                val outputFormat = SimpleDateFormat(pattern, Locale.ENGLISH)
                val time = inputFormat.parse(rawDate)
                outputFormat.format(time!!)
            }
        } catch (e: Exception) {
            "Invalid Time"
        }
    }

    override fun parseToLocalDateTime(dateTimeStr: String, pattern: String): LocalDateTime? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                val formatter = DateTimeFormatter.ofPattern(pattern)
                LocalDateTime.parse(dateTimeStr, formatter)
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
    }


}

