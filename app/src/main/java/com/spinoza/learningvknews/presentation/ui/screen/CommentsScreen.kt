package com.spinoza.learningvknews.presentation.ui.screen

import androidx.compose.foundation.Image
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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spinoza.learningvknews.R
import com.spinoza.learningvknews.domain.FeedPost
import com.spinoza.learningvknews.domain.PostComment
import com.spinoza.learningvknews.presentation.ui.COMMENT_BOTTOM_SIZE
import com.spinoza.learningvknews.presentation.ui.COMMENT_TEXT_FONT_SIZE
import com.spinoza.learningvknews.presentation.ui.COMMENT_TIME_FONT_SIZE
import com.spinoza.learningvknews.presentation.ui.COMMENT_TITLE_FONT_SIZE
import com.spinoza.learningvknews.presentation.ui.SIZE_LARGE
import com.spinoza.learningvknews.presentation.ui.SIZE_MEDIUM
import com.spinoza.learningvknews.presentation.ui.SIZE_MINI
import com.spinoza.learningvknews.presentation.ui.SIZE_SMALL
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
                top = SIZE_MEDIUM.dp,
                start = SIZE_SMALL.dp,
                end = SIZE_SMALL.dp,
                bottom = COMMENT_BOTTOM_SIZE.dp
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
            .padding(horizontal = SIZE_MEDIUM.dp, vertical = SIZE_MINI.dp)
    ) {
        Image(
            modifier = Modifier.size(SIZE_LARGE.dp),
            painter = painterResource(id = comment.authorAvatarId),
            contentDescription = stringResource(
                R.string.avatar
            )
        )
        Spacer(modifier = Modifier.width(SIZE_SMALL.dp))
        Column {
            CommentText("${comment.authorName}, commentId: ${comment.id}", COMMENT_TITLE_FONT_SIZE)
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

@Preview
@Composable
private fun PreviewComment() {
    LearningVkNewsTheme() {
        CommentItem(comment = PostComment(0))
    }
}