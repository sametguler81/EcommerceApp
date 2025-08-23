package com.sametguler.myecommerceapp.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sametguler.myecommerceapp.model.Products
import com.sametguler.myecommerceapp.model.ShoppingCart
import com.sametguler.myecommerceapp.model.Users
import com.sametguler.myecommerceapp.service.ApiUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EcommerceRepository {

    val dao = ApiUtils.getEcommerceDaoInterface()
    val productsGelen = MutableLiveData<List<Products>>()
    val currentUserRole = MutableLiveData<String>()
    val currentUser = MutableLiveData<Users>()
    val kayit = MutableLiveData<Boolean>()
    val shoppingCarts = MutableLiveData<List<ShoppingCart>>(emptyList())
    val getProducById = MutableLiveData<Products>()


    fun getShoppingCart() {
        val job: Job = CoroutineScope(Dispatchers.IO).launch {
            val item = dao.getShoppingCart()
            if (item.isSuccessful && item.body()?.success == true) {
                withContext(Dispatchers.Main) {
                    shoppingCarts.value = item.body()?.data!!
                }
            }
        }
    }

    fun getProductById(
        product_id: Int,
    ) {
        val job: Job = CoroutineScope(Dispatchers.IO).launch {
            val item = dao.getProductById(product_id = product_id)
            if (item.isSuccessful && item.body()?.success == true) {
                withContext(Dispatchers.Main) {
                    getProducById.value = item.body()?.data!!
                }
            }
        }
    }


    fun deleteShoppingCartItem(
        shopping_cart_id: Int,
    ) {
        val job: Job = CoroutineScope(Dispatchers.IO).launch {
            val item = dao.deleteShoppingCartItem(shopping_cart_id = shopping_cart_id)
            if (item.isSuccessful && item.body()?.success == true) {
                withContext(Dispatchers.Main) {
                    shoppingCarts.value = shoppingCarts.value.filter {
                        it.shopping_cart_id != shopping_cart_id
                    }
                }
            }
        }
    }

    fun addShoppingCartItem(
        user_id: Int,
        product_id: Int,
        quantity: Int
    ) {
        val job: Job = CoroutineScope(Dispatchers.IO).launch {
            val item = dao.addShoppingCartItem(
                user_id = user_id,
                product_id = product_id,
                quantity = quantity
            )
            if (item.isSuccessful && item.body()?.success == true) {
                val newItem = item.body()?.data
                if (newItem != null) {
                    withContext(Dispatchers.Main) {
                        shoppingCarts.value = shoppingCarts.value + newItem
                    }
                } else {
                    Log.e("repo", "null geldi")
                }
            } else {
                Log.e("repo", "api basarisiz")
            }
        }
    }

    fun kullaniciKayit(
        name: String,
        email: String,
        password: String,
        userRole: String,
        userPhone: String
    ) {
        val job: Job = CoroutineScope(Dispatchers.IO).launch {
            val user = dao.register(
                name = name,
                email = email,
                password = password,
                role = userRole,
                phone = userPhone
            )
            val body = user.body()
            if (user.isSuccessful && body?.success == true) {
                withContext(Dispatchers.Main) {
                    kayit.value = true
                }
            } else {
                withContext(Dispatchers.Main) {
                    kayit.value = false
                }
            }

        }
    }

    fun kullaniciGiris(email: String, password: String) {
        val job: Job = CoroutineScope(Dispatchers.IO).launch {
            val user = dao.login(email, password)
            val body = user.body()
            if (user.isSuccessful && body?.data != null) {
                withContext(Dispatchers.Main) {
                    currentUserRole.value = body.data.user_role
                    currentUser.value = body.data!!
                }
            } else {
                withContext(Dispatchers.Main) {
                    currentUserRole.value = ""
                }
            }
        }
    }

    fun getirUrunler() {
        val job: Job = CoroutineScope(Dispatchers.IO).launch {
            val products = dao.getProducts()
            val body = products.body()
            if (products.isSuccessful && body?.data != null) {
                withContext(Dispatchers.Main) {
                    productsGelen.value = products.body()!!.data!!
                }
            } else {
                productsGelen.value = emptyList()
            }

        }
    }


}