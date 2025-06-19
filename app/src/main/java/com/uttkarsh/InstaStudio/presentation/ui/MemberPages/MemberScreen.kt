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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.uttkarsh.InstaStudio.R
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.uttkarsh.InstaStudio.presentation.ui.ResourcePages.ResourceSheet
import com.uttkarsh.InstaStudio.presentation.ui.ResourcePages.ResourceShimmerShow
import com.uttkarsh.InstaStudio.presentation.ui.utils.BottomSheetType
import com.uttkarsh.InstaStudio.presentation.ui.utils.BottomSheetTypeSaver
import com.uttkarsh.InstaStudio.presentation.ui.utils.AppTopBar
import com.uttkarsh.InstaStudio.presentation.ui.utils.SearchBar
import com.uttkarsh.InstaStudio.presentation.viewmodel.MemberViewModel
import com.uttkarsh.InstaStudio.utils.states.MemberState
import com.uttkarsh.InstaStudio.utils.states.ResourceState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MemberScreen(
    memberViewModel: MemberViewModel = hiltViewModel(),
    navController: NavController
){
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    val state by memberViewModel.memberState.collectAsState()
    val memberName by memberViewModel.memberName.collectAsState()
    val memberEmail by memberViewModel.memberEmail.collectAsState()
    val memberSalary by memberViewModel.memberSalary.collectAsState()
    val memberPhone by memberViewModel.memberPhone.collectAsState()
    val memberAvgRating by memberViewModel.memberAvgRating.collectAsState()
    val memberSpecialization by memberViewModel.memberSpecialization.collectAsState()

    var bottomSheetType by rememberSaveable(stateSaver = BottomSheetTypeSaver) {
        mutableStateOf<BottomSheetType>(BottomSheetType.None)
    }

    val allMembers = if(state is MemberState.PageSuccess){
        (state as MemberState.PageSuccess).response.collectAsLazyPagingItems()
    }else null

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Your Members",
                isNavIcon = true,
                navIcon = R.drawable.back,
                onNavClick = { navController.navigateUp() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    bottomSheetType = BottomSheetType.Add
                    memberViewModel.clearMemberValues()
                    coroutineScope.launch {
                        sheetState.show()
                    }
                },
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(6.dp),
                containerColor = colorResource(id = R.color.dashBoardContainer),
                contentColor = Color.Black,
                modifier = Modifier
                    .size(70.dp)
                    .border(3.dp, Color.White, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.Black,
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.EndOverlay,
    ) {
        when (state) {
            MemberState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(it),
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

            }

            is MemberState.Success -> {
                memberViewModel.getAllMembers()
            }

            is MemberState.PageSuccess -> {
                val isRefreshing = allMembers?.loadState?.refresh is LoadState.Loading
                val pullRefreshState = rememberPullRefreshState(
                    refreshing = isRefreshing,
                    onRefresh = { allMembers?.refresh() }
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pullRefresh(pullRefreshState)
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(it),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
                    ) {
                        item {
                            SearchBar(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(0.dp, bottom = 28.dp)
                                    .border(
                                        BorderStroke(2.dp, colorResource(R.color.searchBarBorder)),
                                        RoundedCornerShape(14.dp)
                                    ),
                                onSearchChanged = { /* TODO */ }
                            )
                        }

                        items(
                            count = allMembers?.itemCount ?: 0,
                            key = { index -> allMembers?.get(index)?.memberId ?: index }

                        ) { index ->

                            allMembers?.get(index)?.let { member ->
                                MemberCard(
                                    name = memberName,
                                    email = memberEmail,
                                    phoneNo = memberPhone,
                                    avgRating = memberAvgRating,
                                    specialization = memberSpecialization,
                                    salary = memberSalary,
                                    onclick = {}
                                )
                            }
                        }

                        allMembers.apply {
                            when {
                                this?.loadState?.append is LoadState.Loading -> {
                                    items(1) {
                                        ResourceShimmerShow()
                                    }
                                }

                                this?.loadState?.refresh is LoadState.Loading -> {
                                    items(10) {
                                        ResourceShimmerShow()
                                    }
                                }
                            }
                        }
                    }
                    PullRefreshIndicator(
                        refreshing = isRefreshing,
                        state = pullRefreshState,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                }
            }
        }
    }

    if(bottomSheetType != BottomSheetType.None){
        val heading = when (bottomSheetType) {
            BottomSheetType.Edit -> "Edit Resource"
            BottomSheetType.Add -> "Add Resource"
            else -> ""
        }

        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch {
                    sheetState.hide()
                }.invokeOnCompletion {
                    bottomSheetType = BottomSheetType.None
                }
            },
            sheetState = sheetState
        ) {
            MemberSheet(
                heading = heading,
                email = memberEmail,
                specialization = memberSpecialization,
                salary = memberSalary,
                onSalaryChange = { memberViewModel.updateMemberSalary(it) },
                onEmailChange = { memberViewModel.updateMemberEmail(it) },
                onSpecializationChange = { memberViewModel.updateMemberSpecialization(it) },
                onSave = {
                    coroutineScope.launch {
                        if (bottomSheetType == BottomSheetType.Add) {
                            memberViewModel.createNewUser()
                        } else if (bottomSheetType == BottomSheetType.Edit) {
                            memberViewModel.updateMemberById()
                        }
                    }
                },
                onCancel = {
                    coroutineScope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        bottomSheetType = BottomSheetType.None
                    }
                }
            )
        }
    }

    LaunchedEffect(state) {
        if (state is ResourceState.Success) {
            Log.d("MemberScreen", "Success - Closing sheet and refreshing")
            coroutineScope.launch {
                sheetState.hide()
            }.invokeOnCompletion {
                bottomSheetType = BottomSheetType.None
            }
        }
    }

    LaunchedEffect(sheetState.isVisible) {
        if (!sheetState.isVisible) {
            Log.d("MemberScreen", "Sheet hidden - refreshing resources")
            memberViewModel.getAllMembers()
        }
    }
}