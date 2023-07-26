package com.spinoza.learningvknews.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class StatisticItem(
    val type: StatisticType,
    val count: Int = 0,
)