package com.uttkarsh.InstaStudio.presentation.ui

import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.uttkarsh.InstaStudio.R
import com.uttkarsh.InstaStudio.domain.model.dto.auth.LoginRequestDTO
import com.uttkarsh.InstaStudio.domain.model.validators.validate
import com.uttkarsh.InstaStudio.presentation.navigation.Screens
import com.uttkarsh.InstaStudio.presentation.viewmodel.AuthViewModel
import com.uttkarsh.InstaStudio.utils.states.AuthState
import kotlinx.coroutines.delay

@Composable
fun SignInScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navController: NavController
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val scrollState = rememberScrollState()
    val alatsiFont = FontFamily(Font(R.font.alatsi))
    val context = LocalContext.current
    val state by viewModel.authState.collectAsState()
    val isRegistered by viewModel.isRegistered.collectAsState()
    val loginType = viewModel.loginType.collectAsState().value

    LaunchedEffect(state) {
        if (state is AuthState.Success) {
            val token = (state as AuthState.Success).token
            val loginRequest = LoginRequestDTO(token, loginType)
            val validationError = loginRequest.validate()
            if (validationError != null) {
                Toast.makeText(context, validationError, Toast.LENGTH_SHORT).show()
                return@LaunchedEffect
            }
            viewModel.onFirebaseLoginSuccess(loginRequest)
        }
    }

    LaunchedEffect(state) {
        delay(1000)
        if (state is AuthState.BackendSuccess) {
            val nextDestination = if (isRegistered) {
                Screens.DashBoardScreen.route
            } else {
                Screens.ProfileCompletionScreen.route
            }
            Log.e("SignIn: isRegistered: ",  isRegistered.toString())

            navController.navigate(nextDestination){
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    val errorMessage = if (state is AuthState.Error) (state as AuthState.Error).message else null
    val isLoading = state is AuthState.Loading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(
                if (isLandscape) Modifier.verticalScroll(scrollState) else Modifier
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.camera),
                contentDescription = null,
                modifier = Modifier
                    .padding(45.dp)
                    .padding(top = 120.dp)
                    .width(240.dp)
                    .height(200.dp),
                contentScale = ContentScale.Crop,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(45.dp)
                .padding(bottom = 110.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = if (errorMessage != null) "Login Failed: $errorMessage" else "Please Sign-In",
                fontFamily = alatsiFont,
                fontSize = 26.sp,
                color = if (errorMessage != null) colorResource(id = R.color.errorRed) else colorResource(id = R.color.black)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    if (!isLoading) {
                        viewModel.signInWithGoogle(context)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(67.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (errorMessage != null) colorResource(R.color.errorRed) else colorResource(id = R.color.GoogleBlue),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(19.dp),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.google),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (errorMessage != null) "Retry Sign-In" else "Continue With Google",
                            fontFamily = alatsiFont,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}




