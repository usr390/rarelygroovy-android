package com.example.rarelygroovy.network // Adjust package name if different

import com.example.rarelygroovy.data.model.Event // Import your Event data class
import retrofit2.Response
import retrofit2.http.GET

interface EnmEventApiService {

    @GET("api/enmEventsTrans") // The relative path of your API endpoint
    suspend fun getEvents(): Response<List<Event>>
}