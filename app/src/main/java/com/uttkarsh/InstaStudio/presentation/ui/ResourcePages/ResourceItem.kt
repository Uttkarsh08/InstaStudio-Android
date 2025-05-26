package com.uttkarsh.InstaStudio.presentation.ui.ResourcePages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uttkarsh.InstaStudio.R

@Composable
fun ResourceItem(
    name: String,
    price: Long,
    onCLick: () -> Unit

) {
    val alatsiFont = FontFamily(Font(R.font.alatsi))

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .height(130.dp),
        shape = RoundedCornerShape(27.dp),
        color = colorResource(R.color.mediumContainer),
        onClick = onCLick
    ){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ){

            Text(name, fontFamily = alatsiFont, fontSize = 24.sp, color = Color.Black, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

            Text("₹ $price", fontFamily = alatsiFont, fontSize = 16.sp, color = Color.Black, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)


        }

    }
}

@Preview(showBackground = true)
@Composable
fun showp(){
    ResourceItem("Sony A3", 12000, {})
}