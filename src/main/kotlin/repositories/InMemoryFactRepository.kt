package com.challenge.repositories

import com.challenge.resources.SortOrder
import com.challenge.model.Fact
import com.challenge.model.FactStats
import com.challenge.model.PaginatedResult
import io.ktor.server.plugins.*
import io.ktor.util.collections.*

/**
 * An in-memory implementation of [FactRepository].
 *
 * This repository stores facts in memory and tracks access statistics. Since it uses
 * [ConcurrentMap], it supports concurrent access, but data is **not** persisted.
 */
class InMemoryFactRepository : FactRepository {
    private val facts = ConcurrentMap<String, Fact>()
    private val accessStats = ConcurrentMap<String, Int>()

    /**
     * Stores a new fact in the repository.
     * If a fact with the same `shortId` already exists, it will be replaced.
     */
    override fun addFact(fact: Fact) {
        this.facts[fact.shortId] = fact
    }

    /**
     * Retrieves a fact by its `shortId`. Also increments its access count.
     *
     * @throws NotFoundException if the fact does not exist.
     */
    override fun getFact(shortId: String): Fact {
        return this.facts[shortId]?.also {
            this.accessStats.merge(shortId, 1) { oldValue, _ -> oldValue + 1 }
        } ?: throw NotFoundException()
    }

    /**
     * Returns a paginated list of facts.
     *
     * @param offset The number of items to skip.
     * @param limit The maximum number of facts to return.
     */
    override fun getFacts(offset: Int, limit: Int): PaginatedResult<Fact> {
        return PaginatedResult(this.facts.values.drop(offset).take(limit), this.facts.size)
    }


    /**
     * Retrieves fact access statistics, optionally sorted.
     *
     * @param offset The number of items to skip.
     * @param limit The maximum number of statistics to return.
     * @param sort The sorting order (ASC/DESC) based on access count.
     */
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