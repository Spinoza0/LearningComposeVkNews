package com.spinoza.learningvknews.presentation.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.spinoza.learningvknews.domain.PostComment
import com.spinoza.learningvknews.presentation.viewmodel.MainViewModel

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    paddingValues: PaddingValues,
) {
    val feedPosts = viewModel.feedPosts.observeAsState(listOf())

    if (feedPosts.value.isNotEmpty()) {
        val comments = mutableListOf<PostComment>().apply {
            repeat(20) {
                add(PostComment(it))
            }
        }
        CommentsScreen(feedPost = feedPosts.value[0], comments)
    }

//    LazyColumn(
//        modifier = Modifier.padding(paddingValues),
//        contentPadding = PaddingValues(
//            top = SIZE_MEDIUM.dp,
//            start = SIZE_SMALL.dp,
//            end = SIZE_SMALL.dp,
//            bottom = HOME_BOTTOM_SIZE.dp
//        ),
//        verticalArrangement = Arrangement.spacedBy(SIZE_SMALL.dp)
//    ) {
//        items(feedPosts.value, key = { it.id }) { feedPost ->
//            val dismissState = rememberDismissState()
//
//            if (dismissState.isDismissed(DismissDirection.EndToStart) ||
//                dismissState.isDismissed(DismissDirection.StartToEnd)
//            ) {
//                viewModel.delete(feedPost)
//            }
//
//            SwipeToDismiss(
//                modifier = Modifier.animateItemPlacement(),
//                state = dismissState,
//                dismissThresholds = { FractionalThreshold(DISMISS_THRESHOLDS) },
//                background = { },
//                directions = setOf(DismissDirection.EndToStart, DismissDirection.StartToEnd)
//            ) {
//                PostCard(
//                    feedPost = feedPost,
//                    onStatisticClickListener = { statisticItem ->
//                        viewModel.updateCount(feedPost, statisticItem)
//                    }
//                )
//            }
//        }
//    }
}