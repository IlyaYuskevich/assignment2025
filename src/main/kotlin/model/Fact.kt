package com.challenge.model

import com.challenge.AppConfig
import io.ktor.resources.*
import io.viascom.nanoid.NanoId
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class Fact(
    val id: String,
    val text: String,
    val source: String,
    val sourceUrl: String,
    val language: String,
    val permalink: String,

    @Transient
    private val shortId: String = NanoId.generate(7),

    val shortUrl: String = "${AppConfig.baseUrl}/facts/${shortId}"
) {
    fun getShortId(): String = shortId
}
@Resource("/api/v2/facts")
class FactsApi {
    @Resource("random")
    class RandomFact(val parent: FactsApi = FactsApi())
}
