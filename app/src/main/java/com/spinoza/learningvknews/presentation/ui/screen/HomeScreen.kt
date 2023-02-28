package com.spinoza.learningvknews.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spinoza.learningvknews.domain.PostComment
import com.spinoza.learningvknews.presentation.ui.PostCard
import com.spinoza.learningvknews.presentation.ui.screen.CommentsScreen
import com.spinoza.learningvknews.presentation.viewmodel.MainViewModel

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    paddingValues: PaddingValues
) {
    val feedPosts = viewModel.feedPosts.observeAsState(listOf())

    if(feedPosts.value.isNotEmpty()) {
        val comments= mutableListOf<PostComment>().apply {
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