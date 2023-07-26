package com.spinoza.learningvknews.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class FeedPost(
    val id: String,
    val communityName: String,
    val publicationDate: String,
    val communityImageUrl: String,
    val contentText: String,
    val contentImageUrl: String?,
    val statistics: List<StatisticItem>,
)