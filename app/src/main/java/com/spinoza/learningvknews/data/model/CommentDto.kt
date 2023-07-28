package com.spinoza.learningvknews.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentDto(
    @SerialName("id") val id: Long,
    @SerialName("from_id") val authorId: Long,
    @SerialName("date") val date: Long,
    @SerialName("text") val text: String,
)