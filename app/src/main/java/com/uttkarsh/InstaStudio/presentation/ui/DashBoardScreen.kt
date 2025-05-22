package com.uttkarsh.InstaStudio.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uttkarsh.InstaStudio.R
import com.uttkarsh.InstaStudio.presentation.navigation.Screens
import com.uttkarsh.InstaStudio.presentation.viewmodel.AuthViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.ProfileViewModel

@Composable
fun DashBoardScreen(
    authViewModel: AuthViewModel,
    viewModel: ProfileViewModel,
    navController: NavController
){

    val studioImage = viewModel.StudioImageBitMap

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier.width(200.dp).height(200.dp).clip(RoundedCornerShape(100.dp)),
        ){
            if(studioImage != null){
                Image(
                    bitmap = studioImage.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }else{
                Image(
                    painter = painterResource(id = R.drawable.studiologo),
                    contentDescription = "Default Logo",
                    contentScale = ContentScale.Crop
                )
            }

        }

        Button(onClick = {
            viewModel.getStudioImage()
        }) {
            Text("Get Image")
        }

        Button(onClick = {
            navController.navigate(Screens.ProfileCompletionScreen.route)
        }) {
            Text("Move Back")
        }

        Button(onClick = {
            authViewModel.logout()
            navController.navigate(Screens.LoginTypeScreen.route)
        }) {
            Text("Logout")
        }

    }
}