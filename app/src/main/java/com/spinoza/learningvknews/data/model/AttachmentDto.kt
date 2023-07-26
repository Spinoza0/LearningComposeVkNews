package com.spinoza.learningvknews.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttachmentDto(
    @SerialName("photo") val photo: PhotoDto? = null,
)