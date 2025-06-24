package com.uttkarsh.InstaStudio.presentation.ui.EventPages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.uttkarsh.InstaStudio.R
import com.uttkarsh.InstaStudio.presentation.ui.utils.AppTopBar
import com.uttkarsh.InstaStudio.presentation.viewmodel.EventViewModel
import com.uttkarsh.InstaStudio.presentation.viewmodel.SubEventViewModel
import com.uttkarsh.InstaStudio.utils.states.EventState
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventDetailsScreen(
    eventViewModel: EventViewModel = hiltViewModel(),
    subEventViewModel: SubEventViewModel = hiltViewModel(),
    navController: NavController
) {
    val scrollState = rememberScrollState()

    val alatsiFont = FontFamily(Font(R.font.alatsi))

    val evenState by eventViewModel.eventState.collectAsState()
    val pagerState = rememberPagerState(pageCount = { 4 }, initialPage = 0)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        eventViewModel.getEventById()
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Event Details",
                isNavIcon = true,
                navIcon = R.drawable.back,
                onNavClick = { navController.navigateUp() }
            )
        }
    ){ paddingValues ->
        when(evenState){

            is EventState.Success -> {
                val event = (evenState as EventState.Success).response

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(text = event.clientName.toString(),
                        fontFamily = alatsiFont,
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp, horizontal = 8.dp)
                            .height(150.dp),
                        shape = RoundedCornerShape(27.dp)
                    ) {
                        Image(painterResource(R.drawable.eventdetail), contentDescription = null)

                    }

                    val tabTitles = listOf("Schedule", "Payments", "Contact", "Deliverables")

                    TabRow(
                        selectedTabIndex = pagerState.currentPage,
                        containerColor =Color.Transparent,
                        contentColor = colorResource(R.color.grey),
                        indicator = { tabPositions ->
                            Box(
                                modifier = Modifier
                                    .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                                    .height(2.dp)
                                    .background(colorResource(R.color.darkGreen))
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
                                selectedContentColor = colorResource(R.color.darkGreen),
                                unselectedContentColor = Color.DarkGray
                            )
                        }
                    }

                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) { page ->
                        when (page) {
                            0 -> {
                                EventSchedulePage(subEventViewModel, event, navController)
                            }
                            1 -> {

                            }
                            2->{

                            }
                            3->{

                            }
                        }
                    }
                }
            }
            is EventState.UpcomingPagingSuccess -> {
                //Not to handle here
            }
            is EventState.CompletedPagingSuccess -> {
                //Not to handle here
            }
            is EventState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Failed to load Event.")
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
            EventState.Idle -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Event Not Loaded Yet.")
                }
            }
            EventState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.Black)
                }
            }
        }
    }

}