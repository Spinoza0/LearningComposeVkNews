package com.spinoza.learningvknews.presentation.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spinoza.learningvknews.R
import com.spinoza.learningvknews.domain.FeedPost
import com.spinoza.learningvknews.domain.PostComment
import com.spinoza.learningvknews.presentation.ui.theme.LearningVkNewsTheme

@Composable
fun CommentsScreen(
    feedPost: FeedPost,
    comments: List<PostComment>,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Comments for FeedPost Id: ${feedPost.id}") },
                navigationIcon = {
                    IconButton(onClick = { }) {
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
                top = 16.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 72.dp
            )
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
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = comment.authorAvatarId),
            contentDescription = stringResource(
                R.string.avatar
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            CommentText("${comment.authorName}, commentId: ${comment.id}")
            Spacer(modifier = Modifier.height(4.dp))
            CommentText(comment.commentText, 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            CommentText(comment.publicationDate, color = MaterialTheme.colors.onSecondary)
        }
    }
}

@Composable
private fun CommentText(
    text: String,
    fontSize: TextUnit = 12.sp,
    color: Color = MaterialTheme.colors.onPrimary,
) {
    Text(
        text = text,
        color = color,
        fontSize = fontSize
    )
}

@Preview
@Composable
private fun PreviewComment() {
    LearningVkNewsTheme() {
        CommentItem(comment = PostComment(0))
    }
}