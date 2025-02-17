package com.example.listfilterandsortapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge  // Import the enableEdgeToEdge function
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Density
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.listfilterandsortapp.ui.theme.ListFilterAndSortAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListFilterAndSortAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FilterAndSortListWithLazyColumn(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun FilterAndSortListWithLazyColumn(modifier: Modifier = Modifier) {
    var searchQuery by remember { mutableStateOf("") }
    var isSorted by remember { mutableStateOf(false) }

    // Large in-memory list of fruits
    // List of fruits
//    val fruits = listOf(
//        "Apple", "Banana", "Orange", "Mango", "Pineapple", "Grapes", "Watermelon",
//        "Strawberry", "Blueberry", "Kiwi", "Peach", "Plum", "Apricot", "Cantaloupe",
//        "Papaya", "Pomegranate", "Cherry", "Lemon", "Lime", "Guava", "Lychee", "Dragon fruit",
//        "Passion Fruit", "Raspberry", "Blackberry", "Coconut", "Fig"
//    )

    val fruits = listOf(
        "Apple", "Banana", "Orange", "Mango", "Pineapple", "Grapes", "Watermelon",
        "Strawberry", "Blueberry", "Kiwi", "Peach", "Plum", "Apricot", "Cantaloupe",
        "Papaya", "Pomegranate", "Cherry", "Lemon", "Lime", "Guava", "Lychee", "Dragon fruit",
        "Passion Fruit", "Raspberry", "Blackberry", "Coconut", "Fig",
        "Cranberry", "Gooseberry", "Mulberry", "Tangerine", "Nectarine", "Persimmon",
        "Starfruit", "Boysenberry", "Currant", "Elderberry", "Jackfruit", "Longan",
        "Marionberry", "Sapodilla", "Soursop", "Ugli fruit", "Durian", "Breadfruit",
        "Jujube", "Salak (Snake Fruit)", "Chico", "Loquat", "Tamarind", "Horned Melon"
    )



    // Derived state: Filter the list based on search query
    val filteredFruits by remember {
        derivedStateOf {
            if (searchQuery.isBlank()) {
                fruits
            } else {
                fruits.filter { it.contains(searchQuery, ignoreCase = true) }
            }
        }
    }

    // Derived state: Sort the list alphabetically if sorting is enabled
    val sortedFruits by remember {
        derivedStateOf {
            if (isSorted) {
                filteredFruits.sorted()
            } else {
                filteredFruits
            }
        }
    }

    Column(modifier = modifier.padding(16.dp)) {
        // Search bar for filtering
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search for a fruit") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Toggle sort button
        Button(onClick = { isSorted = !isSorted }, modifier = Modifier.padding(vertical = 8.dp)) {
            Text(if (isSorted) "Disable Sorting" else "Enable Sorting")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display the filtered and sorted list using LazyColumn
        LazyColumn {
            items(sortedFruits) { fruit ->
                Text(text = fruit, modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ListFilterAndSortAppTheme {
        FilterAndSortListWithLazyColumn()
    }
}