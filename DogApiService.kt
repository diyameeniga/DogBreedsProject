package com.example.scrollablelistapp // Package declaration for organizing the code

import android.util.Log // Import for logging
import io.ktor.client.* // Import for HTTP client
import io.ktor.client.call.* // Import for handling HTTP client call
import io.ktor.client.request.* // Import for making HTTP requests
import io.ktor.client.engine.cio.* // Import for using CIO as the engine for HTTP client
import io.ktor.client.plugins.contentnegotiation.* // Import for content negotiation plugin
import io.ktor.client.plugins.logging.* // Import for logging plugin in Ktor client
import io.ktor.serialization.kotlinx.json.* // Import for JSON serialization
import kotlinx.serialization.Serializable // Import for making data classes serializable
import kotlinx.serialization.json.Json // Import for JSON processing

// Object declaration for the DogApiService
object DogApiService {
    // Create an HTTP client using the CIO engine
    private val client = HttpClient(CIO) {
        // Install Content Negotiation plugin for JSON parsing
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // Ignore unknown keys in JSON response
                isLenient = true // Allow non-strict JSON parsing
            })
        }
        // Install Logging plugin for logging HTTP request and response
        install(Logging) {
            level = LogLevel.BODY // Log the body of the requests and responses
        }
    }

    // Data class representing the structure of the API response
    @Serializable
    data class DogApiResponse(val data: List<DogBreed>) // List of DogBreed objects in the response

    // Function to fetch dog breeds from the API asynchronously
    suspend fun getDogBreeds(): List<String> {
        try {
            // Make an API call to fetch dog breeds from the v2 API
            val response: DogApiResponse = client.get("https://dogapi.dog/api/v2/breeds").body()

            // Log the fetched dog breed names
            Log.d("DogApiService", "Fetched Dog Breeds: ${response.data.map { it.attributes.name }}")

            // Extract breed details from the response and format them
            return response.data.map { breed ->
                "${breed.attributes.name}\n" + // Name of the breed
                        "Description: ${breed.attributes.description ?: "No description available."}\n" + // Description of the breed
                        "Hypoallergenic: ${breed.attributes.hypoallergenic?.let { if (it) "Yes" else "No" } ?: "Unknown"}" // Hypoallergenic status
            }
        } catch (e: Exception) {
            // Printing the stack trace in case of an exception
            e.printStackTrace()
            // Log error message
            Log.e("DogApiService", "Error fetching dog breeds: ${e.message}")

            // Handling extra exceptions
            when (e) {
                is io.ktor.client.plugins.HttpRequestTimeoutException -> { // Handle request timeout
                    Log.e("DogApiService", "Request timed out")
                }
                is io.ktor.client.plugins.ClientRequestException -> { // Handle client request error
                    Log.e("DogApiService", "Client request error: ${e.response.status.description}")
                }
                is io.ktor.client.plugins.ServerResponseException -> { // Handle server error
                    Log.e("DogApiService", "Server error: ${e.response.status.description}")
                }
                else -> { // Handle any unexpected errors
                    Log.e("DogApiService", "Unexpected error: ${e.localizedMessage}")
                }
            }

            return emptyList()
        }
    }
}
