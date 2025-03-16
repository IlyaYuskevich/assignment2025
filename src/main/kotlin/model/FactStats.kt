package com.challenge.model

import kotlinx.serialization.Serializable

@Serializable
data class FactStats(
    val fact: Fact,
    val accessCount: Int
)