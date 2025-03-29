package com.vasberc.presentation.componets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ToolbarUiItem(text: String) {
    Text(text, fontSize = 26.sp, modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp, 15.dp),
        textAlign = TextAlign.Center
    )
}