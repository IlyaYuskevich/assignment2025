package com.challenge

import com.challenge.mocks.configureExternalServices
import com.challenge.mocks.createConfigMock
import com.challenge.mocks.createTestClient
import com.challenge.model.Fact
import com.challenge.model.PaginatedResult
import com.challenge.resources.Facts
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.plugins.resources.get
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Unit tests for the Facts API.
 *
 * This test suite verifies that:
 * - Facts can be created via POST requests.
 * - Facts are stored and retrieved correctly.
 * - Facts can be accessed by their short ID.
 */
class FactsApiTest {

    /**
     * Sets up the test environment for API calls.
     *
     * - Mocks application configuration.
     * - Creates a test HTTP client.
     * - Configures external service mocks.
     * - Initializes the application with the configured client.
     *
     * @return Configured [HttpClient] for making test API requests.
     */
    private fun ApplicationTestBuilder.setup(): HttpClient {
        createConfigMock()
        val client = createTestClient()
        configureExternalServices()
        application {
            module(client)
        }
        return client
    }

    /**
     * Tests fact creation, retrieval, and lookup by short ID.
     *
     * **Test Flow:**
     * 1. Creates a new fact via a POST request.
     * 2. Creates a second fact and verifies the response.
     * 3. Fetches all stored facts and checks if they match expectations.
     * 4. Retrieves a fact by its short ID and ensures it's correctly returned.
     */
    @Test
    fun testFacts() = testApplication {
        val client = setup()
        val firstFact = client.post(Facts()).run {
            assertEquals(HttpStatusCode.OK, status)
            val firstFact = body<Fact>()
            assertEquals("Van Gogh only sold one painting when he was alive.", firstFact.text)
            firstFact
        }
        client.post(Facts()).apply {
            assertEquals(HttpStatusCode.OK, status)
            val secondFact = body<Fact>()
            assertEquals("The longest recorded flight of a chicken is thirteen seconds.", secondFact.text)
        }
        client.get(Facts()).apply {
            assertEquals(HttpStatusCode.OK, status)
            val facts = body<PaginatedResult<Fact>>()
            assertEquals(facts.count, 2)
        }
        client.get(Facts.ShortId(shortId = firstFact.shortId)).apply {
            assertEquals(HttpStatusCode.OK, status)
            assertDoesNotThrow { body<Fact>() }
        }
    }
}
