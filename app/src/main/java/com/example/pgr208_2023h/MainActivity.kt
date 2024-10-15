package com.example.pgr208_2023h

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.pgr208_2023h.data.OrderesDatabase
import com.example.pgr208_2023h.screens.Details
import com.example.pgr208_2023h.screens.Favorite
import com.example.pgr208_2023h.screens.Home
import com.example.pgr208_2023h.screens.OrderHistory
import com.example.pgr208_2023h.screens.SearchScreen
import com.example.pgr208_2023h.screens.ShoppingCart
import com.example.pgr208_2023h.ui.theme.PGR2082023HTheme
import com.example.pgr208_2023h.viewmodels.CartViewModel
import com.example.pgr208_2023h.viewmodels.FavoriteViewModel
import com.example.pgr208_2023h.viewmodels.OrderHistoryviewModel
import com.example.pgr208_2023h.viewmodels.ProductViewModel


class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            OrderesDatabase::class.java,
            "app.db"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val productViewModel = ProductViewModel()
        val cartViewModel = CartViewModel()
        val orderHistoryviewModel = OrderHistoryviewModel(database.dao)
        val favoriteViewModel = FavoriteViewModel()


        super.onCreate(savedInstanceState)

        setContent {
            PGR2082023HTheme {
                Surface(
                   // modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background

                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "Home" ){
                        composable("Home"){
                            Home(navController = navController, productViewModel = productViewModel)
                        }
                        composable("ShoppingCart"){
                            ShoppingCart(navController = navController, cartViewModel = cartViewModel, orderHistoryviewModel = orderHistoryviewModel)
                        }
                        composable("Details/{productId}",
                            arguments = listOf(navArgument("productId") { type = NavType.IntType })){backStackEntry ->
                            val productId = backStackEntry.arguments?.getInt("productId") ?: -1
                            Details(navController = navController, productId = productId, productViewModel = productViewModel, cartViewModel = cartViewModel, favoriteViewModel = favoriteViewModel )

                        }
                        composable("OrderHistory"){
                            OrderHistory(navController = navController, orderHistoryviewModel)
                        }
                        composable("Favorite"){
                            Favorite(navController = navController, favoriteViewModel)
                        }

                        composable("Search"){
                            SearchScreen(navController = navController, productViewModel = productViewModel)
                        }
                    }


                }
            }
        }
    }
}







