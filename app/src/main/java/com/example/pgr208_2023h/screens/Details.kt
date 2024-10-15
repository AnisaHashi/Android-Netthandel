package com.example.pgr208_2023h.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.pgr208_2023h.components.AppNavigationDrawer
import com.example.pgr208_2023h.models.Product
import com.example.pgr208_2023h.viewmodels.CartViewModel
import com.example.pgr208_2023h.viewmodels.FavoriteViewModel
import com.example.pgr208_2023h.viewmodels.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Details(
    navController: NavController,
    productId: Int,
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel,
    favoriteViewModel: FavoriteViewModel
) {

    val product = productViewModel.products.find { p -> p.id == productId }

    AppNavigationDrawer(navController = navController, title = "Product details") {
        if (product != null) {
            val isFavorite = favoriteViewModel.FavoriteItems.any { it.id == productId }
            New(innerPadding = it, product = product, isFavorite = isFavorite, onAddCart = {
                cartViewModel.addCartItem(product = product)
                navController.navigate("ShoppingCart")

            }, onAddFav = {
                favoriteViewModel.addFavoriteItem(it)
            })
        }
    }
}

@Composable
fun New(
    innerPadding: PaddingValues,
    product: Product,
    isFavorite: Boolean,
    onAddCart: (product: Product) -> Unit,
    onAddFav: (product: Product) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .verticalScroll(state = scrollState)
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),

            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .padding(16.dp)


        ) {
            Image(
                painter = rememberImagePainter(
                    data = product.image, // Replace with your image URL
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
            )
        }
        Text(
            text = product.title,
            modifier = Modifier
                .padding(16.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal
        )
        Row(
            modifier =
            Modifier
                .padding(16.dp)
                .fillMaxWidth(0.6f)
                .align(alignment = Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Text(text = product.category)
            Row {
                Text(text = "${product.rating.rate}")
                Icon(
                    imageVector = Icons.Filled.Star,
                    tint = Color(android.graphics.Color.parseColor("#ccbb39")),

                    contentDescription = "Localized description"
                )
                Text(text = "${product.rating.count}")
            }
        }

        Text(
            text = product.description,
            modifier = Modifier
                .padding(16.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            IconButton(onClick = { onAddFav(product) }) {
                if (isFavorite) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        modifier = Modifier.size(50.dp),
                        contentDescription = "Localized description",
                        tint = Color.Red
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.FavoriteBorder,
                        modifier = Modifier.size(50.dp),
                        contentDescription = "Localized description",
                    )
                }
            }
            IconButton(onClick = { onAddCart(product) }) {
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    modifier = Modifier.size(50.dp),
                    contentDescription = "Localized description",
                )
            }
        }
    }
}

