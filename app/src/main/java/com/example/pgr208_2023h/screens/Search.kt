package com.example.pgr208_2023h.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction.Companion.Search
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pgr208_2023h.components.AppNavigationDrawer
import com.example.pgr208_2023h.models.Product
import com.example.pgr208_2023h.viewmodels.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController, productViewModel: ProductViewModel) {
    var query by remember { mutableStateOf("") }
    var searched by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf<List<Product>>(emptyList()) }

    AppNavigationDrawer(navController = navController, title = "Search") { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Search input field
            TextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Search") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = Search
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .padding(bottom = 16.dp)
            )

            // Search button
            Button(
                onClick = {

                    val searchResult = productViewModel.products.filter {
                        it.title.lowercase().contains(query.lowercase()) || it.description.lowercase()
                            .contains(query.lowercase())
                    }
                    result = searchResult
                    searched = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Search")
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (searched) {
                    if (result.isNotEmpty()) {


                        items(result) { product ->
                            ProductCard(
                                product,
                                onClick = { navController.navigate("Details/${product.id}") })
                        }
                    } else {
                        item {
                            Column {
                                Text(text = "No match found!", modifier = Modifier.padding(16.dp))
                            }
                        }
                    }
                }

            }
        }

    }

}
