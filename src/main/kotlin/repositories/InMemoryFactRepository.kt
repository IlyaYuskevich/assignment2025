package com.challenge.storage

import com.challenge.resources.SortOrder
import com.challenge.model.Fact
import com.challenge.model.FactStats
import com.challenge.repositories.FactRepository
import io.ktor.server.plugins.*
import io.ktor.util.collections.*

object InMemoryFactRepository : FactRepository {
    private val facts = ConcurrentMap<String, Fact>()
    private val accessStats = ConcurrentMap<String, Int>()

    override fun addFact(fact: Fact) {
        this.facts[fact.getShortId()] = fact
    }

    override fun getFact(shortId: String): Fact {
        return this.facts[shortId]?.also {
            this.accessStats.merge(shortId, 1) { oldValue, _ -> oldValue + 1 }
        } ?: throw NotFoundException()
    }

    override fun getFacts(offset: Int, limit: Int): List<Fact> {
        return this.facts.values.drop(offset).take(limit)
    }

    override fun getStatistics(offset: Int, limit: Int, sort: SortOrder?): List<FactStats> {
        return this.facts.values
            .map { FactStats(it, this.accessStats[it.getShortId()] ?: 0) }
            .let { list ->
                when (sort) {
                    SortOrder.DESC -> list.sortedBy { it.accessCount }.reversed()
                    SortOrder.ASC -> list.sortedBy { it.accessCount }
                    else -> list
                }
            }
            .drop(offset).take(limit)
    }
}