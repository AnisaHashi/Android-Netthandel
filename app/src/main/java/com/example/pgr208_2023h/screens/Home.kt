package com.example.pgr208_2023h.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.pgr208_2023h.components.AppNavigationDrawer

import com.example.pgr208_2023h.models.Product
import com.example.pgr208_2023h.viewmodels.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController, productViewModel: ProductViewModel) {
    LaunchedEffect(Unit, block = {
        productViewModel.getProducts()
    })

    AppNavigationDrawer(
        navController = navController,
        title = "Products",
        backNavigationEnabled = false
    ) {
        ScrollContent(
            innerPadding = it,
            products = productViewModel.products,
            onClick = { navController.navigate("Details/${it}") })
    }
}

@Composable
fun ScrollContent(
    innerPadding: PaddingValues,
    products: List<Product>,
    onClick: (productId: Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = innerPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { product ->
            ProductCard(product, onClick)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCard(product: Product, onClick: (productId: Int) -> Unit) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(16.dp),

        onClick = { onClick(product.id) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            // Image on the left
            Image(
                painter = rememberImagePainter(
                    data = product.image, // Replace with your image URL
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.White)
            )

            // Spacer to separate image and text
            Spacer(modifier = Modifier.width(16.dp))

            // Title and text on the right
            Column {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleMedium,

                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = product.category,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

    }

}
