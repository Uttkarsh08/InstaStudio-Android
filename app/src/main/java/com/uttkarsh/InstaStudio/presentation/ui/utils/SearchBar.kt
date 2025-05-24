package com.uttkarsh.InstaStudio.presentation.ui.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.uttkarsh.InstaStudio.R

@Composable
fun SearchBar(onSearchChanged: (String) -> Unit) {

    val alatsiFont = FontFamily(Font(R.font.alatsi))
    var query by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
            .border(BorderStroke(1.dp, colorResource(R.color.searchBarBorder)),
                RoundedCornerShape(14.dp))

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextField(
                value = query,
                onValueChange = { query = it; onSearchChanged(query) },
                placeholder = { Text("Search", fontFamily = alatsiFont) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(color = colorResource(id = R.color.searchBar)),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Black
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorResource(id = R.color.searchBar),
                    unfocusedContainerColor = colorResource(id = R.color.searchBar),
                    focusedIndicatorColor = colorResource(id = R.color.searchBar),
                    unfocusedIndicatorColor = colorResource(id = R.color.searchBar),
                    cursorColor = Color.Black
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchChanged(query)
                    }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                ),
                shape = RoundedCornerShape(12.dp),
                textStyle = TextStyle(fontFamily = alatsiFont)
            )
        }
    }
}
