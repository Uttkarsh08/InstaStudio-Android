package com.uttkarsh.InstaStudio.presentation.ui.MemberPages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.uttkarsh.InstaStudio.R
import com.uttkarsh.InstaStudio.domain.model.dto.member.MemberProfileResponseDTO
import com.uttkarsh.InstaStudio.domain.model.dto.resource.ResourceResponseDTO
import com.uttkarsh.InstaStudio.presentation.ui.utils.rememberTimeProvider

@Composable
fun MemberItem(
    member: MemberProfileResponseDTO,
    onCLick: () -> Unit,
    ifInFocus: Boolean

) {
    val alatsiFont = FontFamily(Font(R.font.alatsi))

        val modifier = if (ifInFocus) {
            Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .height(90.dp)
        } else {
            Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .height(90.dp)
    }

    Surface(
        modifier = modifier,
        shape = if (ifInFocus) RoundedCornerShape(
            topStart = 15.dp,
            topEnd = 15.dp
        ) else RoundedCornerShape(15.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        onClick = onCLick
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(R.drawable.memberprofile),
                contentDescription = "Image",
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .wrapContentSize()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 30.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {

                    Text(
                        text = member.memberName,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontFamily = alatsiFont,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "₹${member.salary}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontFamily = alatsiFont
                    )
                }

                Box(
                    modifier = Modifier
                        .width(110.dp)
                        .shadow(
                            elevation = 2.dp,
                            shape = RoundedCornerShape(15.dp),
                            clip = true
                        )
                        .background(
                            MaterialTheme.colorScheme.onPrimaryContainer,
                            RoundedCornerShape(15.dp)
                        )
                        .height(35.dp)
                        .align(Alignment.CenterVertically),
                    contentAlignment = Alignment.Center,

                    ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .background(MaterialTheme.colorScheme.onPrimaryContainer),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        StarRating(
                            rating = 3.6,  //change with actual value
                            starSize = 18.dp,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }

        }

    }
}