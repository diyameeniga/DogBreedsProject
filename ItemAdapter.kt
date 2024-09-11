package com.example.scrollablelistapp // Declaring the package for organizing the code

import android.graphics.Typeface // Importing Typeface for font styling
import android.text.SpannableStringBuilder // Importing SpannableStringBuilder for text styling
import android.text.Spanned // Importing Spanned for defining text ranges
import android.text.style.StyleSpan // Importing StyleSpan for applying text styles
import android.view.LayoutInflater // Importing LayoutInflater for inflating layout XML
import android.view.View // Importing View for handling the visual elements
import android.view.ViewGroup // Importing ViewGroup for handling the parent view
import android.widget.TextView // Importing TextView for displaying text
import androidx.recyclerview.widget.RecyclerView // Importing RecyclerView for list management

// Defining the adapter class for the RecyclerView to display items
class ItemAdapter(private val items: MutableList<String>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    // Defining the ViewHolder class to hold item views
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.itemTextView) // Finding the TextView by its ID
    }

    // Creating a new ViewHolder to display the item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false) // Inflating the item layout
        return ItemViewHolder(view) // Returning the created ViewHolder
    }

    // Binding data to the ViewHolder for each item
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position] // Getting the current item; binding appropriate data to designated row

        val lines = item.split("\n") // Splitting the item text by newline to separate breed name and details
        val breedName = lines.firstOrNull() ?: "" // Getting the breed name as the first line
        val details = lines.drop(1).joinToString("\n") // Joining the remaining lines as details

        // Using SpannableStringBuilder to create styled text
        val styledText = SpannableStringBuilder()
            .append(
                breedName,
                StyleSpan(Typeface.BOLD), // Applying bold style to the breed name
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            .append("\n")
            .append(details) // Appending the details text

        holder.textView.text = styledText // Setting the styled text to the TextView
    }

    // Returning the total number of items in the list
    override fun getItemCount(): Int = items.size

    // Updating the list dynamically with new items
    fun updateItems(newItems: List<String>) {
        items.clear() // Clearing the existing items
        items.addAll(newItems) // Adding new items to the list
        notifyDataSetChanged() // Notifying adapter about the data changes
    }
}