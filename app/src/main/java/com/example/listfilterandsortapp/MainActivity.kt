package com.example.listfilterandsortapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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

    // List of fruits with their corresponding emojis
    val fruitEmojiMap = mapOf(
        "Apple" to "🍎", "Banana" to "🍌", "Orange" to "🍊", "Mango" to "🥭", "Pineapple" to "🍍",
        "Grapes" to "🍇", "Watermelon" to "🍉", "Strawberry" to "🍓", "Blueberry" to "🫐",
        "Kiwi" to "🥝", "Peach" to "🍑", "Plum" to "🍈", "Apricot" to "🍑", "Cantaloupe" to "🍈",
        "Papaya" to "🥭", "Pomegranate" to "🍎", "Cherry" to "🍒", "Lemon" to "🍋", "Lime" to "🍋",
        "Guava" to "🥝", "Lychee" to "🍒", "Dragon fruit" to "🐉", "Passion Fruit" to "🍈",
        "Raspberry" to "🍇", "Blackberry" to "🍇", "Coconut" to "🥥", "Fig" to "🍈"
    )

    val fruits = fruitEmojiMap.keys.toList()

    // Derived state for filtering and sorting
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
                FruitCard(fruit = fruit, emoji = fruitEmojiMap[fruit] ?: "🍏")
                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f), thickness = 1.dp)
            }
        }
    }
}

@Composable
fun FruitCard(fruit: String, emoji: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            // Emoji as the "image"
            Text(
                text = emoji,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(end = 16.dp)
            )

            // Fruit name
            Text(
                text = fruit,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}
