package com.challenge

import com.challenge.mocks.configureExternalServices
import com.challenge.mocks.createConfigMock
import com.challenge.mocks.createTestClient
import com.challenge.model.Fact
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

class FactsApiTest {
    private fun ApplicationTestBuilder.setup(): HttpClient {
        createConfigMock()
        val client = createTestClient()
        configureExternalServices()
        application {
            module(client)
        }
        return client
    }
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
            val facts = body<List<Fact>>()
            assertEquals(facts.size, 2)
        }
        client.get(Facts.ShortId(shortId = firstFact.shortId)).apply {
            assertEquals(HttpStatusCode.OK, status)
            assertDoesNotThrow { body<Fact>() }
        }
    }
}
