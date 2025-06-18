package com.uttkarsh.InstaStudio.presentation.ui.ResourcePages

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.uttkarsh.InstaStudio.R
import com.uttkarsh.InstaStudio.presentation.ui.utils.AppTopBar
import com.uttkarsh.InstaStudio.presentation.ui.utils.SearchBar
import com.uttkarsh.InstaStudio.presentation.viewmodel.ResourceViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.uttkarsh.InstaStudio.utils.states.ResourceState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResourceScreen(
    resourceViewModel: ResourceViewModel = hiltViewModel(),
    navController: NavController
){
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    var bottomSheetType by rememberSaveable(stateSaver = BottomSheetTypeSaver) {
        mutableStateOf<BottomSheetType>(BottomSheetType.None)
    }


    val state = resourceViewModel.resourceState.collectAsState()
    val name by resourceViewModel.resourceName.collectAsState()
    val price by resourceViewModel.resourcePrice.collectAsState()

    val allResources = if (state.value is ResourceState.ListSuccess) {
        (state.value as ResourceState.ListSuccess).response.collectAsLazyPagingItems()
    } else null


    Scaffold(
        topBar = {
            AppTopBar(
                title = "Add Resource",
                isNavIcon = true,
                navIcon = R.drawable.back,
                onNavClick =  { navController.navigateUp() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    bottomSheetType = BottomSheetType.Add
                    resourceViewModel.clearResourceValues()
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
        floatingActionButtonPosition = FabPosition.End,
    ){
        when (state.value) {
            ResourceState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is ResourceState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Failed to load resources.")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { resourceViewModel.getAllResources() }) {
                            Text("Retry")
                        }
                    }
                }
            }

            ResourceState.Idle -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No resources loaded yet.")
                }
            }

            is ResourceState.ListSuccess -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize().padding(it),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    item(span = { GridItemSpan(2) }) {
                        SearchBar(
                            modifier = Modifier.fillMaxWidth()
                                .padding(0.dp, bottom = 28.dp)
                                .border(BorderStroke(2.dp, colorResource(R.color.searchBarBorder)),
                                    RoundedCornerShape(14.dp)),
                            onSearchChanged = { /* TODO */ }
                        )
                    }

                    items(
                        count = allResources?.itemCount ?: 0,
                        key = { index -> allResources?.get(index)?.resourceId ?: index }
                    ) { index ->
                        allResources?.get(index)?.let { resource ->
                            ResourceItem(
                                name = resource.resourceName,
                                price = resource.resourcePrice,
                                onCLick = {
                                    Log.d("EDIT_SHEET", "Updating resource: ${resource.resourceId}, ${resource.resourceName}, ${resource.resourcePrice}")
                                    resourceViewModel.prepareEditResource(resource)
                                    bottomSheetType = BottomSheetType.Edit
                                    coroutineScope.launch {
                                        sheetState.show()
                                    }
                                }
                            )
                        }
                    }
                }
            }

            is ResourceState.Success -> {
                resourceViewModel.getAllResources()
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
            ResourceSheet(
                name = name,
                price = price,
                heading = heading,
                onNameChange = { resourceViewModel.updateResourceName(it) },
                onPriceChange = { resourceViewModel.updateResourcePrice(it) },
                onSave = {
                    coroutineScope.launch {
                        sheetState.hide()
                        delay(100L)
                        if (bottomSheetType == BottomSheetType.Add) {
                            resourceViewModel.createNewResource()
                        } else if (bottomSheetType == BottomSheetType.Edit) {
                            resourceViewModel.updateResourceById()
                        }
                        bottomSheetType = BottomSheetType.None
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
}

