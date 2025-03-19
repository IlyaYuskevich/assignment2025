package com.challenge.mocks

import com.challenge.model.Fact
import com.challenge.repositories.FactRepository
import com.challenge.repositories.InMemoryFactRepository

internal val randomFacts = listOf(
    Fact("91276255ba07e327846576a723f9046a", "Van Gogh only sold one painting when he was alive.", "djtech.net", "http://www.djtech.net/humor/useless_facts.htm", "en", "https://uselessfacts.jsph.pl/api/v2/facts/91276255ba07e327846576a723f9046a"),
    Fact("29c04092542e313890d1ea0195aba86d", "The longest recorded flight of a chicken is thirteen seconds.", "djtech.net", "http://www.djtech.net/humor/useless_facts.htm", "en", "https://uselessfacts.jsph.pl/api/v2/facts/29c04092542e313890d1ea0195aba86d"),
    Fact("a33fba0c1d1dc15d6d1aebb8e167957a", "Average life span of a major league baseball: 7 pitches.", "djtech.net", "http://www.djtech.net/humor/useless_facts.htm", "en", "https://uselessfacts.jsph.pl/api/v2/facts/a33fba0c1d1dc15d6d1aebb8e167957a")
)
fun getRandomFactFactory(): () -> Fact {
    var i = 0
    return { randomFacts[i++ % randomFacts.size] }
}

fun createTestFactRepository(): FactRepository {
    val testFactRepository = InMemoryFactRepository()
    testFactRepository.addFact(randomFacts[0])
    testFactRepository.addFact(randomFacts[1])
    testFactRepository.addFact(randomFacts[2])
    repeat(5) { testFactRepository.getFact(randomFacts[0].shortId) }
    repeat(2) { testFactRepository.getFact(randomFacts[1].shortId) }
    repeat(7) { testFactRepository.getFact(randomFacts[2].shortId) }
    return testFactRepository
}


