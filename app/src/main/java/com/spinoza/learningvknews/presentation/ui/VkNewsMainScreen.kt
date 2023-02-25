package com.spinoza.learningvknews.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.spinoza.learningvknews.domain.FeedPost
import com.spinoza.learningvknews.domain.StatisticItem

@Composable
fun MainScreen() {

    val feedPost = remember { mutableStateOf(FeedPost()) }

    Scaffold(
        bottomBar = { MainScreenBottomBar() }
    ) {
        PostCard(
            modifier = Modifier.padding(it),
            feedPost = feedPost.value,
            onStatisticItemClickListener = { statisticItem ->
                statisticItemClickListener(statisticItem, feedPost)
            }
        )
    }
}

private fun statisticItemClickListener(
    newItem: StatisticItem,
    feedPost: MutableState<FeedPost>,
) {
    val oldStatistics = feedPost.value.statistics
    val newStatistics = oldStatistics.toMutableList().apply {
        replaceAll { oldItem ->
            if (oldItem.type == newItem.type) {
                oldItem.copy(count = oldItem.count + 1)
            } else {
                oldItem
            }
        }
    }
    feedPost.value = feedPost.value.copy(statistics = newStatistics)
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