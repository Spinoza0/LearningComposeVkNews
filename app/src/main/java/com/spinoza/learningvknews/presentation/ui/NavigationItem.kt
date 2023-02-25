package com.spinoza.learningvknews.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.spinoza.learningvknews.R

sealed class NavigationItem(
    val titleResId: Int,
    val icon: ImageVector,
) {
    object Home : NavigationItem(R.string.navigation_item_main, Icons.Outlined.Home)
    object Favourite : NavigationItem(R.string.navigation_item_favourite, Icons.Outlined.Favorite)
    object Profile : NavigationItem(R.string.navigation_item_profile, Icons.Outlined.Person)
}