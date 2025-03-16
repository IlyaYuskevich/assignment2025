package com.challenge.resources

import io.ktor.resources.*


@Resource("/admin")
class Admin {
    @Resource("/statistics")
    class Statistics(
        val parent: Admin = Admin(),
        val offset: Int = 0,
        val limit: Int = 10,
        val order: SortOrder? = null
    )
}