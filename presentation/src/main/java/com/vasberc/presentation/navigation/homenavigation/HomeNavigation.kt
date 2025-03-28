package com.vasberc.presentation.navigation.homenavigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.vasberc.presentation.screens.home.allfolders.AllFoldersScreen
import com.vasberc.presentation.screens.home.folder.FolderScreen
import kotlinx.serialization.Serializable

@Composable
fun HomeNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = HomeRoute.Home, modifier = modifier) {
        composable<HomeRoute.Home> {
            AllFoldersScreen(navHostController = navController)
        }
        composable<HomeRoute.Folder> { backStackEntry ->
            val folderName =  backStackEntry.toRoute<HomeRoute.Folder>()
            FolderScreen(folderName.folderName)
        }
    }
}

sealed class HomeRoute {
    @Serializable
    data object Home: HomeRoute()
    @Serializable
    data class Folder(
        val folderName: String,
        val folderPath: String
    ): HomeRoute()
}