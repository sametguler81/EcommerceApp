package com.sametguler.myecommerceapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sametguler.myecommerceapp.repo.EcommerceRepository
import kotlinx.coroutines.launch

class EcommerceViewModel : ViewModel() {
    val repo = EcommerceRepository()
    val products = repo.productsGelen
    val currentUserRole = repo.currentUserRole
    val currentUser = repo.currentUser
    val kayitDurum = repo.kayit
    val shoppingCartItems = repo.shoppingCarts
    val getProductById = repo.getProducById


    fun getShoppingCart() {
        viewModelScope.launch {
            repo.getShoppingCart()
        }
    }

    fun getProductById(product_id: Int) {
        viewModelScope.launch {
            repo.getProductById(product_id = product_id)
        }
    }

    fun deleteShoppingCartItem(shopping_cart_id: Int) {
        viewModelScope.launch {
            repo.deleteShoppingCartItem(shopping_cart_id = shopping_cart_id)
        }
    }

    fun addShoppingCart(user_id: Int, product_id: Int, quantity: Int) {
        viewModelScope.launch {
            repo.addShoppingCartItem(
                user_id = user_id,
                product_id = product_id,
                quantity = quantity
            )
        }
    }

    fun kullaniciKayit(name: String, email: String, password: String, phone: String) {
        viewModelScope.launch {
            repo.kullaniciKayit(
                name = name,
                email = email,
                password = password,
                userRole = "user",
                userPhone = phone
            )
        }
    }

    fun kullaniciGiris(email: String, password: String) {
        viewModelScope.launch {
            repo.kullaniciGiris(email, password)
        }
    }

    fun urunGetir() {
        viewModelScope.launch {
            repo.getirUrunler()
        }

    }

}