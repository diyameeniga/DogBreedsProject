package com.example.scrollablelistapp

import kotlinx.serialization.Serializable

@Serializable
data class DogBreedsResponse(
    val message: Map<String, List<String>>,
    val status: String
)
