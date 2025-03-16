package com.challenge.model

import com.challenge.AppConfig
import io.viascom.nanoid.NanoId
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Fact(
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
