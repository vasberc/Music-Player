package com.vasberc.presentation.componets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vasberc.domain.model.MusicModel
import com.vasberc.presentation.R
import com.vasberc.presentation.utils.toDurationString

@Composable
fun MusicFileUiItem(
    item: MusicModel,
    isPlaying: Boolean,
    isPaused: Boolean,
    onClick: () -> Unit,
    onAddToPlaylist: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(5.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.baseline_music_note_24),
                contentDescription = item.fileName,
                modifier = Modifier.size(50.dp)
            )
            if (isPlaying && !isPaused) {
                EqualizerIcon()
            }
            if (isPlaying && isPaused) {
                Icon(
                    imageVector = Icons.Default.Pause,
                    contentDescription = "Paused",
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(5.dp))
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Artist: ",
                    color = Color.Gray
                )
                Text(
                    text = item.artist,
                    color = Color.Gray
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = item.title,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(2.dp))
                Box {
                    Icon(
                        imageVector = if (item.listsAdded.isEmpty())
                            Icons.Default.AddCircleOutline
                        else
                            Icons.Outlined.Circle,
                        contentDescription = if (item.listsAdded.isEmpty())
                            "Add to list"
                        else
                            "Added to ${item.listsAdded.size} lists click to add it to an other list",
                        modifier = Modifier.background(
                            color = if (item.listsAdded.isEmpty()) Color.Transparent else Color.Green,
                            shape = CircleShape
                        ).clickable {
                            onAddToPlaylist()
                        }
                    )
                    if (item.listsAdded.isNotEmpty()) {
                        Text(
                            text = "${item.listsAdded.size}",
                            fontSize = 12.sp,
                            color = Color.Black,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = item.duration.toDurationString(),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Album: ",
                    color = Color.Gray
                )
                Text(
                    text = item.album,
                            color = Color.Gray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMusicFileUiItem() {
    MusicFileUiItem(
        item = MusicModel(
            fileName = "filename",
            filePath = "filepath",
            title = "title3333333333333333333333333333333333333333",
            artist = "artist",
            album = "album",
            duration = 1000L,
            size = 1000000L,
            listsAdded = listOf("", "")
        ),
        isPlaying = false,
        isPaused = false,
        onClick = {},
        onAddToPlaylist = {}

    )
}