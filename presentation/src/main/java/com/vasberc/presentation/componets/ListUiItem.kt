package com.vasberc.presentation.componets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ListUiItem(
    listName: String,
    isLastItem: Boolean,
    isFirstItem: Boolean,
    onClick: () -> Unit,
    on3DotsClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .clickable { onClick() },
        //top is rounded if is first item, bottom is rounded if is last item
        shape = RoundedCornerShape(
            topStart = if (isFirstItem) 15.dp else 0.dp,
            topEnd = if (isFirstItem) 15.dp else 0.dp,
            bottomStart = if (isLastItem) 15.dp else 0.dp,
            bottomEnd = if (isLastItem) 15.dp else 0.dp
        )

    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(15.dp)
        ) {
            Text(
                text = listName,
                modifier = Modifier.weight(1f),
                fontSize = 21.sp
            )
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Options",
                modifier = Modifier.clickable { on3DotsClick() }
            )
        }
    }
}