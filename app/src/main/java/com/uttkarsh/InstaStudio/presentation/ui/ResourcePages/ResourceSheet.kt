package com.uttkarsh.InstaStudio.presentation.ui.ResourcePages

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.uttkarsh.InstaStudio.R

@Composable
fun ResourceSheet(
    name: String,
    price: Long,
    heading: String,
    errorMessage: String? = null,
    onNameChange: (String) -> Unit,
    onPriceChange: (Long) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    val alatsiFont = FontFamily(Font(R.font.alatsi))
    val context = LocalContext.current

    var priceText by remember(price) { mutableStateOf(if (price == 0L) "" else price.toString()) }

    LaunchedEffect(name, price) {
        Log.d("EditScreen", "Got name: $name")
        Log.d("EditScreen", "Got price: $price")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(heading, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Name", fontFamily = alatsiFont, color = colorResource(R.color.grey)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = priceText,
            onValueChange = {  newText ->
                priceText = newText
                val parsed = newText.toLongOrNull()
                if (parsed != null) {
                    onPriceChange(parsed)
                }else{
                    Toast.makeText(context, "Invalid Price", Toast.LENGTH_SHORT).show()
                }
            },
            label = { Text("Price", fontFamily = alatsiFont, color = colorResource(R.color.grey)) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (!errorMessage.isNullOrBlank()) {
            Text(
                text = errorMessage,
                color = colorResource(R.color.errorRed),
                modifier = Modifier.padding(4.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(onClick = onCancel) {
                Text("Cancel", fontFamily = alatsiFont, color = colorResource(R.color.grey))
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(onClick = onSave,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.buttons),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(19.dp)
                ) {
                Text("Save")
            }
        }
    }
}

