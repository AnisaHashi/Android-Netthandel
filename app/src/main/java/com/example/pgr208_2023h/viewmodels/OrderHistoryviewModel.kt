package com.example.pgr208_2023h.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pgr208_2023h.data.OrderHistory
import com.example.pgr208_2023h.data.OrderHistoryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class OrderHistoryviewModel(private val orderHistoryDao: OrderHistoryDao) : ViewModel() {

    val orders: Flow<List<OrderHistory>> = orderHistoryDao.getAllOrderes()

    fun addOrder(order: OrderHistory) {
        viewModelScope.launch(Dispatchers.IO) {
            orderHistoryDao.addOrder(order)
        }
    }
}