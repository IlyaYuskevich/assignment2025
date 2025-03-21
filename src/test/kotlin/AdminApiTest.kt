package com.challenge

import com.challenge.mocks.createConfigMock
import com.challenge.mocks.createTestClient
import com.challenge.mocks.createTestFactRepository
import com.challenge.model.FactStats
import com.challenge.model.PaginatedResult
import com.challenge.resources.Admin
import com.challenge.resources.Facts
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.assertEquals


class AdminApiTest {
    private fun ApplicationTestBuilder.setup(): HttpClient {
        createConfigMock()
        val client = createTestClient()
        application {
            module(client, createTestFactRepository())
        }
        return client
    }

    @Test
    fun nonAuthorized() = testApplication {
        val client = setup()
        client.get(Admin.Statistics()).apply {
            assertEquals(HttpStatusCode.Unauthorized, status)
        }
    }

    @Test
    fun testStatistics() = testApplication {
        val client = setup()
        val stats = client.get(Admin.Statistics()) {
            header(HttpHeaders.Authorization, "Bearer admin-access-token")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
        }.body<PaginatedResult<FactStats>>()
        client.get(Facts.ShortId(shortId = stats.data[0].fact.shortId)).apply {
            assertEquals(HttpStatusCode.OK, status)
        }
        client.get(Admin.Statistics()) {
            header(HttpHeaders.Authorization, "Bearer admin-access-token")
        }.apply {
            val updatedStats = body<PaginatedResult<FactStats>>()
            assertEquals(stats.data[0].accessCount + 1, updatedStats.data[0].accessCount)
            assertEquals(stats.data[1].accessCount, updatedStats.data[1].accessCount)
        }
    }
}