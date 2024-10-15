package com.example.pgr208_2023h.services

import com.example.pgr208_2023h.models.Product
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://fakestoreapi.com"

interface Productservice {
    @GET("/products?limit=10")
    suspend fun getProucts(): List<Product>

    companion object {
        var apiService: Productservice? = null
        fun getInstance(): Productservice {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(Productservice::class.java)
            }
            return apiService!!
        }
    }
}