package com.uttkarsh.InstaStudio.presentation.ui.EventPages.AddEventPages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uttkarsh.InstaStudio.presentation.viewmodel.AddEventViewModel

@Composable
fun SaveDraftButtons(
    addEventViewModel: AddEventViewModel,
    alatsiFont: FontFamily
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(
            onClick = {
                addEventViewModel.updateEventIsSaved(true)
                addEventViewModel.createNewEvent()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier
                .height(35.dp)
                .width(100.dp)
        ) {
            Text(text = "Save", fontFamily = alatsiFont, fontSize = 15.sp)
        }

        Button(
            onClick = {
                addEventViewModel.updateEventIsSaved(false)
                addEventViewModel.createNewEvent()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier
                .height(35.dp)
                .width(100.dp)
        ) {
            Text(text = "Draft", fontFamily = alatsiFont, fontSize = 15.sp)
        }

    }
}
