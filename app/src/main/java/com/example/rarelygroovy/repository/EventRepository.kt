package com.example.rarelygroovy.repository // Adjust package name if different

import com.example.rarelygroovy.data.model.Event // Import your Event data class
import com.example.rarelygroovy.network.EnmEventApiService
import com.example.rarelygroovy.network.RetrofitInstance // Import your RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class EventRepository(
    private val apiService: EnmEventApiService = RetrofitInstance.api // Default instance, can be injected
) {

    /**
     * Fetches the list of events from the remote API.
     * This function should be called from a coroutine scope.
     *
     * @return ResultWrapper<List<Event>> A wrapper class that can hold either the success data or an error.
     */
    suspend fun getEvents(): ResultWrapper<List<Event>> {
        // Ensure network operations are performed on the IO dispatcher
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getEvents()
                if (response.isSuccessful) {
                    response.body()?.let { events ->
                        ResultWrapper.Success(events)
                    } ?: ResultWrapper.Error("API returned empty successful response.")
                } else {
                    ResultWrapper.Error("API Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                // More specific error handling can be added here (e.g., for IOException)
                ResultWrapper.Error("Network Error: ${e.message ?: "Unknown error"}")
            }
        }
    }
}

/**
 * A generic wrapper class to represent the result of an operation,
 * which can be either a success with data or an error with a message.
 */
sealed class ResultWrapper<out T> {
    data class Success<out T>(val data: T) : ResultWrapper<T>()
    data class Error(val message: String) : ResultWrapper<Nothing>()
    object Loading : ResultWrapper<Nothing>() // Optional: For representing loading state
}