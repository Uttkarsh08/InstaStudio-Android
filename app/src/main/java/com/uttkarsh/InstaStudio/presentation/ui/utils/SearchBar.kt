package com.uttkarsh.InstaStudio.presentation.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.CompositionLocalProvider
import com.uttkarsh.InstaStudio.R

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearchChanged: (String) -> Unit
) {
    val alatsiFont = FontFamily(Font(R.font.alatsi))
    var query by remember { mutableStateOf("") }

    val customSelectionColors = TextSelectionColors(
        handleColor = MaterialTheme.colorScheme.primaryContainer,
        backgroundColor = MaterialTheme.colorScheme.surfaceContainerLowest
    )

    CompositionLocalProvider(LocalTextSelectionColors provides customSelectionColors) {
        Box(
            modifier = modifier
                .height(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.onPrimaryContainer)
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))

                BasicTextField(
                    value = query,
                    onValueChange = {
                        query = it
                        onSearchChanged(query)
                    },
                    singleLine = true,
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontFamily = alatsiFont
                    ),
                    modifier = Modifier.weight(1f),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primaryContainer)
                ) { innerTextField ->
                    if (query.isEmpty()) {
                        Text(
                            text = "Search",
                            fontFamily = alatsiFont,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
                        )
                    }
                    innerTextField()
                }
            }
        }
    }
}
