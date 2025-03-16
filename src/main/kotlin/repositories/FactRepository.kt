package com.challenge.repositories

import com.challenge.resources.SortOrder
import com.challenge.model.Fact
import com.challenge.model.FactStats

interface FactRepository {
    fun addFact(fact: Fact)
    fun getFact(shortId: String): Fact
    fun getFacts(offset: Int, limit: Int): List<Fact>
    fun getStatistics(offset: Int, limit: Int, sort: SortOrder?): List<FactStats>
}