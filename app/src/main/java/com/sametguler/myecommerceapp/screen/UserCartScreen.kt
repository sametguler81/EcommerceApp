package com.sametguler.myecommerceapp.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sametguler.myecommerceapp.R
import com.sametguler.myecommerceapp.viewmodel.EcommerceViewModel

@Composable
fun userCartPage(viewModel: EcommerceViewModel, navController: NavController) {
    val shoppingCartNew = viewModel.shoppingCartNew.observeAsState().value
    val currentUser = viewModel.currentUser.observeAsState().value!!.user_id
    val context = LocalContext.current
    val orderStatus by viewModel.orderStatus.observeAsState()
    LaunchedEffect(Unit) {
        viewModel.getShoppingCart(currentUser)
    }

    if (shoppingCartNew != null) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                items(shoppingCartNew.size) {
                    val resId = context.resources.getIdentifier(
                        shoppingCartNew[it].product_image,
                        "drawable",
                        context.packageName
                    )
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(id = R.color.tfColor),
                            contentColor = Color.White,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = resId),
                                    contentDescription = "itemImage",
                                    modifier = Modifier.size(75.dp)
                                )
                                Column(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .padding(start = 5.dp),
                                    verticalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Text("${shoppingCartNew[it].product_name}", color = Color.White)
                                    Text(
                                        "${shoppingCartNew[it].product_price} ₺",
                                        color = Color.White
                                    )
                                }
                            }
                            Column {
                                IconButton(onClick = {
                                    viewModel.deleteShoppingCartItem(shopping_cart_id = shoppingCartNew[it].shopping_cart_id)
                                }) {
                                    Icon(
                                        tint = Color.White,
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "delete"
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .align(alignment = Alignment.BottomCenter), onClick = {
                shoppingCartNew.forEachIndexed { index, shoppingCartNew ->
                    viewModel.addOrders(
                        user_id = shoppingCartNew.user_id,
                        product_id = shoppingCartNew.product_id,
                        quantity = shoppingCartNew.quantity,
                        total_price = shoppingCartNew.product_price
                    )
                    viewModel.deleteShoppingCartItem(shopping_cart_id = shoppingCartNew.shopping_cart_id)
                }
            }) {
                Text("Sipariş Oluştur")
            }
            LaunchedEffect(orderStatus) {
                if (orderStatus == true) {
                    Toast.makeText(context, "Sipariş oluşturuldu!", Toast.LENGTH_SHORT)
                        .show()
                    viewModel.orderStatus.value = false // Reset
                    navController.navigate("userHome") {
                        popUpTo(route = "userHome") {
                            inclusive = true
                        }
                    }
                }
            }
        }


    }


}

@Composable
fun topBarScreen(navController: NavController, viewModel: EcommerceViewModel) {

    val shoppingCartNew = viewModel.shoppingCartNew.observeAsState().value
    val currentUser = viewModel.currentUser.observeAsState().value!!.user_id
    val context = LocalContext.current
    val orderStatus by viewModel.orderStatus.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.getShoppingCart(currentUser)
    }

    if (shoppingCartNew != null) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                items(shoppingCartNew.size) {
                    val resId = context.resources.getIdentifier(
                        shoppingCartNew[it].product_image,
                        "drawable",
                        context.packageName
                    )
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(id = R.color.tfColor),
                            contentColor = Color.White,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = resId),
                                    contentDescription = "itemImage",
                                    modifier = Modifier.size(75.dp)
                                )
                                Column(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .padding(start = 5.dp),
                                    verticalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Text("${shoppingCartNew[it].product_name}", color = Color.White)
                                    Text(
                                        "${shoppingCartNew[it].product_price} ₺",
                                        color = Color.White
                                    )
                                }
                            }
                            Column {
                                IconButton(onClick = {
                                    viewModel.deleteShoppingCartItem(shopping_cart_id = shoppingCartNew[it].shopping_cart_id)
                                }) {
                                    Icon(
                                        tint = Color.White,
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "delete"
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .align(alignment = Alignment.BottomCenter), onClick = {
                shoppingCartNew.forEachIndexed { index, shoppingCartNew ->
                    viewModel.addOrders(
                        user_id = shoppingCartNew.user_id,
                        product_id = shoppingCartNew.product_id,
                        quantity = shoppingCartNew.quantity,
                        total_price = shoppingCartNew.product_price
                    )
                    viewModel.deleteShoppingCartItem(shopping_cart_id = shoppingCartNew.shopping_cart_id)
                }
            }) {
                Text("Sipariş Oluştur")
            }
            LaunchedEffect(orderStatus) {
                if (orderStatus == true) {
                    Toast.makeText(context, "Sipariş oluşturuldu!", Toast.LENGTH_SHORT)
                        .show()
                    viewModel.orderStatus.value = false // Reset
                    navController.navigate("userHome") {
                        popUpTo(route = "userHome") {
                            inclusive = true
                        }
                    }
                }
            }
        }

    }


}