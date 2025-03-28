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

/**
 * Unit tests for the Admin API, verifying authentication and statistics retrieval.
 */
class AdminApiTest {

    /**
     * Sets up the test environment for API calls.
     *
     * - Mocks application configuration.
     * - Creates a test HTTP client.
     * - Initializes the application with a test client and a pre-populated fact repository.
     *
     * @return Configured [HttpClient] for making test API requests.
     */
    private fun ApplicationTestBuilder.setup(): HttpClient {
        createConfigMock()
        val client = createTestClient()
        application {
            module(client, createTestFactRepository())
        }
        return client
    }


    /**
     * Tests unauthorized access to the Admin Statistics endpoint.
     *
     * Expected behavior:
     * - A request without an authorization token should return `401 Unauthorized`.
     */
    @Test
    fun nonAuthorized() = testApplication {
        val client = setup()
        client.get(Admin.Statistics()).apply {
            assertEquals(HttpStatusCode.Unauthorized, status)
        }
    }

    /**
     * Tests the retrieval and update of access statistics in the Admin API.
     *
     * Steps:
     * 1. Retrieves the initial statistics with a valid admin token.
     * 2. Accesses a specific fact endpoint.
     * 3. Fetches the statistics again to verify that the access count has increased correctly.
     *
     * Expected behavior:
     * - The initial statistics should be retrieved successfully.
     * - Accessing a fact should increase its access count.
     * - The updated statistics should reflect the new access count.
     */
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