package com.uttkarsh.InstaStudio.presentation.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.uttkarsh.InstaStudio.presentation.navigation.Screens
import com.uttkarsh.InstaStudio.presentation.ui.utils.AppTopBar
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
        topBar = {
            AppTopBar("Profile Completion", false, null, {})
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(scrollState)
                        .padding(horizontal = 30.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Spacer(Modifier.height(15.dp))

                    SelectLogoSection(
                        selectedImageUri = selectedImageUri,
                        onImageSelected = { uri: Uri? ->
                            uri?.let { viewModel.onImageSelected(context, it) }
                        }
                    )

                    // Studio Name
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painterResource(R.drawable.stidioname),
                            contentDescription = "Studio Name",
                            modifier = Modifier.size(25.dp),
                            tint = colorResource(R.color.darkGrey)
                        )
                        Spacer(Modifier.width(15.dp))
                        OutlinedTextField(
                            value = studioName,
                            onValueChange = viewModel::updateStudioName,
                            label = {
                                Text(
                                    "Studio Name",
                                    fontFamily = alatsiFont,
                                    color = colorResource(R.color.grey)
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Gray,
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }

                    // Phone
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painterResource(R.drawable.phone),
                            contentDescription = "Phone Number",
                            modifier = Modifier.size(25.dp),
                            tint = colorResource(R.color.darkGrey)
                        )
                        Spacer(Modifier.width(20.dp))
                        OutlinedTextField(
                            value = phoneNo,
                            onValueChange = viewModel::updatePhoneNumber,
                            label = {
                                Text(
                                    "Phone No.",
                                    fontFamily = alatsiFont,
                                    color = colorResource(R.color.grey)
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Gray,
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }

                    // Address
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painterResource(R.drawable.address),
                            contentDescription = "Studio Address",
                            modifier = Modifier.size(25.dp),
                            tint = colorResource(R.color.darkGrey)
                        )
                        Spacer(Modifier.width(20.dp))
                        OutlinedTextField(
                            value = address,
                            onValueChange = viewModel::updateAddress,
                            label = {
                                Text(
                                    "Studio Address",
                                    fontFamily = alatsiFont,
                                    color = colorResource(R.color.grey)
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Gray,
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }

                    // City
                    OutlinedTextField(
                        value = city,
                        onValueChange = viewModel::updateCity,
                        label = {
                            Text(
                                "City",
                                fontFamily = alatsiFont,
                                color = colorResource(R.color.grey)
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Gray,
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    // State and Pin Code
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        OutlinedTextField(
                            value = stateText,
                            onValueChange = viewModel::updateState,
                            label = {
                                Text(
                                    "State",
                                    fontFamily = alatsiFont,
                                    color = colorResource(R.color.grey)
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Gray,
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        OutlinedTextField(
                            value = pinCode,
                            onValueChange = viewModel::updatePinCode,
                            label = {
                                Text(
                                    "Pin Code",
                                    fontFamily = alatsiFont,
                                    color = colorResource(R.color.grey)
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Gray,
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }

                }

                // BUTTON pinned at the bottom
                Column(
                    modifier = Modifier
                        .padding(horizontal = 30.dp, vertical = 10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painterResource(R.drawable.mail),
                            contentDescription = "User Mail",
                            modifier = Modifier.size(25.dp),
                            tint = colorResource(R.color.darkGrey)
                        )
                        Spacer(Modifier.width(20.dp))
                        Text(
                            text = email.value,
                            fontFamily = alatsiFont,
                            fontSize = 14.sp,
                            color = colorResource(R.color.darkGrey)
                        )
                    }

                    // Error message
                    errorMessage?.let {
                        Text(
                            "Failure: $it",
                            color = colorResource(R.color.errorRed),
                            fontFamily = alatsiFont
                        )
                    }

                    Spacer(Modifier.height(10.dp))

                    Button(
                        onClick = { if (!isLoading) viewModel.saveAdminProfile() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(67.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.buttons),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(19.dp)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Get Started", fontFamily = alatsiFont, fontSize = 18.sp)
                        }
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
            color = colorResource(R.color.darkGrey)
        )
    }
}

