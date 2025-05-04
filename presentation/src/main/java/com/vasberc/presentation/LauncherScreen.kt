package com.vasberc.presentation

import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.util.UnstableApi
import androidx.navigation.compose.rememberNavController
import com.vasberc.presentation.componets.BottomNavigation
import com.vasberc.presentation.componets.ControlButtons
import com.vasberc.presentation.navigation.bottomnavigation.BottomNavHost
import com.vasberc.presentation.utils.MusicPlayer
import org.koin.compose.koinInject

@OptIn(UnstableApi::class)
@Composable
fun LauncherScreen(
    player: MusicPlayer = koinInject()
) {
    val navController = rememberNavController()
    val currentPlaying = player.playingSongIndex.collectAsStateWithLifecycle().value
    val isPlaying by remember(currentPlaying) {
        derivedStateOf { currentPlaying != null }
    }
    Scaffold(
        bottomBar = {
            BottomNavigation(navController)
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            BottomNavHost(
                navController = navController,
                modifier = Modifier.weight(1f)
            )
            AnimatedVisibility(isPlaying) {
                Column(
                    modifier = Modifier.background(Color.Black.copy(alpha = 0.2f))
                ) {
                    Spacer(Modifier.height(10.dp))
                    player.mediaSession?.player?.let {
                        Text(
                            text = it.currentMediaItem?.mediaMetadata?.displayTitle?.toString() ?: "",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(5.dp))
                        ControlButtons(player = it)
                    }
                    Spacer(Modifier.height(5.dp))
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LauncherScreen()
}