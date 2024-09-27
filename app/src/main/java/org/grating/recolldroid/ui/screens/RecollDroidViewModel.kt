package org.grating.recolldroid.ui.screens

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.grating.recolldroid.ui.model.RecollDroidUiState

class RecollDroidViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RecollDroidUiState())
    val uiState: StateFlow<RecollDroidUiState> = _uiState.asStateFlow()

    fun updateCurrentQuery(query: String) {
        _uiState.update { currentState ->
            currentState.copy(
                currentQuery = query
            )
        }
    }

    fun executeCurrentQuery() {
        //
    }
}