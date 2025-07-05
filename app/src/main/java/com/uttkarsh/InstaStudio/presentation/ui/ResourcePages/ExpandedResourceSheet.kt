package com.uttkarsh.InstaStudio.presentation.ui.ResourcePages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.uttkarsh.InstaStudio.R
import com.uttkarsh.InstaStudio.domain.model.dto.resource.ResourceResponseDTO
import com.uttkarsh.InstaStudio.presentation.ui.utils.rememberTimeProvider

@Composable
fun ExpandedResourceSheet(
    resource: ResourceResponseDTO,
    onEditClicked: () -> Unit,
    onDeleteClicked: () -> Unit
) {
    val alatsiFont = FontFamily(Font(R.font.alatsi))

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 0.dp),
        shape = RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLow
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(resource.resourceLastUsedEvent?.eventType ?: "Not Used",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontFamily = alatsiFont,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Location: ${resource.resourceLastUsedEvent?.eventLocation}",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontFamily = alatsiFont
                    )

                }

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    val timeProvider = rememberTimeProvider()
                    Text(
                        "Registered At: ${timeProvider.formatDate(resource.resourceRegisteredAt).toString()}",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontFamily = alatsiFont
                    )

                }

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = onDeleteClicked,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    shape = RoundedCornerShape(27.dp),
                    modifier = Modifier
                        .width(100.dp)
                        .height(35.dp),
                ) {
                    Text("Delete", fontFamily = alatsiFont)
                }

                Spacer(Modifier.height(16.dp))

                Button(onClick = onEditClicked,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(27.dp),
                    modifier = Modifier
                        .width(100.dp)
                        .height(35.dp)
                ) {
                    Text("Edit", fontFamily = alatsiFont)
                }
            }

        }
    }

}