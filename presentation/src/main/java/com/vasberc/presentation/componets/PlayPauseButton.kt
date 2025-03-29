package com.vasberc.presentation.componets

import androidx.annotation.OptIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.compose.state.rememberPlayPauseButtonState
import com.vasberc.presentation.R

@OptIn(UnstableApi::class)
@Composable
fun PlayPauseButton(player: Player, modifier: Modifier = Modifier) {
    val state = rememberPlayPauseButtonState(player)
    IconButton(onClick = state::onClick, modifier = modifier, enabled = state.isEnabled) {
        Icon(
            imageVector = if (state.showPlay) Icons.Default.PlayArrow else Icons.Default.Pause,
            contentDescription =
                if (state.showPlay) stringResource(R.string.playpause_button_play)
                else stringResource(R.string.playpause_button_pause),
        )
    }
}