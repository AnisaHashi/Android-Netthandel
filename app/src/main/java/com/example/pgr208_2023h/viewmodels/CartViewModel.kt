package com.example.pgr208_2023h.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.pgr208_2023h.models.Product

class CartViewModel : ViewModel() {
    private val _cartList = mutableStateListOf<Product>()
    val cartItems: List<Product>
        get() = _cartList

    fun addCartItem(product: Product) {
        _cartList.add(product)
    }

    fun deleteCartItem(product: Product) {
        _cartList.remove(product)
    }

    fun clear() {
        _cartList.clear()
    }

}