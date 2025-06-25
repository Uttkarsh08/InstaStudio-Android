package com.uttkarsh.InstaStudio.presentation.ui.utils

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uttkarsh.InstaStudio.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    isNavIcon: Boolean,
    navIcon:  Int?,
    onNavClick: () -> Unit
){
    val alatsiFont = FontFamily(Font(R.font.alatsi))

    if(isNavIcon){
        CenterAlignedTopAppBar(
            navigationIcon = {
                IconButton(onClick = onNavClick){
                    Icon(
                        painter = painterResource(id = navIcon!!),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
            },
            title = { Text(text = title,
                fontFamily = alatsiFont,
                fontSize = 20.sp,
                color = Color.Black)
            }

        )
    }else{
        CenterAlignedTopAppBar(
            title = { Text(text = title,
                fontFamily = alatsiFont,
                fontSize = 20.sp,
                color = Color.Black)
            }

        )
    }

}