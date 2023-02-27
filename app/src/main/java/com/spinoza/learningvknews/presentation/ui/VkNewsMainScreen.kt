package com.spinoza.learningvknews.presentation.ui

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.spinoza.learningvknews.navigation.AppNavGraph
import com.spinoza.learningvknews.navigation.Screen
import com.spinoza.learningvknews.presentation.HomeScreen
import com.spinoza.learningvknews.presentation.viewmodel.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel) {

    val navHostController = rememberNavController()

    Scaffold(
        bottomBar = { MainScreenBottomBar(navHostController) }
    ) { paddingValues ->
        AppNavGraph(
            navHostController = navHostController,
            homeScreenContent = { HomeScreen(viewModel, paddingValues) },
            favouriteScreenContent = { /*TODO*/ },
            profileScreenContent = { /*TODO*/ })
    }
}

@Composable
private fun MainScreenBottomBar(
    navHostController: NavHostController,
) {
    BottomNavigation {
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val items = listOf(
            NavigationItem.Home,
            NavigationItem.Favourite,
            NavigationItem.Profile
        )

        items.forEach { item ->
            val title = stringResource(item.titleResId)
            BottomNavigationItem(
                selected = currentRoute == item.screen.route,
                onClick = {
                    navHostController.navigate(item.screen.route) {
                        popUpTo(Screen.NewsFeed.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = title) },
                label = { Text(title) },
                selectedContentColor = MaterialTheme.colors.onPrimary,
                unselectedContentColor = MaterialTheme.colors.onSecondary
            )
        }
    }
}