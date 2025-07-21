package com.uttkarsh.InstaStudio.presentation.ui.MemberPages

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.uttkarsh.InstaStudio.R
import com.uttkarsh.InstaStudio.presentation.ui.utils.NoteMarkTextField

@Composable
fun MemberSheet(
    email: String,
    specialization: String,
    salary: Long,
    heading: String,
    errorMessage: String? = null,
    onSalaryChange: (Long) -> Unit,
    onEmailChange: (String) -> Unit,
    onSpecializationChange: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    val alatsiFont = FontFamily(Font(R.font.alatsi))
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var salaryText by remember(salary) { mutableStateOf(if (salary == 0L) "" else salary.toString()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 32.dp, start = 16.dp, end =  16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(heading,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                fontFamily = alatsiFont,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(Modifier.height(30.dp))

        NoteMarkTextField(
            text = email,
            onValueChange = onEmailChange,
            label = "Name",
            hint = "Sony A3",
            isNumberType = false,
            haveTrailingIcon = false,
            trailingIconConfig = null
        )

        NoteMarkTextField(
            text = salaryText,
            onValueChange = {  newText ->
                salaryText = newText
                val parsed = newText.toLongOrNull()
                if (parsed != null) {
                    onSalaryChange(parsed)
                }else{
                    Toast.makeText(context, "Invalid Price", Toast.LENGTH_SHORT).show()
                }
            },
            label = "Salary",
            hint = "₹1000",
            isNumberType = true,
            haveTrailingIcon = false,
            trailingIconConfig = null
        )

        NoteMarkTextField(
            text = specialization,
            onValueChange = onSpecializationChange,
            label = "Specialization",
            hint = "Gimble",
            isNumberType = false,
            haveTrailingIcon = false,
            trailingIconConfig = null
        )

        if (!errorMessage.isNullOrBlank()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(4.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onCancel,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(27.dp),
                modifier = Modifier.width(100.dp)
            ) {
                Text("Cancel", fontFamily = alatsiFont)
            }

            Button(onClick = onSave,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(27.dp),
                modifier = Modifier.width(100.dp)
            ) {
                Text("Save", fontFamily = alatsiFont)
            }
        }
    }
}