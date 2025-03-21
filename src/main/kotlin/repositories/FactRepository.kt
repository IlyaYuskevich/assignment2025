package com.challenge.repositories

import com.challenge.resources.SortOrder
import com.challenge.model.Fact
import com.challenge.model.FactStats
import com.challenge.model.PaginatedResult
import java.awt.print.Pageable

interface FactRepository {
    fun addFact(fact: Fact)
    fun getFact(shortId: String): Fact
    fun getFacts(offset: Int, limit: Int): PaginatedResult<Fact>
    fun getStatistics(offset: Int, limit: Int, sort: SortOrder?): PaginatedResult<FactStats>
}