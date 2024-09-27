package org.grating.recolldroid.data

class RecollResultsSet {
}

interface RecollResultsRepository {
    suspend fun executeQuery(query: String): RecollResultsSet
}
