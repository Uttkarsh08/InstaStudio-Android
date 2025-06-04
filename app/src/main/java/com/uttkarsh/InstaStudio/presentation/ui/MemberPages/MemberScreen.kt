package com.uttkarsh.InstaStudio.presentation.ui.MemberPages

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridCells.*
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.uttkarsh.InstaStudio.R
import com.uttkarsh.InstaStudio.presentation.ui.ResourcePages.ResourceItem
import com.uttkarsh.InstaStudio.presentation.ui.utils.AppTopBar
import com.uttkarsh.InstaStudio.presentation.ui.utils.SearchBar
import com.uttkarsh.InstaStudio.presentation.viewmodel.MemberViewModel
import com.uttkarsh.InstaStudio.utils.states.MemberState
import com.uttkarsh.InstaStudio.utils.states.ResourceState
import kotlinx.coroutines.launch

@Composable
fun MemberScreen(
    memberViewModel: MemberViewModel = hiltViewModel(),
    navController: NavController
){
    val state = memberViewModel.memberState.collectAsState()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Your Members",
                isNavIcon = true,
                navIcon = R.drawable.back,
                onNavClick = { navController.navigateUp() }
            )
        }
    ) {
        when (state.value) {
            MemberState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is MemberState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Failed to load resources.")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { memberViewModel.getAllMembers() }) {
                            Text("Retry")
                        }
                    }
                }
            }

            MemberState.Idle -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No Members loaded yet.")
                }
            }

            is MemberState.ListSuccess -> {
                Column(
                    modifier = Modifier.padding(it)
                ) {

                }
            }

            is MemberState.Success -> {
                memberViewModel.getAllMembers()
            }

            is MemberState.PageSuccess -> {}
        }
    }
}