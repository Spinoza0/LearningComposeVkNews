package com.spinoza.learningvknews.presentation.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.spinoza.learningvknews.domain.PostComment
import com.spinoza.learningvknews.presentation.viewmodel.MainViewModel

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
//            top = 16.dp,
//            start = 8.dp,
//            end = 8.dp,
//            bottom = 72.dp
//        ),
//        verticalArrangement = Arrangement.spacedBy(8.dp)
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
//                dismissThresholds = { FractionalThreshold(0.75f) },
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