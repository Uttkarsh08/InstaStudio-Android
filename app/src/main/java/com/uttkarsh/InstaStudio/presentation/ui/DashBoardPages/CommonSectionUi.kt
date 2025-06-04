package com.uttkarsh.InstaStudio.presentation.ui.DashBoardPages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.uttkarsh.InstaStudio.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CommonSectionUi(
    icon: Int,
    heading: String,
    description: String,
    onClick: () -> Unit,
    isLeft: Boolean = true,
    isBig: Boolean = false,
    modifier: Modifier = Modifier
){
    val alatsiFont = FontFamily(Font(R.font.alatsi))

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp,
                start = if (isLeft) 16.dp else 8.dp,
                end = if (isLeft) 8.dp else 16.dp,
                bottom = 8.dp
            ),
        shape = RoundedCornerShape(27.dp),
        color = colorResource(R.color.dashBoardContainer),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.7f)
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Text(
                text = heading,
                fontFamily = alatsiFont,
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = description,
                fontFamily = alatsiFont,
                fontSize = 14.sp,
                color = colorResource(R.color.buttons)
            )
        }
    }
}

