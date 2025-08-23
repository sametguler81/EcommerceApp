package com.sametguler.myecommerceapp.service

class ApiUtils {
    //https://sametguler.online/ecommerce/

    companion object {
        val BASE_URL = "https://sametguler.online/ecommerce/"

        fun getEcommerceDaoInterface(): EcommerceDaoInterface {
            return ApiClient.getClient(BASE_URL).create(EcommerceDaoInterface::class.java)
        }

    }
}