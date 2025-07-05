package com.uttkarsh.InstaStudio.presentation.ui.ResourcePages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import com.uttkarsh.InstaStudio.R
import com.uttkarsh.InstaStudio.presentation.ui.utils.AppTopBar
import com.uttkarsh.InstaStudio.presentation.ui.utils.SearchBar
import com.uttkarsh.InstaStudio.presentation.viewmodel.ResourceViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.uttkarsh.InstaStudio.presentation.ui.utils.BottomSheetType
import com.uttkarsh.InstaStudio.presentation.ui.utils.BottomSheetTypeSaver
import com.uttkarsh.InstaStudio.utils.states.ResourceState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ResourceScreen(
    resourceViewModel: ResourceViewModel = hiltViewModel(),
    navController: NavController
) {

    LaunchedEffect(Unit) { resourceViewModel.getAllResources() }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    var bottomSheetType by rememberSaveable(stateSaver = BottomSheetTypeSaver) {
        mutableStateOf<BottomSheetType>(BottomSheetType.None)
    }

    val state by resourceViewModel.resourceState.collectAsState()
    val name by resourceViewModel.resourceName.collectAsState()
    val price by resourceViewModel.resourcePrice.collectAsState()
    val expandedResourceId by resourceViewModel.expandedResourceId.collectAsState()

    val allResources = if (state is ResourceState.ListSuccess) {
        (state as ResourceState.ListSuccess).response.collectAsLazyPagingItems()
    } else null

    val errorMessage = (state as? ResourceState.Error)?.message

    Scaffold(
        topBar = {
            AppTopBar(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                title = "Your Resources",
                isNavIcon = true,
                navIcon = R.drawable.back,
                isRightIcon = false,
                rightIcon = null,
                onRightIconClicked = {},
                onNavClick = { navController.navigateUp() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    bottomSheetType = BottomSheetType.Add
                    resourceViewModel.clearResourceValues()
                    coroutineScope.launch { sheetState.show() }
                },
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(6.dp),
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(50.dp)
                    .border(3.dp, MaterialTheme.colorScheme.onPrimaryContainer, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(
                        BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary),
                        RoundedCornerShape(10.dp)
                    ),
                onSearchChanged = { /* handle search */ }
            )

            when (state) {
                ResourceState.Loading -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                    CircularProgressIndicator()
                }

                is ResourceState.Error -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Failed to load resources.")
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { resourceViewModel.getAllResources() }) {
                            Text("Retry")
                        }
                    }
                }

                ResourceState.Idle -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Text("No resources loaded yet.")
                }

                is ResourceState.ListSuccess -> {
                    val isRefreshing = allResources?.loadState?.refresh is LoadState.Loading
                    val pullRefreshState = rememberPullRefreshState(
                        refreshing = isRefreshing,
                        onRefresh = { allResources?.refresh() }
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .pullRefresh(pullRefreshState)
                    ) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            items(
                                count = allResources?.itemCount ?: 0,
                                key = { index -> allResources?.get(index)?.resourceId ?: index }
                            ) { index ->
                                allResources?.get(index)?.let { resource ->

                                    val isExpanded = expandedResourceId == resource.resourceId

                                    Surface(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .animateContentSize(),
                                        shape = RoundedCornerShape(12.dp),
                                        color = MaterialTheme.colorScheme.surfaceContainerLow,
                                        tonalElevation = 1.dp,
                                        border = if (isExpanded) {
                                            BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary)
                                        } else {
                                            null
                                        }
                                    ) {
                                        Column {
                                            ResourceItem(
                                                resource = resource,
                                                onCLick = {
                                                    if (isExpanded) {
                                                        resourceViewModel.setExpandedResourceId(null)
                                                    } else {
                                                        resourceViewModel.prepareEditResource(resource)
                                                        resourceViewModel.setExpandedResourceId(resource.resourceId)
                                                    }
                                                },
                                                ifInFocus = isExpanded
                                            )

                                            AnimatedVisibility(
                                                visible = isExpanded,
                                                enter = expandVertically() + fadeIn(),
                                                exit = shrinkVertically() + fadeOut()
                                            ) {
                                                ExpandedResourceSheet(
                                                    resource = resource,
                                                    onEditClicked = { /* handle edit */ },
                                                    onDeleteClicked = { /* handle delete */ }
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            allResources?.apply {
                                when {
                                    loadState.append is LoadState.Loading -> {
                                        items(1) { ResourceShimmerShow() }
                                    }
                                    loadState.refresh is LoadState.Loading -> {
                                        items(5) { ResourceShimmerShow() }
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

                is ResourceState.Success -> {}
            }
        }

        if (bottomSheetType != BottomSheetType.None) {
            val heading = when (bottomSheetType) {
                BottomSheetType.Edit -> "Edit Resource"
                BottomSheetType.Add -> "Add Resource"
                else -> ""
            }

            ModalBottomSheet(
                onDismissRequest = {
                    coroutineScope.launch { sheetState.hide() }
                        .invokeOnCompletion { bottomSheetType = BottomSheetType.None }
                },
                sheetState = sheetState,
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                ResourceSheet(
                    name = name,
                    price = price,
                    heading = heading,
                    errorMessage = errorMessage,
                    onNameChange = { resourceViewModel.updateResourceName(it) },
                    onPriceChange = { resourceViewModel.updateResourcePrice(it) },
                    onSave = {
                        coroutineScope.launch {
                            if (bottomSheetType == BottomSheetType.Add) {
                                resourceViewModel.createNewResource()
                            } else if (bottomSheetType == BottomSheetType.Edit) {
                                resourceViewModel.updateResourceById()
                            }
                        }
                    },
                    onCancel = {
                        coroutineScope.launch { sheetState.hide() }
                            .invokeOnCompletion { bottomSheetType = BottomSheetType.None }
                    }
                )
            }
        }

        LaunchedEffect(state) {
            if (state is ResourceState.Success) {
                coroutineScope.launch { sheetState.hide() }
                    .invokeOnCompletion { bottomSheetType = BottomSheetType.None }
            }
        }

        LaunchedEffect(sheetState.isVisible) {
            if (!sheetState.isVisible) {
                resourceViewModel.getAllResources()
            }
        }
    }
}
