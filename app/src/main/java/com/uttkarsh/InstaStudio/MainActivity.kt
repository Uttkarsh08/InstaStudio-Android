package com.uttkarsh.InstaStudio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.uttkarsh.InstaStudio.presentation.navigation.Navigation
import com.uttkarsh.InstaStudio.presentation.ui.DashBoardPages.DashBoardScreen
import com.uttkarsh.InstaStudio.ui.theme.InstaStudioTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InstaStudioTheme {
                Navigation()
            }
        }
    }
}
