package com.uttkarsh.InstaStudio.presentation.ui.EventPages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.uttkarsh.InstaStudio.R
import shimmerEffect

@Composable
fun EventShimmerEffect(
    modifier: Modifier = Modifier,
    color: Color
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(27.dp))
            .shimmerEffect()
            .background(color = colorResource(R.color.shimmer))
            .border(
                width = 1.dp,
                color = colorResource(R.color.shimmer),
                shape = RoundedCornerShape(27.dp)
            )
    )
}

@Composable
fun EventShimmerShow(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        repeat(10) {
            EventShimmerEffect(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}