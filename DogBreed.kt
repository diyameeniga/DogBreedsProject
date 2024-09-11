package com.example.scrollablelistapp

import kotlinx.serialization.Serializable

@Serializable
/*data class DogBreed(
    val id: String, // RWRONG
    val name: String,
)*/


data class DogBreed(
    val id: String,
    val type: String,
    val attributes: DogBreedAttributes
)

data class DogBreedAttributes(
    val name: String,
    val description: String? = null,
    val hypoallergenic: Boolean? = null
)

