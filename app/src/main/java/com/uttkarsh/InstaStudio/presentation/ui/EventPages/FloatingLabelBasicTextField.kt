package com.uttkarsh.InstaStudio.presentation.ui.EventPages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.uttkarsh.InstaStudio.R

@Composable
fun FloatingLabelBasicTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    val isFocusedOrNotEmpty = value.isNotEmpty()
    val underlineColor = colorResource(R.color.grey)
    val alatsiFont = remember { FontFamily(Font(R.font.alatsi)) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        AnimatedVisibility(
            visible = isFocusedOrNotEmpty,
            enter = fadeIn() + slideInVertically { it / 2 },
            exit = fadeOut() + slideOutVertically { it / 2 }
        ) {
            Text(
                text = label,
                fontSize = 12.sp,
                fontFamily = alatsiFont,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 2.dp)
            )
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            cursorBrush = SolidColor(Color.Black),
            textStyle = TextStyle(
                color = Color.Black,
                fontFamily = alatsiFont,
                fontSize = 16.sp,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 2.dp),
            decorationBox = { innerTextField ->
                Box {
                    if (value.isEmpty()) {
                        Text(
                            text = label,
                            fontSize = 16.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    innerTextField()
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            )
        )

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        ) {
            drawLine(
                color = underlineColor,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = 1.dp.toPx()
            )
        }
    }
}
