package com.spinoza.learningvknews.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.spinoza.learningvknews.R

sealed class NavigationItem(
    val screen: Screen,
    val titleResId: Int,
    val icon: ImageVector,
) {

    object Home :
        NavigationItem(
            Screen.NewsFeed,
            R.string.navigation_item_main,
            Icons.Outlined.Home
        )

    object Favourite : NavigationItem(
        Screen.Favourite,
        R.string.navigation_item_favourite,
        Icons.Outlined.Favorite
    )

    object Profile :
        NavigationItem(
            Screen.Profile,
            R.string.navigation_item_profile,
            Icons.Outlined.Person
        )
}