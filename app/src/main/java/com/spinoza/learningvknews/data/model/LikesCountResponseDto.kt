package com.spinoza.learningvknews.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LikesCountResponseDto(
    @SerialName("response") val likes: LikesCountDto,
)