package com.challenge.resources

import io.ktor.resources.*

@Resource("/facts")
class Facts(
    val offset: Int = 0,
    val limit: Int = 10,
) {
    @Resource("/{shortId}")
    class ShortId(val parent: Facts = Facts(), val shortId: String)
}


