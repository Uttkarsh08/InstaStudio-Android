package com.uttkarsh.InstaStudio.presentation.ui.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.uttkarsh.InstaStudio.R

@Composable
fun NoteMarkTextField(
    text: String,
    onValueChange: (String) -> Unit,
    label: String?,
    hint: String,
    isNumberType: Boolean,
    haveTrailingIcon: Boolean,
    trailingIconConfig: TrailingIconConfig? = null,
    readOnly: Boolean = false,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val alatsiFont = FontFamily(Font(R.font.alatsi))

    val customSelectionColors = TextSelectionColors(
        handleColor = MaterialTheme.colorScheme.primaryContainer,
        backgroundColor = MaterialTheme.colorScheme.surfaceContainerLowest
    )

    CompositionLocalProvider(LocalTextSelectionColors provides customSelectionColors) {
        Column(modifier = modifier) {
            if (label != null) {
                Text(
                    text = label,
                    fontFamily = alatsiFont,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                OutlinedTextField(
                    value = text,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .fillMaxSize(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                        cursorColor = MaterialTheme.colorScheme.primaryContainer,
                        focusedBorderColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    singleLine = true,
                    readOnly = readOnly,
                    placeholder = {
                        Text(
                            text = hint,
                            fontFamily = alatsiFont,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    shape = RoundedCornerShape(10.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = if (isNumberType) KeyboardType.Number else KeyboardType.Text
                    ),
                    trailingIcon = {
                        if (haveTrailingIcon) {
                            when (trailingIconConfig) {
                                is TrailingIconConfig.ResourceIcon -> {
                                    Icon(
                                        painter = painterResource(id = trailingIconConfig.resId),
                                        contentDescription = trailingIconConfig.contentDescription,
                                        modifier = Modifier.height(15.dp)
                                    )
                                }
                                is TrailingIconConfig.ImageVectorIcon -> {
                                    Icon(
                                        imageVector = trailingIconConfig.imageVector,
                                        contentDescription = trailingIconConfig.contentDescription,
                                        modifier = Modifier.height(15.dp)
                                    )
                                }
                                is TrailingIconConfig.ComposableIcon -> {
                                    trailingIconConfig.content()
                                }
                                null -> {}
                            }
                        }
                    }
                )

                if (readOnly && onClick != null) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onClick()
                            }
                    )
                }
            }
        }
    }
}




sealed class TrailingIconConfig {
    data class ResourceIcon(val resId: Int, val contentDescription: String? = null) : TrailingIconConfig()
    data class ImageVectorIcon(val imageVector: ImageVector, val contentDescription: String? = null) : TrailingIconConfig()
    data class ComposableIcon(val content: @Composable () -> Unit) : TrailingIconConfig()
}