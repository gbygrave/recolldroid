package org.grating.recolldroid.ui.model

import org.grating.recolldroid.data.RecollResult

sealed interface QueryResultsUiState {
    data class Success(val results: List<RecollResult>) : QueryResultsUiState
    data object Error: QueryResultsUiState
    data object Loading: QueryResultsUiState
    data object Empty: QueryResultsUiState
}

data class RecollDroidUiState(
    val currentQuery: String = "",
    val queryResults: QueryResultsUiState = QueryResultsUiState.Empty
)