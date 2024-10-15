package com.example.pgr208_2023h.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.pgr208_2023h.models.Product

class FavoriteViewModel : ViewModel() {
    private val _cartList = mutableStateListOf<Product>()
    val FavoriteItems: List<Product>
        get() = _cartList

    fun addFavoriteItem(favorite: Product) {
        _cartList.add(favorite)
    }
}