package com.vasberc.presentation.componets

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun EqualizerIcon(isPlaying: Boolean) {
    // Define the Animatable variables for each bar
    val animBar1 = remember { Animatable(1f) }
    val animBar2 = remember { Animatable(1f) }
    val animBar3 = remember { Animatable(1f) }

    val scope = rememberCoroutineScope() //keeping the coroutine to sto the while loops when isPlaying changes to false

    // Define a LaunchedEffect to continuously animate when isPlaying is true
    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            // Start continuous animation for each bar
            scope.launch {
                while (isPlaying) {
                    animBar1.animateTo(
                        targetValue = 1f * Math.random().toFloat(),
                        animationSpec = tween(durationMillis = 400, easing = LinearEasing)
                    )
                }
            }
            scope.launch {
                while (isPlaying) {
                    animBar2.animateTo(
                        targetValue = 0.5f * Math.random().toFloat(),
                        animationSpec = tween(durationMillis = 500, easing = LinearEasing)
                    )
                }
            }
            scope.launch {
                while (isPlaying) {
                    animBar3.animateTo(
                        targetValue = 0.5f * Math.random().toFloat(),
                        animationSpec = tween(durationMillis = 600, easing = LinearEasing)
                    )
                }
            }
        } else {
            // Reset the heights when not playing
            animBar1.snapTo(1f)
            animBar2.snapTo(1f)
            animBar3.snapTo(1f)
        }
    }

    if (isPlaying) {
        Box(
            modifier = Modifier
                .size(24.dp), // Set the size of the Box
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp), // Space between bars
                verticalAlignment = Alignment.Bottom, // Align bars to the bottom
                modifier = Modifier.fillMaxSize()
            ) {
                EqualizerBar(height = animBar1.value)
                EqualizerBar(height = animBar2.value)
                EqualizerBar(height = animBar3.value)
            }
        }
    }
}

@Composable
fun EqualizerBar(height: Float) {
    Box(
        modifier = Modifier
            .width(6.dp) // Width of each bar
            .height(50.dp * height) // Scale the height based on the animated value
            .background(Color.Green) // Bar color
    )
}
