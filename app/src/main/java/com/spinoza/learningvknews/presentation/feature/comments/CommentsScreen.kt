package com.spinoza.learningvknews.presentation.feature.comments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.spinoza.learningvknews.R
import com.spinoza.learningvknews.data.repository.NewsFeedRepositoryImpl
import com.spinoza.learningvknews.domain.model.FeedPost
import com.spinoza.learningvknews.domain.model.PostComment
import com.spinoza.learningvknews.presentation.feature.comments.viewmodel.CommentsViewModel
import com.spinoza.learningvknews.presentation.feature.comments.viewmodel.CommentsViewModelFactory
import com.spinoza.learningvknews.presentation.util.COMMENT_BOTTOM_SIZE
import com.spinoza.learningvknews.presentation.util.COMMENT_TEXT_FONT_SIZE
import com.spinoza.learningvknews.presentation.util.COMMENT_TIME_FONT_SIZE
import com.spinoza.learningvknews.presentation.util.COMMENT_TITLE_FONT_SIZE
import com.spinoza.learningvknews.presentation.util.SIZE_AVATAR
import com.spinoza.learningvknews.presentation.util.SIZE_MEDIUM
import com.spinoza.learningvknews.presentation.util.SIZE_MINI
import com.spinoza.learningvknews.presentation.util.SIZE_SMALL

@Composable
fun CommentsScreen(
    feedPost: FeedPost,
    onBackPressed: () -> Unit,
) {
    val viewModel: CommentsViewModel = viewModel(
        factory = CommentsViewModelFactory(feedPost, NewsFeedRepositoryImpl.getInstance())
    )
    val screenState =
        viewModel.screenState.observeAsState(CommentsScreenState.Initial).value
    if (screenState is CommentsScreenState.Comments) {
        ShowComments(screenState.comments, onBackPressed)
    }
}

@Composable
private fun ShowComments(
    comments: List<PostComment>,
    onBackPressed: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.comments)) },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(
                top = SIZE_MEDIUM.dp,
                start = SIZE_SMALL.dp,
                end = SIZE_SMALL.dp,
                bottom = COMMENT_BOTTOM_SIZE.dp
            ),
            verticalArrangement = Arrangement.spacedBy(SIZE_MEDIUM.dp)
        ) {
            items(items = comments, key = { it.id }) {
                CommentItem(comment = it)
            }
        }
    }
}

@Composable
private fun CommentItem(comment: PostComment) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SIZE_MEDIUM.dp, vertical = SIZE_MINI.dp)
    ) {
        AsyncImage(
            model = comment.authorAvatarUrl,
            modifier = Modifier
                .size(SIZE_AVATAR.dp)
                .clip(CircleShape),
            contentDescription = stringResource(R.string.avatar)
        )
        Spacer(modifier = Modifier.width(SIZE_SMALL.dp))
        Column {
            CommentText(comment.authorName, COMMENT_TITLE_FONT_SIZE)
            Spacer(modifier = Modifier.height(SIZE_MINI.dp))
            CommentText(comment.commentText, COMMENT_TEXT_FONT_SIZE)
            Spacer(modifier = Modifier.height(SIZE_MINI.dp))
            CommentText(
                comment.publicationDate,
                COMMENT_TIME_FONT_SIZE,
                color = MaterialTheme.colors.onSecondary
            )
        }
    }
}

@Composable
private fun CommentText(
    text: String,
    fontSize: Int,
    color: Color = MaterialTheme.colors.onPrimary,
) {
    Text(
        text = text,
        color = color,
        fontSize = fontSize.sp
    )
}