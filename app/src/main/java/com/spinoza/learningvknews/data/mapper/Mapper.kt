package com.spinoza.learningvknews.data.mapper

import com.spinoza.learningvknews.data.model.NewsFeedResponseDto
import com.spinoza.learningvknews.domain.model.FeedPost
import com.spinoza.learningvknews.domain.model.StatisticItem
import com.spinoza.learningvknews.domain.model.StatisticType
import kotlin.math.absoluteValue

fun NewsFeedResponseDto.toFeedPosts(): List<FeedPost> {
    val result = mutableListOf<FeedPost>()
    newsFeedContent.posts.forEach { post ->
        newsFeedContent.groups.find { it.id == post.communityId.absoluteValue }?.let { group ->
            val firstAttachment = post.attachments?.firstOrNull()
            val bestQualityImageUrl = firstAttachment?.photo?.photoUrls?.lastOrNull()?.url
            val feedPost = FeedPost(
                id = post.id.toString(),
                communityName = group.name,
                publicationDate = post.date.toString(),
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