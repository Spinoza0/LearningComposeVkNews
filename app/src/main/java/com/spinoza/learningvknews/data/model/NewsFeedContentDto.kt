package com.spinoza.learningvknews.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsFeedContentDto(
    @SerialName("items") val posts: List<PostDto>,
    @SerialName("groups") val groups: List<GroupDto>,
)