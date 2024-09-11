package com.example.scrollablelistapp // Declaring package for code organization

import android.os.Bundle // Importing Bundle for passing data between Android components
import android.util.Log // Importing Log for logging debug messages
import androidx.appcompat.app.AppCompatActivity // Importing AppCompatActivity for activity behavior
import androidx.recyclerview.widget.LinearLayoutManager // Importing LinearLayoutManager for arranging items in a RecyclerView
import androidx.recyclerview.widget.RecyclerView // Importing RecyclerView for list management
import kotlinx.coroutines.CoroutineScope // Importing CoroutineScope for coroutine management
import kotlinx.coroutines.Dispatchers // Importing Dispatchers for defining coroutine thread usage
import kotlinx.coroutines.launch // Importing launch for launching new coroutines
import kotlinx.coroutines.withContext // Importing withContext for switching coroutine context

// Defining MainActivity class, which is the entry point of the application
class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ItemAdapter // Declaring adapter for RecyclerView

    // Overriding onCreate function, called when activity is first created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Calling superclass onCreate to perform initial setup
        setContentView(R.layout.activity_main) // Setting the content view to activity_main layout

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView) // Finding RecyclerView by its ID
        recyclerView.layoutManager = LinearLayoutManager(this) // Setting LinearLayoutManager to arrange items vertically

        val items = mutableListOf<String>() // Creating a mutable list to hold items
        adapter = ItemAdapter(items) // Initializing adapter with empty item list
        recyclerView.adapter = adapter // Attaching adapter to RecyclerView

        fetchDogBreeds() // Fetching dog breeds from API
    }

    // Defining function to fetch dog breeds from API
    private fun fetchDogBreeds() {
        CoroutineScope(Dispatchers.IO).launch { // Launching a coroutine in the IO dispatcher
            try {
                val dogBreeds = DogApiService.getDogBreeds() // Fetching list of dog breeds from API

                Log.d("MainActivity", "Fetched Dog Breeds: $dogBreeds") // Logging fetched data to verify response

                withContext(Dispatchers.Main) { // Switching to Main thread for UI updates
                    adapter.updateItems(dogBreeds) // Updating RecyclerView with fetched breeds
                }
            } catch (e: Exception) {
                e.printStackTrace() // Printing stack trace in case of an exception
                Log.e("MainActivity", "Error fetching dog breeds: ${e.message}") // Logging error message
            }
        }
    }
}