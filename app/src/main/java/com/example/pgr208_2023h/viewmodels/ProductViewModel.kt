package com.example.pgr208_2023h.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pgr208_2023h.models.Product
import com.example.pgr208_2023h.services.Productservice
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val _productList = mutableStateListOf<Product>()
    val products: List<Product>
        get() = _productList

    fun getProducts() {
        viewModelScope.launch {
            val service = Productservice.getInstance()
            _productList.clear()
            _productList.addAll(service.getProucts())

        }
    }
}