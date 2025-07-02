package com.uttkarsh.InstaStudio.presentation.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import com.uttkarsh.InstaStudio.R
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.uttkarsh.InstaStudio.presentation.navigation.Screens
import com.uttkarsh.InstaStudio.presentation.ui.utils.NoteMarkTextField
import com.uttkarsh.InstaStudio.presentation.viewmodel.ProfileViewModel
import com.uttkarsh.InstaStudio.utils.states.ProfileState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCompletionScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavController
) {
    val scrollState = rememberScrollState()
    val alatsiFont = FontFamily(Font(R.font.alatsi))
    val context = LocalContext.current
    val state by viewModel.profileState.collectAsState()

    val studioName = viewModel.studioName
    val phoneNo = viewModel.phoneNumber
    val address = viewModel.address
    val city = viewModel.city
    val stateText = viewModel.state
    val pinCode = viewModel.pinCode
    val selectedImageUri = viewModel.selectedStudioImageUri

    val isLoading = state is ProfileState.Loading
    val errorMessage = (state as? ProfileState.Error)?.message

    LaunchedEffect(Unit) {
        viewModel.fetchLatestEmail()
    }
    val email = viewModel.userEmail.collectAsState()

    LaunchedEffect(state) {
        if (state is ProfileState.Success) {
            navController.navigate(Screens.DashBoardScreen.route) {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        contentWindowInsets = WindowInsets.statusBars
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .verticalScroll(scrollState)
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 60.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 27.dp,
                            topEnd = 27.dp
                        )
                    )
                    .background(MaterialTheme.colorScheme.onPrimaryContainer)
                    .padding(
                        horizontal = 32.dp,
                        vertical = 24.dp
                    )
                    .navigationBarsPadding(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(
                    text = "Register",
                    fontFamily = alatsiFont,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                )

                SelectLogoSection(
                    selectedImageUri = selectedImageUri,
                    onImageSelected = { uri: Uri? ->
                        uri?.let { viewModel.onImageSelected(context, it) }
                    }
                )

                Text(
                    text = email.value,
                    fontFamily = alatsiFont,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(Modifier.height(20.dp))

                NoteMarkTextField(
                    text = studioName,
                    onValueChange = viewModel::updateStudioName,
                    label = "Studio Name",
                    hint = "Priya Studio",
                    isNumberType = false
                )

                NoteMarkTextField(
                    text = phoneNo,
                    onValueChange = viewModel::updatePhoneNumber,
                    label = "Phone No.",
                    hint = "7088XXXXXX",
                    isNumberType = true
                )

                NoteMarkTextField(
                    text = address,
                    onValueChange = viewModel::updateAddress,
                    label = "Studio Address",
                    hint = "Hardaspur",
                    isNumberType = false
                )

                NoteMarkTextField(
                    text = city,
                    onValueChange = viewModel::updateCity,
                    label = "City",
                    hint = "Rudrapur",
                    isNumberType = false
                )

                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    NoteMarkTextField(
                        text = stateText,
                        onValueChange = viewModel::updateState,
                        label = "State",
                        hint = "Uttarakhand",
                        isNumberType = false,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(Modifier.width(20.dp))

                    NoteMarkTextField(
                        text = pinCode,
                        onValueChange = viewModel::updatePinCode,
                        label = "Pin Code",
                        hint = "263112",
                        isNumberType = true,
                        modifier = Modifier.weight(1f)
                    )

                }

                Spacer(Modifier.height(10.dp))

                errorMessage?.let {
                    Text(
                        text = "Failure: $it",
                        color = MaterialTheme.colorScheme.error,
                        fontFamily = alatsiFont,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center
                    )
                }



                Button(
                    onClick = { if (!isLoading) viewModel.saveAdminProfile() },
                    modifier = Modifier
                        .width(200.dp)
                        .height(50.dp)
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    shape = RoundedCornerShape(15.dp),
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Submit", fontFamily = alatsiFont, fontSize = 18.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun SelectLogoSection(
    selectedImageUri: Uri?,
    onImageSelected: (Uri?) -> Unit
) {
    val alatsiFont = FontFamily(Font(R.font.alatsi))

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onImageSelected(uri)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    launcher.launch("image/*")
                }
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (selectedImageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(selectedImageUri),
                contentDescription = "Selected Logo",
                modifier = Modifier
                    .padding(top = 12.dp)
                    .width(80.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(100.dp)),
                contentScale = ContentScale.Crop,
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.studiologo),
                contentDescription = "Default Logo",
                modifier = Modifier
                    .padding(top = 12.dp)
                    .width(80.dp)
                    .height(80.dp),
                contentScale = ContentScale.Crop
            )
        }

        Text(
            text = "Add Logo",
            fontFamily = alatsiFont,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}