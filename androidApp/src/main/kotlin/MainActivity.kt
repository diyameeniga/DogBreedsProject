package com.example.androidApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.shared.ApiService
import com.example.shared.DogRepository
import com.example.shared.DogBreed
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    private val apiService = ApiService()
    private val dogRepository = DogRepository(apiService)
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var breeds by remember { mutableStateOf(emptyList<DogBreed>()) }

            // Fetch the dog breeds when the composable is created
            LaunchedEffect(Unit) {
                coroutineScope.launch {
                    breeds = dogRepository.getDogBreeds()
                }
            }

            // Display the list of dog breeds
            DogBreedList(breeds)
        }
    }
}

@Composable
fun DogBreedList(dogBreeds: List<DogBreed>) {
    LazyColumn {
        items(dogBreeds.size) { index ->
            BasicText(dogBreeds[index].name)
        }
    }
}

@Preview
@Composable
fun PreviewDogBreedList() {
    DogBreedList(listOf(DogBreed("Labrador"), DogBreed("Poodle"), DogBreed("Bulldog")))
}