package com.uttkarsh.InstaStudio.presentation.ui.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
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
    navIcon: Int?,
    isRightIcon: Boolean,
    rightIcon: ImageVector?,
    onNavClick: () -> Unit,
    onRightIconClicked: () -> Unit
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
            },
            actions = {
                if (isRightIcon && rightIcon != null) {
                    Surface(
                        color = colorResource(R.color.darkGrey),
                        modifier = Modifier.height(40.dp).width(60.dp).padding(end = 8.dp),
                        shape = RoundedCornerShape(27.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable(onClick = onRightIconClicked),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = rightIcon,
                                contentDescription = "Right Icon",
                                modifier = Modifier.size(20.dp),
                                tint = Color.Black
                            )
                        }
                    }


                }
            }

        )
    }else{
        CenterAlignedTopAppBar(
            title = { Text(text = title,
                fontFamily = alatsiFont,
                fontSize = 20.sp,
                color = Color.Black)
            },


        )
    }

}