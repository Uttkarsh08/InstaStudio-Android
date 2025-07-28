package com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventPages.utils.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.uttkarsh.InstaStudio.domain.model.EventType
import com.uttkarsh.InstaStudio.presentation.ui.utils.NoteMarkTextField
import com.uttkarsh.InstaStudio.presentation.viewmodel.AddEventViewModel

@Composable
fun ClientInfoSection(
    addEventViewModel: AddEventViewModel,
    alatsiFont: FontFamily
) {
    val selectedEventType by addEventViewModel.selectedEventType
    val clientName = addEventViewModel.clientName

    if (selectedEventType == EventType.WEDDING || selectedEventType == EventType.ANNIVERSARY ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            NoteMarkTextField(
                text = addEventViewModel.groomName,
                onValueChange = addEventViewModel::updateGroomName,
                label = "Groom Name",
                hint = "Harish",
                isNumberType = false,
                haveTrailingIcon = false,
                trailingIconConfig = null,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "weds",
                fontFamily = alatsiFont,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .padding(bottom = 16.dp)
            )

            NoteMarkTextField(
                text = addEventViewModel.brideName,
                onValueChange = addEventViewModel::updateBrideName,
                label = "Bride Name",
                hint = "Jyoti",
                isNumberType = false,
                haveTrailingIcon = false,
                trailingIconConfig = null,
                modifier = Modifier.weight(1f)
            )

        }
    } else {
        NoteMarkTextField(
            text = clientName,
            onValueChange = addEventViewModel::updateClientName,
            label = "Client Name",
            hint = "Harish",
            isNumberType = false,
            haveTrailingIcon = false,
            trailingIconConfig = null
        )
    }
}
