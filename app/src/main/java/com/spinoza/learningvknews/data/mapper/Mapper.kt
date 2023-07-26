package com.spinoza.learningvknews.data.mapper

import com.spinoza.learningvknews.data.model.NewsFeedResponseDto
import com.spinoza.learningvknews.domain.model.FeedPost
import com.spinoza.learningvknews.domain.model.StatisticItem
import com.spinoza.learningvknews.domain.model.StatisticType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.absoluteValue

fun NewsFeedResponseDto.toFeedPosts(): List<FeedPost> {
    val result = mutableListOf<FeedPost>()
    val simpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    simpleDateFormat.timeZone = TimeZone.getDefault()
    newsFeedContent.posts.forEach { post ->
        newsFeedContent.groups.find { it.id == post.communityId.absoluteValue }?.let { group ->
            val firstAttachment = post.attachments?.firstOrNull()
            val bestQualityImageUrl = firstAttachment?.photo?.photoUrls?.lastOrNull()?.url
            val feedPost = FeedPost(
                id = post.id.toString(),
                communityName = group.name,
                publicationDate = post.date.timeStampToDate(simpleDateFormat),
                communityImageUrl = group.imageUrl,
                contentText = post.text,
                contentImageUrl = bestQualityImageUrl,
                statistics = listOf(
                    StatisticItem(StatisticType.LIKES, post.likes.count),
                    StatisticItem(StatisticType.VIEWS, post.views.count),
                    StatisticItem(StatisticType.SHARES, post.shares.count),
                    StatisticItem(StatisticType.COMMENTS, post.comments.count),
                )
            )
            result.add(feedPost)
        }
    }
    return result
}

private fun Long.timeStampToDate(simpleDateFormat: SimpleDateFormat): String {
    val date = Date(this * MILLIS_IN_SECOND)
    return simpleDateFormat.format(date)
}

private const val DATE_FORMAT = "d MMMM yyyy, hh:mm"
private const val MILLIS_IN_SECOND = 1000