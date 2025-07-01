package com.uttkarsh.InstaStudio.presentation.ui.EventPages.EventListPages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.uttkarsh.InstaStudio.R
import com.uttkarsh.InstaStudio.domain.model.dto.event.EventListResponseDTO
import com.uttkarsh.InstaStudio.presentation.navigation.Screens
import com.uttkarsh.InstaStudio.presentation.ui.utils.AppTopBar
import com.uttkarsh.InstaStudio.presentation.ui.utils.SearchBar
import com.uttkarsh.InstaStudio.presentation.viewmodel.EventViewModel
import com.uttkarsh.InstaStudio.utils.states.EventState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun EventScreen(
    eventViewModel: EventViewModel = hiltViewModel(),
    navController: NavController
) {
    val pagerState = rememberPagerState(pageCount = { 2 }, initialPage = 0)
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Events",
                isNavIcon = true,
                navIcon = R.drawable.back,
                isRightIcon = false,
                rightIcon = null,
                onRightIconClicked = {},
                onNavClick = { navController.navigateUp() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {


            val tabTitles = listOf("Upcoming", "Completed")

            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = colorResource(R.color.mainGreen),
                contentColor = Color.Black,
                indicator = { tabPositions ->
                    Box(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                            .height(2.dp)
                            .background(Color.Black)
                    )
                }
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title, fontWeight = FontWeight.Bold) },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        selectedContentColor = Color.Black,
                        unselectedContentColor = Color.DarkGray
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> {
                        LaunchedEffect(Unit) { eventViewModel.loadUpcomingEventsIfNeeded() }
                        EventsPage(
                            eventViewModel,
                            stateFlow = eventViewModel.upcomingEventState,
                            navController
                        )
                    }
                    1 -> {
                        LaunchedEffect(Unit) { eventViewModel.loadCompletedEventsIfNeeded() }
                        EventsPage(
                            eventViewModel,
                            stateFlow = eventViewModel.completedEventState,
                            navController
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun EventsPage(
    eventViewModel: EventViewModel,
    stateFlow: StateFlow<EventState>,
    navController: NavController
) {
    val state by stateFlow.collectAsState()

    when (state) {
        is EventState.Error -> {
            val errorState = state as EventState.Error
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Failed: ${errorState.message}")
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        EventState.Idle -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No Events loaded yet.")
            }
        }

        EventState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is EventState.UpcomingPagingSuccess -> {
            val items = (state as EventState.UpcomingPagingSuccess).data.collectAsLazyPagingItems()
            EventLazyList(eventViewModel, items, navController, true)
        }

        is EventState.CompletedPagingSuccess -> {
            val items = (state as EventState.CompletedPagingSuccess).data.collectAsLazyPagingItems()
            EventLazyList(eventViewModel, items, navController, false)
        }

        else -> {}
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EventLazyList(
    eventViewModel: EventViewModel,
    items: LazyPagingItems<EventListResponseDTO>,
    navController: NavController,
    isForUpcomingEvents: Boolean
) {

    val isRefreshing = items.loadState.refresh is LoadState.Loading
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { items.refresh() }
    )


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(
                    BorderStroke(2.dp, colorResource(R.color.searchBarBorder)),
                    RoundedCornerShape(14.dp)
                ),

            onSearchChanged = { /* TODO: Update viewModel with new search query
            if(isForUpcomingEvents) Search in Upcoming else Completed
            */ }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
            ) {
                items(
                    count = items.itemCount,
                    key = { items[it]?.eventId ?: it }
                ) {
                    items[it]?.let { event ->
                        EventCard(event = event, onclick = {
                            eventViewModel.updateEventId(event.eventId)
                            navController.navigate(Screens.EventDetailScreen.route)
                        })
                    }
                }

                items.apply {
                    when {
                        loadState.append is LoadState.Loading -> {
                            item {
                                EventShimmerShow()
                            }
                        }

                        loadState.refresh is LoadState.Loading -> {
                            items(10) {
                                EventShimmerShow()
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