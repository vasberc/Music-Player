package com.vasberc.presentation.navigation.bottomnavigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vasberc.presentation.screens.home.HomeScreen
import com.vasberc.presentation.screens.settings.SettingsScreen

@Composable
fun BottomNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = Route.Home, modifier = modifier) {
        composable<Route.Home> {
            HomeScreen()
        }
        composable<Route.Settings> {
            SettingsScreen()
        }
    }
}