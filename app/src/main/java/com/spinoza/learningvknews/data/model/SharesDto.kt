package com.spinoza.learningvknews.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SharesDto(
    @SerialName("count") val count: Int,
)