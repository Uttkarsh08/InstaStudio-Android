package com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventPages.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.uttkarsh.InstaStudio.presentation.ui.utils.NoteMarkTextField

@Composable
fun LocationSection(
    location: String,
    onLocationChange: (String) -> Unit,
    city: String,
    onCityChange: (String) -> Unit,
    state: String,
    onStateChange: (String) -> Unit,
    contactNumber: String? = null,
    onContactNumberChange: ((String) -> Unit)? = null,
    cityStateInRow: Boolean = false
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        NoteMarkTextField(
            text = location,
            onValueChange = onLocationChange,
            label = "Location",
            hint = "LPU",
            isNumberType = false,
            haveTrailingIcon = false,
            trailingIconConfig = null
        )

        if (cityStateInRow) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                NoteMarkTextField(
                    modifier = Modifier.weight(1f),
                    text = city,
                    onValueChange = onCityChange,
                    label = "City",
                    hint = "Phagwara",
                    isNumberType = false,
                    haveTrailingIcon = false,
                    trailingIconConfig = null
                )
                NoteMarkTextField(
                    modifier = Modifier.weight(1f),
                    text = state,
                    onValueChange = onStateChange,
                    label = "State",
                    hint = "Punjab",
                    isNumberType = false,
                    haveTrailingIcon = false,
                    trailingIconConfig = null
                )
            }
        } else {
            NoteMarkTextField(
                text = city,
                onValueChange = onCityChange,
                label = "City",
                hint = "Phagwara",
                isNumberType = false,
                haveTrailingIcon = false,
                trailingIconConfig = null
            )
            NoteMarkTextField(
                text = state,
                onValueChange = onStateChange,
                label = "State",
                hint = "Punjab",
                isNumberType = false,
                haveTrailingIcon = false,
                trailingIconConfig = null
            )
        }

        if (contactNumber != null && onContactNumberChange != null) {
            NoteMarkTextField(
                text = contactNumber,
                onValueChange = onContactNumberChange,
                label = "Contact No.",
                hint = "7088XXXXXX",
                isNumberType = true,
                haveTrailingIcon = false,
                trailingIconConfig = null
            )
        }
    }
}


