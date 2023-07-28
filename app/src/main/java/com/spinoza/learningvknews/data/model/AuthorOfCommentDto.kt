package com.spinoza.learningvknews.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorOfCommentDto(
    @SerialName("id") val id: Long,
    @SerialName("photo_100") val avatarUrl: String,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
)