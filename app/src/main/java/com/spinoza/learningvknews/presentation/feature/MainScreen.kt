package com.spinoza.learningvknews.presentation.feature

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.spinoza.learningvknews.domain.FeedPost
import com.spinoza.learningvknews.presentation.feature.homescreen.CommentsScreen
import com.spinoza.learningvknews.presentation.feature.homescreen.HomeScreen
import com.spinoza.learningvknews.presentation.navigation.AppNavGraph
import com.spinoza.learningvknews.presentation.navigation.NavigationItem
import com.spinoza.learningvknews.presentation.navigation.NavigationState
import com.spinoza.learningvknews.presentation.navigation.rememberNavigationState

@Composable
fun MainScreen() {
    val navigationState = rememberNavigationState()

    val commentsToPost: MutableState<FeedPost?> = remember { mutableStateOf(null) }

    Scaffold(
        bottomBar = { MainScreenBottomBar(navigationState) }
    ) { paddingValues ->
        AppNavGraph(
            navHostController = navigationState.navHostController,
            homeScreenContent = {
                if (commentsToPost.value == null) {
                    HomeScreen(paddingValues) {
                        commentsToPost.value = it
                    }
                } else {
                    CommentsScreen {
                        commentsToPost.value = null
                    }
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