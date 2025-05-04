package com.vasberc.presentation.navigation.bottomnavigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vasberc.presentation.navigation.homenavigation.HomeNavigation
import com.vasberc.presentation.navigation.listsnavigation.ListsNavigation

@Composable
fun BottomNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = Route.Home, modifier = modifier) {
        composable<Route.Home> {
            HomeNavigation(
                navController = rememberNavController()
            )
        }
        composable<Route.Settings> {
            ListsNavigation(
                navController = rememberNavController()
            )
        }
    }
}