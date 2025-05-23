package com.uttkarsh.InstaStudio.presentation.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.uttkarsh.InstaStudio.R
import androidx.compose.runtime.getValue
import com.uttkarsh.InstaStudio.presentation.navigation.Screens
import com.uttkarsh.InstaStudio.presentation.viewmodel.AuthViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.DashBoardViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.ProfileViewModel
import com.uttkarsh.InstaStudio.utils.states.DashBoardState

@Composable
fun DashBoardScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
    dashBoardViewModel: DashBoardViewModel = hiltViewModel(),
    navController: NavController
){

    val state by dashBoardViewModel.dashBoardState.collectAsState()
    val studioImage = profileViewModel.StudioImageBitMap
    val isRegistered by authViewModel.isRegistered.collectAsState()
    val isAuthRefreshed by authViewModel.isAuthRefreshed.collectAsState()

    LaunchedEffect(Unit) {
        authViewModel.refreshAuthState()
    }
    LaunchedEffect(isAuthRefreshed) {
        if (isAuthRefreshed){
            Log.d("isRegistered in DashBoard: ", isRegistered.toString())
            authViewModel.resetAuthRefreshFlag()
        }
    }

    LaunchedEffect(state) {
        if(state is DashBoardState.Success){
            val studioId = (state as DashBoardState.Success).studioId
            val userId = (state as DashBoardState.Success).userId

            Log.d("DashBoardScreen", studioId.toString())
            Log.d("DashBoardScreen", userId.toString())
        }
    }
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
            profileViewModel.getStudioImage()
        }) {
            Text("Get Image")
        }

        Button(onClick = {
            dashBoardViewModel.getUserProfile()
        }) {
            Text("Get Data")
        }

        Button(onClick = {
            authViewModel.logout()
            profileViewModel.resetProfileState()
            navController.navigate(Screens.LoginTypeScreen.route)

        }) {
            Text("Logout")
        }

    }
}