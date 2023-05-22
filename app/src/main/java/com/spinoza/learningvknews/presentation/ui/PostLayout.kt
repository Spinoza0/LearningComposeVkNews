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
import androidx.compose.ui.unit.dp
import com.spinoza.learningvknews.R
import com.spinoza.learningvknews.domain.FeedPost
import com.spinoza.learningvknews.domain.StatisticItem
import com.spinoza.learningvknews.domain.StatisticType

private const val WEIGHT = 1f

@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    feedPost: FeedPost,
    onStatisticClickListener: (StatisticItem) -> Unit,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(size = SIZE_SMALL.dp),
        elevation = CARD_ELEVATION.dp
    ) {
        Column(
            modifier = Modifier.padding(SIZE_SMALL.dp)
        ) {
            PostHeader(feedPost)
            SpacerHeightSmall()

            PostBody(feedPost)
            SpacerHeightSmall()

            PostFooter(
                feedPost = feedPost,
                onStatisticClickListener = onStatisticClickListener
            )
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
        SpacerWidthSmall()
        Column(
            modifier = Modifier.weight(WEIGHT)
        ) {
            Text(
                text = feedPost.communityName,
                color = MaterialTheme.colors.onPrimary
            )
            SpacerWidthMini()
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
        SpacerHeightSmall()
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
    feedPost: FeedPost,
    onStatisticClickListener: (StatisticItem) -> Unit,
) {
    val statistics = feedPost.statistics
    Row {
        Row(
            modifier = Modifier.weight(WEIGHT)
        ) {
            val viewsItem = statistics.getItemByType(StatisticType.VIEWS)
            IconWithText(
                iconResId = R.drawable.ic_views_count,
                iconDescriptionResId = R.string.views_count,
                text = viewsItem.count.toString(),
                onItemClickListener = { onStatisticClickListener(viewsItem) }
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
                onItemClickListener = { onStatisticClickListener(sharesItem) }
            )

            val commentsItem = statistics.getItemByType(StatisticType.COMMENTS)
            IconWithText(
                iconResId = R.drawable.ic_comment,
                iconDescriptionResId = R.string.comments_count,
                text = commentsItem.count.toString(),
                onItemClickListener = { onStatisticClickListener(commentsItem) }
            )

            val likesItem = statistics.getItemByType(StatisticType.LIKES)
            IconWithText(
                iconResId = R.drawable.ic_like,
                iconDescriptionResId = R.string.likes_count,
                text = likesItem.count.toString(),
                onItemClickListener = { onStatisticClickListener(likesItem) }
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
            modifier = Modifier.size(ICON_SIZE.dp),
            painter = painterResource(id = iconResId),
            contentDescription = stringResource(iconDescriptionResId),
            tint = MaterialTheme.colors.onSecondary
        )
        SpacerWidthMini()
        Text(
            text = text,
            color = MaterialTheme.colors.onSecondary
        )
    }
}

@Composable
private fun SpacerWidthSmall() {
    Spacer(modifier = Modifier.width(SIZE_SMALL.dp))
}

@Composable
private fun SpacerWidthMini() {
    Spacer(modifier = Modifier.width(SIZE_MINI.dp))
}

@Composable
private fun SpacerHeightSmall() {
    Spacer(modifier = Modifier.height(SIZE_SMALL.dp))
}
