package com.uttkarsh.InstaStudio.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.uttkarsh.InstaStudio.domain.model.LoginRequestDTO
import com.uttkarsh.InstaStudio.presentation.navigation.Screens
import com.uttkarsh.InstaStudio.presentation.viewmodel.AuthViewModel
import com.uttkarsh.InstaStudio.utils.states.AuthState

@Composable
fun SignInScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val state by viewModel.authState.collectAsState()
    val loginType = viewModel.loginType.collectAsState().value

    LaunchedEffect(state) {
        if (state is AuthState.Success) {
            val token = (state as AuthState.Success).token
            viewModel.onFirebaseLoginSuccess(LoginRequestDTO(token, loginType))
        }
        if (state is AuthState.BackendSuccess) {
            navController.navigate(Screens.LogOutScreen.route) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            viewModel.signInWithGoogle(context)
        }) {
            Text("Sign in with Google")
        }

        when (state) {
            is AuthState.Loading -> {
                CircularProgressIndicator()
            }
            is AuthState.Error -> {
                val error = (state as AuthState.Error).message
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Login Failed: $error")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {
                        viewModel.signInWithGoogle(context)
                    }) {
                        Text("Retry Sign-In")
                    }
                }
            }
            else -> {
                null
            }
        }
    }
}



