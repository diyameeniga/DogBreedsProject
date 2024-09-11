package com.example.shared

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.client.plugins.logging.*
import kotlinx.serialization.Serializable

@Serializable
data class DogBreedResponse(
    val message: Map<String, List<String>>,
    val status: String
)

class ApiService {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
        install(Logging) {
            level = LogLevel.BODY
        }
    }

    suspend fun fetchDogBreeds(): List<DogBreed> {
        val response: HttpResponse = client.get("https://dog.ceo/api/breeds/list/all")
        val dogBreedResponse: DogBreedResponse = response.body()

        return dogBreedResponse.message.keys.map { DogBreed(it) }
    }
}
