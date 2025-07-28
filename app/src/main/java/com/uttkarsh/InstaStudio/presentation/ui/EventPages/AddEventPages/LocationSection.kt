package com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventPages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.uttkarsh.InstaStudio.presentation.ui.utils.NoteMarkTextField
import com.uttkarsh.InstaStudio.presentation.viewmodel.AddEventViewModel

@Composable
fun LocationSection(addEventViewModel: AddEventViewModel) {
    val eveLocation = addEventViewModel.eventLocation
    val eventCity = addEventViewModel.eventCity
    val eventState = addEventViewModel.eventState
    val clientPhoneNo = addEventViewModel.clientPhoneNo

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        NoteMarkTextField(
            text = eveLocation,
            onValueChange = addEventViewModel::updateEventLocation,
            label = "Location",
            hint = "LPU",
            isNumberType = false,
            haveTrailingIcon = false,
            trailingIconConfig = null
        )

        NoteMarkTextField(
            text = eventCity,
            onValueChange = addEventViewModel::updateEventCity,
            label = "City",
            hint = "Phagwara",
            isNumberType = false,
            haveTrailingIcon = false,
            trailingIconConfig = null
        )

        NoteMarkTextField(
            text = eventState,
            onValueChange = addEventViewModel::updateEventState,
            label = "State",
            hint = "Punjab",
            isNumberType = false,
            haveTrailingIcon = false,
            trailingIconConfig = null
        )

        NoteMarkTextField(
            text = clientPhoneNo,
            onValueChange = addEventViewModel::updateClientPhoneNo,
            label = "Contact No.",
            hint = "7088XXXXXX",
            isNumberType = true,
            haveTrailingIcon = false,
            trailingIconConfig = null
        )
    }
}
