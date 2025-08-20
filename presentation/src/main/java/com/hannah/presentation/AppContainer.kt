package com.hannah.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
internal fun AppContainer(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavigationItem.List
    ) {
        composable(NavigationItem.List.route) {

        }
        composable(NavigationItem.Detail.route) {

        }
    }
}

private enum class Screen {
    LIST,
    DETAIL,
}
private sealed class NavigationItem(val route: String) {
    object List : NavigationItem(Screen.LIST.name)
    object Detail : NavigationItem(Screen.DETAIL.name)
}