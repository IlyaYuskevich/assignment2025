package com.challenge.model

import kotlinx.serialization.Serializable

@Serializable
data class PaginatedResult<T>(
    val data: List<T>,
    val count: Int
)