package com.spinoza.learningvknews.presentation.feature.homescreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spinoza.learningvknews.domain.FeedPost
import com.spinoza.learningvknews.presentation.ui.PostCard
import com.spinoza.learningvknews.presentation.util.DISMISS_THRESHOLDS
import com.spinoza.learningvknews.presentation.util.HOME_BOTTOM_SIZE
import com.spinoza.learningvknews.presentation.util.SIZE_MEDIUM
import com.spinoza.learningvknews.presentation.util.SIZE_SMALL
import com.spinoza.learningvknews.presentation.viewmodel.MainViewModel

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun PostsScreen(
    posts: List<FeedPost>,
    viewModel: MainViewModel,
    paddingValues: PaddingValues,
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(
            top = SIZE_MEDIUM.dp,
            start = SIZE_SMALL.dp,
            end = SIZE_SMALL.dp,
            bottom = HOME_BOTTOM_SIZE.dp
        ),
        verticalArrangement = Arrangement.spacedBy(SIZE_SMALL.dp)
    ) {
        items(posts, key = { it.id }) { feedPost ->
            val dismissState = rememberDismissState()

            if (dismissState.isDismissed(DismissDirection.EndToStart) ||
                dismissState.isDismissed(DismissDirection.StartToEnd)
            ) {
                viewModel.delete(feedPost)
            }

            SwipeToDismiss(
                modifier = Modifier.animateItemPlacement(),
                state = dismissState,
                dismissThresholds = { FractionalThreshold(DISMISS_THRESHOLDS) },
                background = { },
                directions = setOf(DismissDirection.EndToStart, DismissDirection.StartToEnd)
            ) {
                PostCard(
                    feedPost = feedPost,
                    onStatisticClickListener = { statisticItem ->
                        viewModel.updateCount(feedPost, statisticItem)
                    },
                    onCommentsClickListener = viewModel::showComments
                )
            }
        }
    }
}