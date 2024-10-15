package com.example.pgr208_2023h.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pgr208_2023h.components.AppNavigationDrawer
import com.example.pgr208_2023h.data.OrderHistory
import com.example.pgr208_2023h.models.Product
import com.example.pgr208_2023h.viewmodels.CartViewModel
import com.example.pgr208_2023h.viewmodels.OrderHistoryviewModel
import java.time.LocalDateTime

@Composable
fun ShoppingCartItem(product: Product, onDelete: () -> Unit) {

    Row(
        verticalAlignment = Alignment.CenterVertically,

        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )

    {
        // Card
        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f),
        ) {
            ProductCard(product = product, onClick = {})
        }
        // Button delete
        IconButton(onClick = onDelete) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Localized description"
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingCart(
    navController: NavController,
    cartViewModel: CartViewModel,
    orderHistoryviewModel: OrderHistoryviewModel
) {

    AppNavigationDrawer(navController = navController, title = "Cart") { innerPadding ->

        Column(modifier = Modifier.fillMaxHeight()) {
            LazyColumn(
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (cartViewModel.cartItems.isNotEmpty()) {

                    items(cartViewModel.cartItems) { product ->
                        ShoppingCartItem(
                            product = product,
                            onDelete = { cartViewModel.deleteCartItem(product) })
                    }
                } else {
                    item {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                        ) {
                            Text(
                                "Your shopping cart is empty!"
                            )
                        }

                    }
                }

            }

            if (cartViewModel.cartItems.isNotEmpty()) {

                Button(
                    onClick = {
                        val date = LocalDateTime.now()
                        val sum = cartViewModel.cartItems.sumOf { it.price }
                        val antall = cartViewModel.cartItems.size
                        val items = cartViewModel.cartItems.map { it.title }


                        //   val orderHistory= OrderHistory(date = date, items = items, sumPrice = sum, sumItems =  antall)
                        val orderHistory =
                            OrderHistory(
                                date = date,
                                items = items,
                                sumPrice = sum,
                                sumItems = antall
                            )
                        orderHistoryviewModel.addOrder(orderHistory)

                        cartViewModel.clear()

                        navController.navigate("OrderHistory")

                    },
                    modifier = Modifier
                        .fillMaxWidth()


                ) {
                    Text("Place order (${cartViewModel.cartItems.sumOf { it.price }})")
                }
            }

        }
    }


}