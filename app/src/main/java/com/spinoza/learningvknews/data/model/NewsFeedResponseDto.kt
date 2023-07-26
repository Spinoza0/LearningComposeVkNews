package com.spinoza.learningvknews.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsFeedResponseDto(
    @SerialName("response") val newsFeedContent: NewsFeedContentDto
)