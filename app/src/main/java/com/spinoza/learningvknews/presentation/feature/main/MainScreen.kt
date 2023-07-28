package com.spinoza.learningvknews.presentation.feature.main

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.spinoza.learningvknews.presentation.feature.comments.CommentsScreen
import com.spinoza.learningvknews.presentation.feature.main.model.NavigationItem
import com.spinoza.learningvknews.presentation.feature.news.NewsFeedScreen
import com.spinoza.learningvknews.presentation.navigation.AppNavGraph
import com.spinoza.learningvknews.presentation.navigation.NavigationState
import com.spinoza.learningvknews.presentation.navigation.rememberNavigationState

@Composable
fun MainScreen() {
    val navigationState = rememberNavigationState()

    Scaffold(
        bottomBar = { MainScreenBottomBar(navigationState) }
    ) { paddingValues ->
        AppNavGraph(
            navHostController = navigationState.navHostController,
            newsFeedScreenContent = {
                NewsFeedScreen(paddingValues) { feedPost ->
                    navigationState.navigateToComments(feedPost)
                }
            },
            commentsScreenContent = { feedPost ->
                CommentsScreen(feedPost) {
                    navigationState.navHostController.popBackStack()
                }
            },
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
        val items = listOf(
            NavigationItem.Home,
            NavigationItem.Favourite,
            NavigationItem.Profile
        )
        items.forEach { item ->
            val title = stringResource(item.titleResId)
            val isSelected = navBackStackEntry?.destination?.hierarchy?.any {
                it.route == item.screen.route
            } ?: false
            BottomNavigationItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navigationState.navigateTo(item.screen.route)
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