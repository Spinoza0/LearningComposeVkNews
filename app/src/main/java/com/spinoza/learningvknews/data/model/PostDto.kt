package com.spinoza.learningvknews.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    @SerialName("id") val id: Long,
    @SerialName("source_id") val communityId: Long,
    @SerialName("text") val text: String,
    @SerialName("date") val date: Long,
    @SerialName("likes") val likes: LikesDto,
    @SerialName("comments") val comments: CommentsDto,
    @SerialName("views") val views: ViewsDto,
    @SerialName("reposts") val shares: SharesDto,
    @SerialName("attachments") val attachments: List<AttachmentDto>? = null,
)