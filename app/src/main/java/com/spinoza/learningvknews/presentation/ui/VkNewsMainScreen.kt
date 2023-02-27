package com.spinoza.learningvknews.presentation.ui

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import com.spinoza.learningvknews.presentation.HomeScreen
import com.spinoza.learningvknews.presentation.viewmodel.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel) {

    val selectedNavItem by viewModel.selectedNavItem.observeAsState(NavigationItem.Home)

    Scaffold(
        bottomBar = {
            MainScreenBottomBar(selectedNavItem) { viewModel.selectNavItem(it) }
        }
    ) {paddingValues ->
        when (selectedNavItem) {
            NavigationItem.Home -> HomeScreen(viewModel, paddingValues)
            else -> {}
        }
    }
}

@Composable
private fun MainScreenBottomBar(
    selectedNavItem: NavigationItem,
    onSelectItem: (NavigationItem) -> Unit,
) {
    BottomNavigation {
        val items = listOf(
            NavigationItem.Home,
            NavigationItem.Favourite,
            NavigationItem.Profile
        )

        items.forEach { item ->
            val title = stringResource(item.titleResId)
            BottomNavigationItem(
                selected = selectedNavItem == item,
                onClick = { onSelectItem(item) },
                icon = { Icon(item.icon, contentDescription = title) },
                label = { Text(title) },
                selectedContentColor = MaterialTheme.colors.onPrimary,
                unselectedContentColor = MaterialTheme.colors.onSecondary
            )
        }
    }
}