package com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventPages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.uttkarsh.InstaStudio.R
import com.uttkarsh.InstaStudio.domain.model.DatePickerTarget
import com.uttkarsh.InstaStudio.domain.model.TimePickerTarget
import com.uttkarsh.InstaStudio.presentation.ui.utils.NoteMarkTextField
import com.uttkarsh.InstaStudio.presentation.ui.utils.TrailingIconConfig
import com.uttkarsh.InstaStudio.presentation.viewmodel.AddEventViewModel
import java.time.LocalDateTime

@Composable
fun DateTimeSection(
    addEventViewModel: AddEventViewModel,
    eventStartDate: String,
    eventStartTime: String,
    eventEndDate: String,
    eventEndTime: String,
    alatsiFont: FontFamily
) {

    Column {
        Text(
            text = "Date & Time",
            fontFamily = alatsiFont,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
            ) {

                NoteMarkTextField(
                    text = eventStartDate,
                    onValueChange = {},
                    label = "From",
                    hint = eventStartDate,
                    isNumberType = false,
                    haveTrailingIcon = true,
                    trailingIconConfig = TrailingIconConfig.ResourceIcon(R.drawable.calender),
                    readOnly = true,
                    onClick = { addEventViewModel.onDateBoxClick(DatePickerTarget.START_DATE) }
                )

                Spacer(Modifier.height(8.dp))

                NoteMarkTextField(
                    text = eventStartTime,
                    onValueChange = {},
                    hint = eventStartTime,
                    label = null,
                    isNumberType = false,
                    haveTrailingIcon = true,
                    trailingIconConfig = TrailingIconConfig.ImageVectorIcon(Icons.Default.KeyboardArrowDown),
                    readOnly = true,
                    onClick = { addEventViewModel.onTimeBoxClick(TimePickerTarget.START_TIME) }
                )
            }

            Column(
                modifier = Modifier.weight(1f),
            ) {

                NoteMarkTextField(
                    text = eventEndDate,
                    onValueChange = {},
                    label = "To",
                    hint = eventEndDate,
                    isNumberType = false,
                    haveTrailingIcon = true,
                    trailingIconConfig = TrailingIconConfig.ResourceIcon(R.drawable.calender),
                    readOnly = true,
                    onClick = { addEventViewModel.onDateBoxClick(DatePickerTarget.END_DATE) }
                )

                Spacer(Modifier.height(8.dp))

                NoteMarkTextField(
                    text = eventEndTime,
                    onValueChange = {},
                    hint = eventEndTime,
                    label = null,
                    isNumberType = false,
                    haveTrailingIcon = true,
                    trailingIconConfig = TrailingIconConfig.ImageVectorIcon(Icons.Default.KeyboardArrowDown),
                    readOnly = true,
                    onClick = { addEventViewModel.onTimeBoxClick(TimePickerTarget.END_TIME) }
                )
            }
        }
    }
}
