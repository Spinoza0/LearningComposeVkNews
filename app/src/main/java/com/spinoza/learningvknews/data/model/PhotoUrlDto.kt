package com.spinoza.learningvknews.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoUrlDto(
    @SerialName("url") val url: String?,
)