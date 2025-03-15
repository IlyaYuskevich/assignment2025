package com.challenge.storage

import com.challenge.model.Fact
import io.ktor.server.plugins.*
import io.ktor.util.collections.*

object FactStorage {
    private val facts = ConcurrentMap<String, Fact>()
    private val accessStats = ConcurrentMap<String, Int>()

    fun addFact(fact: Fact) {
        this.facts[fact.getShortId()] = fact
    }

    fun getFact(shortId: String): Fact {
        return this.facts[shortId]?.also {
            this.accessStats.merge(shortId, 1) { oldValue, _ -> oldValue + 1 }
        } ?: throw NotFoundException()
    }

    fun getFacts(offset: Int, limit: Int): List<Fact> {
        return this.facts.values.drop(offset).take(limit)
    }
}