package com.uttkarsh.InstaStudio.presentation.ui.ResourcePages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import shimmerEffect


@Composable
fun ResourceShimmerEffect(
    modifier: Modifier = Modifier,
    color: Color
) {
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(90.dp)
                .clip(RoundedCornerShape(15.dp))
                .shimmerEffect()
                .background(color)
                .border(1.dp, color = color, shape = RoundedCornerShape(15.dp))
        )
    }
}

@Composable
fun ResourceShimmerShow(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        repeat(10) {
            ResourceShimmerEffect(
                modifier = Modifier
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.surfaceContainerLow
            )
        }
    }
}