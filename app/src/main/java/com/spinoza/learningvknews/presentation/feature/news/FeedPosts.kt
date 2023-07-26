package com.spinoza.learningvknews.presentation.feature.news

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spinoza.learningvknews.domain.model.FeedPost
import com.spinoza.learningvknews.presentation.feature.news.viewmodel.NewsFeedViewModel
import com.spinoza.learningvknews.presentation.theme.DarkBlue
import com.spinoza.learningvknews.presentation.util.DISMISS_THRESHOLDS
import com.spinoza.learningvknews.presentation.util.NEWS_FEED_BOTTOM_SIZE
import com.spinoza.learningvknews.presentation.util.SIZE_MEDIUM
import com.spinoza.learningvknews.presentation.util.SIZE_SMALL

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun FeedPosts(
    posts: List<FeedPost>,
    viewModel: NewsFeedViewModel,
    paddingValues: PaddingValues,
    isNextDataLoading: Boolean,
    onCommentsClickListener: (FeedPost) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(
            top = SIZE_MEDIUM.dp,
            start = SIZE_SMALL.dp,
            end = SIZE_SMALL.dp,
            bottom = NEWS_FEED_BOTTOM_SIZE.dp
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
                    onViewsClickListener = { statisticItem ->
                        viewModel.updateCount(feedPost, statisticItem)
                    },
                    onShareClickListener = { statisticItem ->
                        viewModel.updateCount(feedPost, statisticItem)
                    },
                    onLikeClickListener = { statisticItem ->
                        viewModel.changeLikeStatus(feedPost)
                    },
                    onCommentsClickListener = {
                        onCommentsClickListener(feedPost)
                    }
                )
            }
        }
        item {
            if (isNextDataLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(SIZE_MEDIUM.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = DarkBlue)
                }
            } else {
                SideEffect {
                    viewModel.loadNextRecommendations()
                }
            }
        }
    }
}