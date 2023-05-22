package com.spinoza.learningvknews.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class NavigationState(val navHostController: NavHostController) {

    fun navigateTo(route: String) {
        navHostController.navigate(route) {
            navHostController.graph.startDestinationRoute?.let {
                popUpTo(it) {
                    saveState = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController(),
): NavigationState = remember {
    NavigationState(navHostController)
}