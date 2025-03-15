package com.challenge.model

import io.ktor.resources.*
import kotlinx.serialization.Serializable


@Serializable
data class Fact(
    val id: String,
    val text: String,
    val source: String,
    val sourceUrl: String,
    val language: String,
    val permalink: String,
)
@Resource("/api/v2/facts")
class Facts {
    @Resource("random")
    class RandomFact(val parent: Facts = Facts())
}
