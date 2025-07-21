package com.uttkarsh.InstaStudio.presentation.ui.MemberPages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.floor

@Composable
fun StarRating(
    rating: Double,
    modifier: Modifier = Modifier,
    starCount: Int = 5,
    starSize: Dp = 24.dp,
    filledColor: Color = Color(0xFFFFC107),
    unfilledColor: Color = Color(0xFFFFEC81)
) {
    val filledStars = floor(rating).toInt()
    val hasHalfStar = (rating - filledStars) > 0
    val partialFraction = rating - filledStars

    Row(modifier = modifier) {
        for (i in 0 until starCount) {
            if (i < filledStars) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = filledColor,
                    modifier = Modifier.size(starSize)
                )
            } else if (i == filledStars && hasHalfStar) {
                Box(modifier = Modifier.size(starSize)) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = unfilledColor,
                        modifier = Modifier.matchParentSize()
                    )
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = filledColor,
                        modifier = Modifier
                            .matchParentSize()
                            .clip(RectangleShape)
                            .graphicsLayer {
                                clip = true
                                shape = RectangleShape
                            }
                            .drawWithContent {
                                clipRect(right = size.width * partialFraction.toFloat()) {
                                    this@drawWithContent.drawContent()
                                }
                            }
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = unfilledColor,
                    modifier = Modifier.size(starSize)
                )
            }
        }
    }
}
