package com.example.shared

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogRepository(private val apiService: ApiService) {
    suspend fun getDogBreeds(): List<DogBreed> {
        return withContext(Dispatchers.Default) {
            apiService.fetchDogBreeds()
        }
    }
}
