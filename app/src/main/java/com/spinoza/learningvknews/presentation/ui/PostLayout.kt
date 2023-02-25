package com.spinoza.learningvknews.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spinoza.learningvknews.R
import com.spinoza.learningvknews.domain.FeedPost
import com.spinoza.learningvknews.domain.StatisticItem
import com.spinoza.learningvknews.domain.StatisticType
import com.spinoza.learningvknews.presentation.ui.theme.LearningVkNewsTheme

private const val WEIGHT = 1f

@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    feedPost: FeedPost,
    onStatisticItemClickListener: (StatisticItem) -> Unit,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(size = 8.dp),
        elevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            PostHeader(feedPost)
            SpacerHeight8dp()

            PostBody(feedPost)
            SpacerHeight8dp()

            PostFooter(feedPost.statistics, onStatisticItemClickListener)
        }
    }
}

@Composable
private fun PostHeader(feedPost: FeedPost) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            painter = painterResource(id = feedPost.avatarResId),
            contentDescription = stringResource(R.string.image_author)
        )
        SpacerWidth8dp()
        Column(
            modifier = Modifier.weight(WEIGHT)
        ) {
            Text(
                text = feedPost.communityName,
                color = MaterialTheme.colors.onPrimary
            )
            SpacerWidth4dp()
            Text(
                text = feedPost.publicationDate,
                color = MaterialTheme.colors.onSecondary
            )
        }
        Icon(
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = stringResource(R.string.menu),
            tint = MaterialTheme.colors.onSecondary
        )
    }
}

@Composable
private fun PostBody(feedPost: FeedPost) {
    Column {
        Text(text = feedPost.contentText)
        SpacerHeight8dp()
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            painter = painterResource(id = feedPost.contentImageResId),
            contentDescription = stringResource(R.string.image_content),
            contentScale = ContentScale.FillWidth
        )
    }
}

@Composable
private fun PostFooter(
    statistics: List<StatisticItem>,
    onItemClickListener: (StatisticItem) -> Unit,
) {
    Row {
        Row(
            modifier = Modifier.weight(WEIGHT)
        ) {
            val viewsItem = statistics.getItemByType(StatisticType.VIEWS)
            IconWithText(
                iconResId = R.drawable.ic_views_count,
                iconDescriptionResId = R.string.views_count,
                text = viewsItem.count.toString(),
                onItemClickListener = { onItemClickListener(viewsItem) }
            )
        }
        Row(
            modifier = Modifier.weight(WEIGHT),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val sharesItem = statistics.getItemByType(StatisticType.SHARES)
            IconWithText(
                iconResId = R.drawable.ic_share,
                iconDescriptionResId = R.string.shares_count,
                text = sharesItem.count.toString(),
                onItemClickListener = { onItemClickListener(sharesItem) }
            )

            val commentsItem = statistics.getItemByType(StatisticType.COMMENTS)
            IconWithText(
                iconResId = R.drawable.ic_comment,
                iconDescriptionResId = R.string.comments_count,
                text = commentsItem.count.toString(),
                onItemClickListener = { onItemClickListener(commentsItem) }
            )

            val likesItem = statistics.getItemByType(StatisticType.LIKES)
            IconWithText(
                iconResId = R.drawable.ic_like,
                iconDescriptionResId = R.string.likes_count,
                text = likesItem.count.toString(),
                onItemClickListener = { onItemClickListener(likesItem) }
            )
        }
    }
}

private fun List<StatisticItem>.getItemByType(type: StatisticType): StatisticItem =
    this.find { it.type == type } ?: throw RuntimeException()


@Composable
private fun IconWithText(
    iconResId: Int,
    iconDescriptionResId: Int,
    text: String,
    onItemClickListener: () -> Unit,
) {
    Row(
        modifier = Modifier.clickable { onItemClickListener() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(25.dp),
            painter = painterResource(id = iconResId),
            contentDescription = stringResource(iconDescriptionResId),
            tint = MaterialTheme.colors.onSecondary
        )
        SpacerWidth4dp()
        Text(
            text = text,
            color = MaterialTheme.colors.onSecondary
        )
    }
}

@Composable
private fun SpacerWidth8dp() {
    Spacer(modifier = Modifier.width(8.dp))
}

@Composable
private fun SpacerWidth4dp() {
    Spacer(modifier = Modifier.width(4.dp))
}

@Composable
private fun SpacerHeight8dp() {
    Spacer(modifier = Modifier.height(8.dp))
}

@Preview
@Composable
private fun PreviewLight() {
    LearningVkNewsTheme(darkTheme = false) {
        PostCard(feedPost = FeedPost(), onStatisticItemClickListener = {})
    }
}

@Preview
@Composable
private fun PreviewDark() {
    LearningVkNewsTheme(darkTheme = true) {
        PostCard(feedPost = FeedPost(), onStatisticItemClickListener = {})
    }
}