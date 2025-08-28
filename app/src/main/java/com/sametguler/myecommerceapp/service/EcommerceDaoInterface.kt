package com.sametguler.myecommerceapp.service

import com.sametguler.myecommerceapp.model.*
import retrofit2.Response
import retrofit2.http.*

interface EcommerceDaoInterface {

    // Kullanıcı Kayıt
    @FormUrlEncoded
    @POST("register.php")
    suspend fun register(
        @Field("user_name") name: String,
        @Field("user_email") email: String,
        @Field("user_password") password: String,
        @Field("user_role") role: String = "customer",
        @Field("user_phone") phone: String
    ): Response<ApiResponse<Users>>

    @FormUrlEncoded
    @POST("create_Product.php")
    suspend fun addProduct(
        @Field("product_name") product_name: String,
        @Field("product_desc") product_desc: String,
        @Field("product_image") product_image: String,
        @Field("product_price") product_price: Double,
        @Field("product_stock") product_stock: Int,
    ): Response<ApiResponse<Products>>

    // Giriş
    @FormUrlEncoded
    @POST("login.php")
    suspend fun login(
        @Field("user_email") email: String,
        @Field("user_password") password: String
    ): Response<ApiResponse<Users>>

    @FormUrlEncoded
    @POST("get_shopping_cart.php")
    suspend fun getShoppingCart(
        @Field("user_id") user_id: Int
    ): Response<ApiResponse<List<shoppingCartNew>>>

    // Ürünleri Getir
    @GET("get_products.php")
    suspend fun getProducts(): Response<ApiResponse<List<Products>>>

    @GET("get_orders_new.php")
    suspend fun getOrdersNew(): Response<ApiResponse<List<OrdersNew>>>

    // Ürün Güncelle
    @FormUrlEncoded
    @POST("update_product.php")
    suspend fun updateProduct(
        @Field("product_id") product_id: Int,
        @Field("product_name") product_name: String,
        @Field("product_desc") product_desc: String,
        @Field("product_image") product_image: String,
        @Field("product_price") product_price: Double,
        @Field("product_stock") product_stock: Int
    ): Response<ApiResponse<Products>>

    // Ürün Sil
    @FormUrlEncoded
    @POST("delete_product.php")
    suspend fun deleteProduct(
        @Field("product_id") product_id: Int
    ): Response<ApiResponse<Products>>

    //Sepetteki ürünü sil
    @FormUrlEncoded
    @POST("delete_shopping_cart.php")
    suspend fun deleteShoppingCartItem(
        @Field("shopping_cart_id") shopping_cart_id: Int
    ): Response<ApiResponse<ShoppingCart>>


    // Sipariş Oluştur
    @FormUrlEncoded
    @POST("create_order.php")
    suspend fun createOrder(
        @Field("user_id") user_id: Int,
        @Field("product_id") product_id: Int,
        @Field("quantity") quantity: Int,
        @Field("total_price") total_price: Double
    ): Response<ApiResponse<Orders>>

    @FormUrlEncoded
    @POST("add_shopping_cart.php")
    suspend fun addShoppingCartItem(
        @Field("user_id") user_id: Int,
        @Field("product_id") product_id: Int,
        @Field("quantity") quantity: Int,
    ): Response<ApiResponse<ShoppingCart>>

    @FormUrlEncoded
    @POST("get_product_id.php")
    suspend fun getProductById(
        @Field("product_id") product_id: Int
    ): Response<ApiResponse<Products>>

    // Tüm Siparişleri Getir
    @GET("get_orders.php")
    suspend fun getOrders(): Response<ApiResponse<List<Orders>>>

    // Sipariş Durumunu Güncelle
    @FormUrlEncoded
    @POST("update_order_status.php")
    suspend fun updateOrderStatus(
        @Field("order_id") order_id: Int,
        @Field("order_status") order_status: String
    ): Response<ApiResponse<Orders>>
}
