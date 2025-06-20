package com.uttkarsh.InstaStudio.presentation.ui.EventPages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.uttkarsh.InstaStudio.R
import com.uttkarsh.InstaStudio.presentation.ui.utils.AppTopBar
import com.uttkarsh.InstaStudio.presentation.viewmodel.AddEventViewModel

@Composable
fun AddEventScreen(
    addEventViewModel: AddEventViewModel = hiltViewModel(),
    navController: NavController
){

    val clientName = addEventViewModel.clientName
    val clientPhoneNo = addEventViewModel.clientPhoneNo
    val eventType = addEventViewModel.eventType
    val eventStartDate = addEventViewModel.eventStartDate
    val eventEndDate = addEventViewModel.eventEndDate
    val eveLocation = addEventViewModel.eventLocation
    val eventCity = addEventViewModel.eventCity
    val eventState = addEventViewModel.eventState
    val eventIsSaved = addEventViewModel.eventIsSaved

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Add Event",
                isNavIcon = true,
                navIcon = R.drawable.back,
                onNavClick = {navController.navigateUp()},
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(it)
        ) {
            OutlinedTextField(
                value = clientName,
                onValueChange = { addEventViewModel.updateClientName(it) },
                label = { Text("Client Name") }
            )
            OutlinedTextField(
                value = clientPhoneNo,
                onValueChange = { addEventViewModel.updateClientPhoneNo(it) },
                label = { Text("Client Number") }
            )
            OutlinedTextField(
                value = eventType,
                onValueChange = { addEventViewModel.updateEventType(it) },
                label = { Text("Event Type") }
            )
            OutlinedTextField(
                value = eveLocation,
                onValueChange = { addEventViewModel.updateEventLocation(it) },
                label = { Text("eventLocation") }
            )
            OutlinedTextField(
                value = eventCity,
                onValueChange = { addEventViewModel.updateEventCity(it) },
                label = { Text("eventCity") }
            )
            OutlinedTextField(
                value = eventState,
                onValueChange = { addEventViewModel.updateEventState(it) },
                label = { Text("eventState") }
            )
            Checkbox(
                checked = eventIsSaved,
                onCheckedChange = { addEventViewModel.updateEventIsSaved(it) },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Green,
                    uncheckedColor = Color.Gray,
                    checkmarkColor = Color.White
                )
            )

            Button(onClick = {
                addEventViewModel.createNewEvent()
            }) {
                Text(text = "Add Event")
            }

        }
    }
}
