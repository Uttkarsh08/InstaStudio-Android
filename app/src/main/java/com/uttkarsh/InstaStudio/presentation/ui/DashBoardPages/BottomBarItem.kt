package com.uttkarsh.InstaStudio.presentation.ui.DashBoardPages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination

@Composable
fun BottomBarItem(
    item: NavigationItem,
    selected: Boolean,
    onClick: () -> Unit,
    currentDestination: NavDestination?

) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = { onClick() }) {
            if (selected){
                Image(
                    painter = painterResource(id =  item.selectedIcon),
                    contentDescription = item.iconContentDescription,
                    modifier = Modifier.size(28.dp)
                )
            }else{
                Image(
                    painter = painterResource(id =  item.icon),
                    contentDescription = item.iconContentDescription,
                    modifier = Modifier.size(28.dp)
                )
            }

        }
    }
}
