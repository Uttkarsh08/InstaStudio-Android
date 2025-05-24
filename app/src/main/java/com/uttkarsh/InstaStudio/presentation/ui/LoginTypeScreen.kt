package com.uttkarsh.InstaStudio.presentation.ui

import android.content.res.Configuration
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.uttkarsh.InstaStudio.R
import com.uttkarsh.InstaStudio.domain.model.UserType
import com.uttkarsh.InstaStudio.presentation.navigation.Screens
import com.uttkarsh.InstaStudio.presentation.viewmodel.AuthViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginTypeScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navController: NavController
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(
                if (isLandscape) Modifier.verticalScroll(scrollState) else Modifier
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.camera),
                contentDescription = null,
                modifier = Modifier
                    .padding(45.dp)
                    .padding(top = 120.dp)
                    .width(240.dp)
                    .height(200.dp),
                contentScale = ContentScale.Crop,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(45.dp)
                .padding(bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginTypeButton(
                painter = painterResource(id = R.drawable.admin),
                text = "Admin Login",
                onClick = {
                    viewModel.setLoginType(UserType.ADMIN)
                    navController.navigate(Screens.SignInScreen.route)
                    Log.d("LoginType", viewModel.loginType.value.toString())
                }
            )
            Spacer(modifier = Modifier.height(30.dp))

            LoginTypeButton(
                painter = painterResource(id = R.drawable.subadmin),
                text = "Member Login",
                onClick = {
                    viewModel.setLoginType(UserType.MEMBER)
                    navController.navigate(Screens.SignInScreen.route)
                    Log.d("LoginType", viewModel.loginType.value.toString())
                }
            )
            Spacer(modifier = Modifier.height(30.dp))

            LoginTypeButton(
                painter = painterResource(id = R.drawable.customer),
                text = "Customer Login",
                onClick = {
                    viewModel.setLoginType(UserType.CUSTOMER)
                    navController.navigate(Screens.SignInScreen.route)
                    Log.d("LoginType", viewModel.loginType.value.toString())
                }
            )
            Spacer(modifier = Modifier.height(30.dp))

        }
    }
}



@Composable
fun LoginTypeButton(painter: Painter, text: String, onClick: ()-> Unit){
    val alatsiFont = FontFamily(Font(R.font.alatsi))

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(67.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.LightBlack),
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(19.dp),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Icon(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .size(18.dp)
                    .align(Alignment.CenterStart)
            )
            Text(
                text = text,
                fontFamily = alatsiFont,
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenPreview2(){
    LoginTypeScreen(
        navController = rememberNavController()
    )
}
