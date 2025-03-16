package com.challenge.api

import io.ktor.resources.*

@Resource("/api/v2/facts")
class UselessFactsExternal {
    @Resource("random")
    class Random(val parent: UselessFactsExternal = UselessFactsExternal())
}
