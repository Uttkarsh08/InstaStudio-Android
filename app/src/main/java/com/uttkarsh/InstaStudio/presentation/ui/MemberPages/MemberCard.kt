package com.uttkarsh.InstaStudio.presentation.ui.MemberPages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
fun MemberCard(
    name: String,
    email: String,
    phoneNo: String,
    avgRating: Long,
    specialization: String,
    salary: Long,
    onclick: () -> Unit
) {
    val alatsiFont = FontFamily(Font(R.font.alatsi))

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .height(90.dp),
        shape = RoundedCornerShape(27.dp),
        color = colorResource(R.color.mediumContainer),
        onClick = onclick
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ){

            Text(name, fontFamily = alatsiFont, fontSize = 24.sp, color = Color.Black, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

            Row {
                Text(email, fontFamily = alatsiFont, fontSize = 16.sp, color = Color.Black, modifier = Modifier.fillMaxWidth(0.5f), textAlign = TextAlign.Center)
                Text(phoneNo, fontFamily = alatsiFont, fontSize = 16.sp, color = Color.Black, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            }

            Row {
                Text(avgRating.toString(), fontFamily = alatsiFont, fontSize = 16.sp, color = Color.Black, modifier = Modifier.fillMaxWidth(0.5f), textAlign = TextAlign.Center)
                Text("₹ $salary", fontFamily = alatsiFont, fontSize = 16.sp, color = Color.Black, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            }

            Text("Specialization: {$specialization}", fontFamily = alatsiFont, fontSize = 16.sp, color = Color.Black, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)


        }
    }
}

@Preview(showBackground = true)
@Composable
fun showPrev(){
    MemberCard(
        name = "Aman",
        email = "amam@gmail.com",
        phoneNo = "7088776440",
        avgRating = 3,
        specialization = "Gimble",
        salary = 10000,
        onclick = {}
    )
}