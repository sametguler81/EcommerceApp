package com.sametguler.myecommerceapp.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//https://sametguler.online/ecommerce/

class ApiClient {
    companion object {
        fun getClient(baseUrl: String): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}