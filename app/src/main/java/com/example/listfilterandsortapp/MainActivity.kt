package com.example.listfilterandsortapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge  // Import the enableEdgeToEdge function
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.listfilterandsortapp.ui.theme.ListFilterAndSortAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge content
        enableEdgeToEdge()

        setContent {
            ListFilterAndSortAppTheme {
                // Scaffold layout to structure the app layout
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FilterAndSortListApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun FilterAndSortListApp(modifier: Modifier = Modifier) {
    var searchQuery by remember { mutableStateOf("") }
    var isSorted by remember { mutableStateOf(false) }

    // In-memory list of fruits
    val fruits = listOf("Apple", "Banana", "Orange", "Mango", "Pineapple", "Grapes", "Watermelon")

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

        // Display the list of filtered (and possibly sorted) fruits
        sortedFruits.forEach { fruit ->
            Text(text = fruit, modifier = Modifier.padding(vertical = 4.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ListFilterAndSortAppTheme {
        FilterAndSortListApp()
    }
}