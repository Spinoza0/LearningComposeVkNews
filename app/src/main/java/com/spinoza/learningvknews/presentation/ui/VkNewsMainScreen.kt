package com.spinoza.learningvknews.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = { MainScreenFAB(snackbarHostState = snackbarHostState) },
        bottomBar = { MainScreenBottomBar() }
    ) {
    }
}

@Composable
private fun MainScreenFAB(snackbarHostState: SnackbarHostState) {
    val scope = rememberCoroutineScope()
    val fabIsVisible = remember { mutableStateOf(true) }

    if (fabIsVisible.value) {
        FloatingActionButton(onClick = {
            scope.launch {
                val action = snackbarHostState.showSnackbar(
                    message = "This is snackbar",
                    actionLabel = "Hide FAB",
                    duration = SnackbarDuration.Long
                )
                if (action == SnackbarResult.ActionPerformed) {
                    fabIsVisible.value = false
                }
            }
        }) {
            Icon(Icons.Filled.Favorite, null)
        }
    }
}

@Composable
private fun MainScreenBottomBar() {
    BottomNavigation {
        val items = listOf(
            NavigationItem.Home,
            NavigationItem.Favourite,
            NavigationItem.Profile
        )
        val selectedItemPosition = remember {
            mutableStateOf(FIRST_ITEM)
        }
        items.forEachIndexed() { index, item ->
            val title = stringResource(item.titleResId)
            BottomNavigationItem(
                selected = selectedItemPosition.value == index,
                onClick = { selectedItemPosition.value = index },
                icon = { Icon(item.icon, contentDescription = title) },
                label = { Text(title) },
                selectedContentColor = MaterialTheme.colors.onPrimary,
                unselectedContentColor = MaterialTheme.colors.onSecondary
            )
        }
    }
}

private const val FIRST_ITEM = 0