package com.uttkarsh.InstaStudio.presentation.ui.ResourcePages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.uttkarsh.InstaStudio.R
import com.uttkarsh.InstaStudio.domain.model.dto.resource.ResourceResponseDTO
import com.uttkarsh.InstaStudio.presentation.ui.utils.rememberTimeProvider

@Composable
fun ResourceItem(
    resource: ResourceResponseDTO,
    onCLick: () -> Unit,
    ifInFocus: Boolean

) {
    val alatsiFont = FontFamily(Font(R.font.alatsi))

    val modifier = if(ifInFocus){
        Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .height(90.dp)
    }else{
        Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .height(90.dp)
    }

    Surface(
        modifier = modifier,
        shape = if(ifInFocus) RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp) else RoundedCornerShape(15.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        onClick = onCLick
    ){

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ){

            Row(
                modifier = Modifier
                    .wrapContentSize()
            ) {
                Image(painter = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = "Image",
                    modifier = Modifier.clip(RoundedCornerShape(15.dp))
                    )

            }

            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 32.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentWidth(),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.Start
                ) {

                    Text(text = resource.resourceName,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontFamily = alatsiFont,
                        fontWeight = FontWeight.Bold
                    )

                    Column {
                        Text(text = "₹${resource.resourcePrice}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontFamily = alatsiFont
                        )

                        Text(text = "Last used: ${resource.resourceLastUsedEvent?.eventStartDate}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontFamily = alatsiFont
                        )
                    }

                }

                Box(
                    modifier = Modifier.size(30.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.cyancircle),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        tint = MaterialTheme.colorScheme.secondary
                    )

                    val timeProvider = rememberTimeProvider()
                    if(resource.resourceLastUsedEvent?.evenIsSaved == true && resource.resourceLastUsedEvent.eventEndDate >= timeProvider.nowDate()){
                        Icon(
                            painter = painterResource(R.drawable.cyancircle),
                            contentDescription = null,
                            modifier = Modifier.size(10.dp),
                            tint = MaterialTheme.colorScheme.errorContainer
                        )
                    }else{
                        Icon(
                            painter = painterResource(R.drawable.cyancircle),
                            contentDescription = null,
                            modifier = Modifier.size(10.dp),
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }

            }

        }

    }
}