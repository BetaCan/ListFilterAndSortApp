package com.example.listfilterandsortapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.listfilterandsortapp.ui.theme.ListFilterAndSortAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    // List of fruits
    val fruits = listOf(
        "Apple", "Banana", "Orange", "Mango", "Pineapple", "Grapes", "Watermelon",
        "Strawberry", "Blueberry", "Kiwi", "Peach", "Plum", "Apricot", "Cantaloupe",
        "Papaya", "Pomegranate", "Cherry", "Lemon", "Lime", "Guava", "Lychee", "Dragon fruit",
        "Passion Fruit", "Raspberry", "Blackberry", "Coconut", "Fig"
    )

    val filteredFruits by remember {
        derivedStateOf {
            if (searchQuery.isBlank()) {
                fruits
            } else {
                fruits.filter { it.contains(searchQuery, ignoreCase = true) }
            }
        }
    }

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
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search for a fruit") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { isSorted = !isSorted }, modifier = Modifier.padding(vertical = 8.dp)) {
            Text(if (isSorted) "Disable Sorting" else "Enable Sorting")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(sortedFruits) { fruit ->
                FruitCard(fruit = fruit)
                Divider(color = Color.Gray, thickness = 1.dp)
            }
        }
    }
}

@Composable
fun FruitCard(fruit: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            // Add an icon or image
            Image(
                painter = painterResource(id = R.drawable.fruitimage), // Replace with your image resource
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 16.dp)
            )

            // Fruit name
            Text(
                text = fruit,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}


