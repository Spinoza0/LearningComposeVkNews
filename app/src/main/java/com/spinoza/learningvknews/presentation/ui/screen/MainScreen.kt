package com.spinoza.learningvknews.presentation.ui.screen

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.spinoza.learningvknews.presentation.navigation.AppNavGraph
import com.spinoza.learningvknews.presentation.navigation.NavigationState
import com.spinoza.learningvknews.presentation.navigation.rememberNavigationState
import com.spinoza.learningvknews.presentation.navigation.NavigationItem
import com.spinoza.learningvknews.presentation.viewmodel.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel) {

    val navigationState = rememberNavigationState()

    Scaffold(
        bottomBar = { MainScreenBottomBar(navigationState) }
    ) { paddingValues ->
        AppNavGraph(
            navHostController = navigationState.navHostController,
            homeScreenContent = { HomeScreen(viewModel, paddingValues) },
            favouriteScreenContent = { /*TODO*/ },
            profileScreenContent = { /*TODO*/ })
    }
}

@Composable
private fun MainScreenBottomBar(
    navigationState: NavigationState,
) {
    BottomNavigation {
        val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
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
                onClick = { navigationState.navigateTo(item.screen.route) },
                icon = { Icon(item.icon, contentDescription = title) },
                label = { Text(title) },
                selectedContentColor = MaterialTheme.colors.onPrimary,
                unselectedContentColor = MaterialTheme.colors.onSecondary
            )
        }
    }
}