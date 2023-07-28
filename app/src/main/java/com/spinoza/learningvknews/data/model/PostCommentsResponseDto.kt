package com.spinoza.learningvknews.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostCommentsResponseDto(
    @SerialName("response") val content: PostCommentsDto,
)