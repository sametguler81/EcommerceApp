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
    val shoppingCartNew = repo.shoppingCartsNew
    val getProductById = repo.getProducById
    val getOrdes = repo.getOrders
    val orderStatus = repo.orderStatus
    val getOrdersNew = repo.ordersNew

    fun addProduct(
        product_name: String,
        product_desc: String,
        product_image: String,
        product_price: Double,
        product_stock: Int
    ) {
        viewModelScope.launch {
            repo.addProduct(product_name, product_desc, product_image, product_price, product_stock)
        }

    }

    fun updateOrderStatus(order_id: Int, order_status: String) {
        viewModelScope.launch {
            repo.updateOrderStatus(order_id, order_status)
        }
    }

    fun getOrdersNew() {
        viewModelScope.launch {
            repo.getOrdersNew()
        }
    }

    fun addOrders(user_id: Int, product_id: Int, quantity: Int, total_price: Double) {
        viewModelScope.launch {
            repo.addOrders(user_id, product_id, quantity, total_price)
        }
    }

    fun getOrders() {
        viewModelScope.launch {
            repo.getOrders()
        }
    }

    fun getShoppingCart(user_id: Int) {
        viewModelScope.launch {
            repo.getShoppingCart(user_id = user_id)
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
            // veritabanında sildikten sonra LiveData güncelle
            shoppingCartNew.value = shoppingCartNew.value?.filter {
                it.shopping_cart_id != shopping_cart_id
            }
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