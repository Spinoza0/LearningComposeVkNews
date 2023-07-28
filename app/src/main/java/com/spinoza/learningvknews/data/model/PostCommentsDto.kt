package com.spinoza.learningvknews.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostCommentsDto(
    @SerialName("items") val comments: List<CommentDto>,
    @SerialName("profiles") val authors: List<AuthorOfCommentDto>,
)