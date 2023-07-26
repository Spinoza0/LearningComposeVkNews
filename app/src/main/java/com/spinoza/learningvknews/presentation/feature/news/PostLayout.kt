package com.spinoza.learningvknews.presentation.feature.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.spinoza.learningvknews.R
import com.spinoza.learningvknews.domain.model.FeedPost
import com.spinoza.learningvknews.domain.model.StatisticItem
import com.spinoza.learningvknews.domain.model.StatisticType
import com.spinoza.learningvknews.presentation.theme.DarkRed
import com.spinoza.learningvknews.presentation.util.CARD_ELEVATION
import com.spinoza.learningvknews.presentation.util.ICON_SIZE
import com.spinoza.learningvknews.presentation.util.SIZE_MINI
import com.spinoza.learningvknews.presentation.util.SIZE_SMALL

private const val WEIGHT = 1f

@Composable
fun PostCard(
    feedPost: FeedPost,
    onStatisticClickListener: (StatisticItem) -> Unit,
    onCommentsClickListener: (FeedPost) -> Unit,
    modifier: Modifier = Modifier,
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
                onStatisticClickListener = onStatisticClickListener,
                onCommentsClickListener = onCommentsClickListener
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
        AsyncImage(
            model = feedPost.communityImageUrl,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
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
        if (feedPost.contentImageUrl != null) {
            SpacerHeightSmall()
            AsyncImage(
                model = feedPost.contentImageUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentDescription = stringResource(R.string.image_content),
                contentScale = ContentScale.FillWidth
            )
        }
    }
}

@Composable
private fun PostFooter(
    feedPost: FeedPost,
    onStatisticClickListener: (StatisticItem) -> Unit,
    onCommentsClickListener: (FeedPost) -> Unit,
) {
    Row {
        Row(
            modifier = Modifier.weight(WEIGHT)
        ) {
            val viewsItem = feedPost.statistics.getItemByType(StatisticType.VIEWS)
            IconWithText(
                iconResId = R.drawable.ic_views_count,
                iconDescriptionResId = R.string.views_count,
                text = viewsItem.count.toFormattedStatisticCount(),
                onItemClickListener = { onStatisticClickListener(viewsItem) }
            )
        }
        Row(
            modifier = Modifier.weight(WEIGHT),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val sharesItem = feedPost.statistics.getItemByType(StatisticType.SHARES)
            IconWithText(
                iconResId = R.drawable.ic_share,
                iconDescriptionResId = R.string.shares_count,
                text = sharesItem.count.toFormattedStatisticCount(),
                onItemClickListener = { onStatisticClickListener(sharesItem) }
            )

            val commentsItem = feedPost.statistics.getItemByType(StatisticType.COMMENTS)
            IconWithText(
                iconResId = R.drawable.ic_comment,
                iconDescriptionResId = R.string.comments_count,
                text = commentsItem.count.toFormattedStatisticCount(),
                onItemClickListener = { onCommentsClickListener(feedPost) }
            )

            val likesItem = feedPost.statistics.getItemByType(StatisticType.LIKES)
            IconWithText(
                iconResId = if (feedPost.isFavourite) R.drawable.ic_like_set else R.drawable.ic_like,
                iconDescriptionResId = R.string.likes_count,
                text = likesItem.count.toFormattedStatisticCount(),
                iconTint = if (feedPost.isFavourite) DarkRed else MaterialTheme.colors.onSecondary,
                onItemClickListener = { onStatisticClickListener(likesItem) }
            )
        }
    }
}

private fun List<StatisticItem>.getItemByType(type: StatisticType): StatisticItem =
    this.find { it.type == type } ?: throw RuntimeException()

private fun Int.toFormattedStatisticCount(): String {
    return if (this > VISUAL_COUNTER_LIMIT) {
        String.format("%sK", this / COUNTER_DIVIDER)
    } else if (this > COUNTER_DIVIDER) {
        String.format("%.1fK", this.toFloat() / COUNTER_DIVIDER)
    } else {
        this.toString()
    }
}

@Composable
private fun IconWithText(
    iconResId: Int,
    iconDescriptionResId: Int,
    text: String,
    iconTint: Color = MaterialTheme.colors.onSecondary,
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
            tint = iconTint
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

private const val VISUAL_COUNTER_LIMIT = 100_000
private const val COUNTER_DIVIDER = 1000