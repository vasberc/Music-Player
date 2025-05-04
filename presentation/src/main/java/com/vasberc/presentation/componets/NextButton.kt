package com.vasberc.presentation.componets

import androidx.annotation.OptIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.compose.state.rememberNextButtonState
import com.vasberc.presentation.R


@OptIn(UnstableApi::class)
@Composable
fun NextButton(player: Player, modifier: Modifier = Modifier) {
    val state = rememberNextButtonState(player)
    IconButton(onClick = state::onClick, modifier = modifier, enabled = state.isEnabled) {
        Icon(
            imageVector = Icons.Default.SkipNext,
            contentDescription = stringResource(R.string.next)
        )
    }
}