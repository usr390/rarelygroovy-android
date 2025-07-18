package com.example.rarelygroovy.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private const val BASE_URL = "https://enm-project-production.up.railway.app/"

    // Create a logging interceptor
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Log request and response bodies
    }

    // Create an OkHttpClient and add the logging interceptor
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS) // Optional: Set connection timeout
        .readTimeout(30, TimeUnit.SECONDS)    // Optional: Set read timeout
        .writeTimeout(30, TimeUnit.SECONDS)   // Optional: Set write timeout
        .build()

    // Configure Gson to be lenient (optional, but can help with slightly malformed JSON)
    private val gson = GsonBuilder()
        .setLenient()
        .create()

    // Lazy initialize Retrofit instance
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // Set the custom OkHttpClient
            .addConverterFactory(GsonConverterFactory.create(gson)) // Use Gson for JSON parsing
            .build()
    }

    // Lazy initialize the API service
    val api: EnmEventApiService by lazy {
        retrofit.create(EnmEventApiService::class.java)
    }
}
