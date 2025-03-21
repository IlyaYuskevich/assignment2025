package com.challenge.repositories

import com.challenge.resources.SortOrder
import com.challenge.model.Fact
import com.challenge.model.FactStats
import com.challenge.model.PaginatedResult
import io.ktor.server.plugins.*
import io.ktor.util.collections.*

class InMemoryFactRepository : FactRepository {
    private val facts = ConcurrentMap<String, Fact>()
    private val accessStats = ConcurrentMap<String, Int>()

    override fun addFact(fact: Fact) {
        this.facts[fact.shortId] = fact
    }

    override fun getFact(shortId: String): Fact {
        return this.facts[shortId]?.also {
            this.accessStats.merge(shortId, 1) { oldValue, _ -> oldValue + 1 }
        } ?: throw NotFoundException()
    }

    override fun getFacts(offset: Int, limit: Int): PaginatedResult<Fact> {
        return PaginatedResult(this.facts.values.drop(offset).take(limit), this.facts.size)
    }

    override fun getStatistics(offset: Int, limit: Int, sort: SortOrder?): PaginatedResult<FactStats> {
        val allStats = this.facts.values
            .map { FactStats(it, this.accessStats[it.shortId] ?: 0) }
            .let { list ->
                when (sort) {
                    SortOrder.DESC -> list.sortedBy { it.accessCount }.reversed()
                    SortOrder.ASC -> list.sortedBy { it.accessCount }
                    else -> list
                }
            }
        return PaginatedResult(allStats.drop(offset).take(limit), allStats.size)
    }
}