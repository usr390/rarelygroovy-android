package com.example.rarelygroovy.ui.eventlist // Adjust package name if different

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rarelygroovy.data.model.Event // Import your Event data class
import com.example.rarelygroovy.repository.EventRepository // Import your EventRepository
import com.example.rarelygroovy.repository.ResultWrapper // Import your ResultWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// This data class represents the state of your UI
data class EventListUiState(
    val isLoading: Boolean = false,
    val events: List<Event> = emptyList(),
    val errorMessage: String? = null
)

class EventListViewModel(
    // For simplicity now, we'll create the repository instance directly.
    // Later, you'd typically inject this using a dependency injection framework like Hilt.
    private val eventRepository: EventRepository = EventRepository()
) : ViewModel() {

    // Private MutableStateFlow that holds the current UI state
    private val _uiState = MutableStateFlow(EventListUiState(isLoading = true)) // Start with loading true

    // Publicly exposed StateFlow that the UI can observe
    val uiState: StateFlow<EventListUiState> = _uiState.asStateFlow()

    // init block is called when the ViewModel is created
    init {
        Log.d("EventListViewModel", "ViewModel initialized, fetching events...")
        fetchEvents() // Fetch events when the ViewModel is first created
    }

    // Function to fetch events from the repository
    fun fetchEvents() {
        // Launch a coroutine in the viewModelScope, so it's lifecycle-aware
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) } // Set loading state
            Log.d("EventListViewModel", "fetchEvents called, launching coroutine.")

            when (val result = eventRepository.getEvents()) {
                is ResultWrapper.Success -> {
                    Log.d("EventListViewModel", "Successfully fetched ${result.data.size} events.")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            events = result.data,
                            errorMessage = null
                        )
                    }
                }
                is ResultWrapper.Error -> { // Combined GenericError and NetworkError for simplicity here
                    Log.e("EventListViewModel", "Error fetching events: ${result.message}")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            events = emptyList(), // Clear previous events on error
                            errorMessage = "Error: ${result.message}"
                        )
                    }
                }
                // If you kept ResultWrapper.Loading, you might handle it or it might be redundant
                // if you set isLoading = true at the start of fetchEvents.
                // is ResultWrapper.Loading -> { /* Optionally handle */ }
                is ResultWrapper.Loading -> { // ADD THIS BRANCH
                    // You might already be setting isLoading = true at the start of fetchEvents.
                    // So, this branch might just be a no-op or log, unless you want
                    // to differentiate this specific loading state.
                    Log.d("EventListViewModel", "Events are loading (from Repository)...")
                    // _uiState.update { it.copy(isLoading = true) } // This is likely redundant
                }
            }
        }
    }
}
