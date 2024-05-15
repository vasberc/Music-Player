package com.vasberc.presentation.navigation.bottomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

sealed class Route {
    abstract val icon: ImageVector
    abstract val text: String

    @Serializable
    data object Home : Route() {
        override val icon: ImageVector = Icons.Default.Home
        override val text: String = "Home"
    }

    @Serializable
    data object Settings : Route() {
        override val icon: ImageVector = Icons.Default.Settings
        override val text: String = "Settings"
    }

    companion object {
        fun getRoutes(): List<Route> = listOf(
            Route.Home,
            Route.Settings
        )
    }
}