package com.sametguler.myecommerceapp.model

import android.graphics.drawable.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.sql.Timestamp
import java.time.LocalDateTime

data class ApiResponse<T>(
    val success: Boolean,
    val message: String? = null,
    val data: T? = null
)

data class OrdersNew(
    @SerializedName("order_id")
    @Expose
    var order_id: Int,
    @SerializedName("total_price")
    @Expose
    var total_price: Double,
    @SerializedName("quantity")
    @Expose
    var quantity: Int,
    @SerializedName("user_id")
    @Expose
    var user_id: Int,
    @SerializedName("user_name")
    @Expose
    var user_name: String,
    @SerializedName("user_email")
    @Expose
    var user_email: String,
    @SerializedName("user_phone")
    @Expose
    var user_phone: String,
    @SerializedName("product_id")
    @Expose
    var product_id: Int,
    @SerializedName("product_name")
    @Expose
    var product_name: String,
    @SerializedName("product_image")
    @Expose
    var product_image: String,
    @SerializedName("product_stock")
    @Expose
    var product_stock: Int,
    @SerializedName("order_status")
    @Expose
    var order_status: String,
    @SerializedName("created_at")
    @Expose
    var created_at: String,
)

data class Users(
    @SerializedName("user_id")
    @Expose
    var user_id: Int,
    @SerializedName("user_name")
    @Expose
    var user_name: String,
    @SerializedName("user_email")
    @Expose
    var user_email: String,
    @SerializedName("user_password")
    @Expose
    var user_password: String,
    @SerializedName("user_role")
    @Expose
    var user_role: String,
    @SerializedName("user_phone")
    @Expose
    var user_phone: String
)

data class Products(
    @SerializedName("product_id")
    @Expose
    var product_id: Int,
    @SerializedName("product_name")
    @Expose
    var product_name: String,
    @SerializedName("product_desc")
    @Expose
    var product_desc: String,
    @SerializedName("product_image")
    @Expose
    var product_image: String,
    @SerializedName("product_price")
    @Expose
    var product_price: Double,
    @SerializedName("product_stock")
    @Expose
    var product_stock: Int
)

data class shoppingCartNew(
    @SerializedName("shopping_cart_id")
    @Expose
    var shopping_cart_id: Int,
    @SerializedName("user_id")
    @Expose
    var user_id: Int,
    @SerializedName("product_id")
    @Expose
    var product_id: Int,
    @SerializedName("product_name")
    @Expose
    var product_name: String,
    @SerializedName("product_price")
    @Expose
    var product_price: Double,
    @SerializedName("product_image")
    @Expose
    var product_image: String,
    @SerializedName("quantity")
    @Expose
    var quantity: Int,
)

data class ShoppingCart(
    @SerializedName("shopping_cart_id")
    @Expose
    var shopping_cart_id: Int,
    @SerializedName("user_id")
    @Expose
    var user_id: Int,
    @SerializedName("product_id")
    @Expose
    var product_id: Int,
    @SerializedName("quantity")
    @Expose
    var quantity: Int,
)

data class Orders(
    @SerializedName("order_id")
    @Expose
    var order_id: Int,
    @SerializedName("total_price")
    @Expose
    var total_price: Double,
    @SerializedName("quantity")
    @Expose
    var quantity: Int,
    @SerializedName("user_id")
    @Expose
    var user_id: Int,
    @SerializedName("product_id")
    @Expose
    var product_id: Int,
    @SerializedName("order_status")
    @Expose
    var order_status: String,
    @SerializedName("created_at")
    @Expose
    var created_at: String
)

data class NavItem(
    val label: String,
    val icon: ImageVector
)