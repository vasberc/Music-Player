package com.vasberc.presentation.navigation.listsnavigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.vasberc.presentation.screens.lists.ListScreen
import kotlinx.serialization.Serializable

@Composable
fun ListsNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = ListsRoute.Home, modifier = modifier) {
        composable<ListsRoute.Home> {
            ListScreen(navHostController = navController)
        }
        composable<ListsRoute.List> { backStackEntry ->
            val list =  backStackEntry.toRoute<ListsRoute.List>()
            Text(list.listName)
        }
    }
}

sealed class ListsRoute {
    @Serializable
    data object Home: ListsRoute()
    @Serializable
    data class List(
        val listName: String
    ): ListsRoute()
}