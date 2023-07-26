package com.spinoza.learningvknews.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GroupDto(
    @SerialName("id")  val id: Long,
    @SerialName("name")  val name: String,
    @SerialName("photo_200")  val imageUrl: String,
)