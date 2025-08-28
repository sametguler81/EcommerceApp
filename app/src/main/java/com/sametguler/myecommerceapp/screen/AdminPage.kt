package com.sametguler.myecommerceapp.screen

import android.graphics.Paint.Align
import android.graphics.drawable.Icon
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.sametguler.myecommerceapp.ContentScreen
import com.sametguler.myecommerceapp.R
import com.sametguler.myecommerceapp.model.NavItem
import com.sametguler.myecommerceapp.model.Orders
import com.sametguler.myecommerceapp.model.OrdersNew
import com.sametguler.myecommerceapp.model.Users
import com.sametguler.myecommerceapp.viewmodel.EcommerceViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminPage(viewModel: EcommerceViewModel, navController: NavController) {

    val orders = viewModel.getOrdersNew.observeAsState(emptyList()).value
    val currentUser = viewModel.currentUser.observeAsState().value

    val navbItemList = listOf(
        NavItem(label = "Siparişler", icon = Icons.Default.ShoppingCart),
        NavItem(label = "Ürünler", icon = Icons.Default.Home),
        NavItem(label = "Hesap", icon = Icons.Default.AccountCircle)
    )

    val selectedIndex = remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.tfColor),
                    titleContentColor = Color.White,
                ),
                title = { Text("Hello, ${currentUser?.user_name ?: "User"}!") }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = colorResource(R.color.tfColor),
                contentColor = Color.White
            ) {
                navbItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = index == selectedIndex.value,
                        onClick = {
                            selectedIndex.value = index
                        },
                        icon = {
                            Icon(
                                imageVector = navbItemList[index].icon,
                                contentDescription = "ikon"
                            )
                        },
                        label = { Text("${navbItemList[index].label}") }
                    )
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),

            ) {
            currentUser?.let { user ->
                AdminContentScreen(
                    viewModel,
                    selectedIndex.value,
                    orders = orders,
                    currentUsers = currentUser,
                    navController = navController,
                )
            } ?: Box(modifier = Modifier.fillMaxSize()) {
                Text("Yükleniyor...")
            }
        }

        // Siparişleri çekmek için sadece bir kere çalışsın
        LaunchedEffect(Unit) {
            viewModel.getOrdersNew()
        }
    }
}

@Composable
fun OrderPage(
    currentUsers: Users,
    orders: List<OrdersNew>,
    selectedIndex: Int,
    navController: NavController
) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        if (orders.isEmpty()) {
            // Sipariş yoksa ortada göster
            Text(
                text = "Sipariş yok",
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            // Sipariş varsa listele
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(orders.size) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = when (orders[it].order_status) {
                                "pending" -> colorResource(R.color.tfColor)
                                "processing" -> Color.Yellow
                                "shipping" -> Color.Gray
                                "delivered" -> Color.Green
                                else -> Color.Gray
                            }
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        val resId = context.resources.getIdentifier(
                            orders[it].product_image,
                            "drawable",
                            context.packageName
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = resId),
                                    contentDescription = "product_image",
                                    modifier = Modifier.size(75.dp)
                                )
                                Column(
                                    modifier = Modifier.padding(horizontal = 5.dp),
                                    verticalArrangement = Arrangement.SpaceEvenly,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text("Order ID")
                                    Text("${orders[it].order_id}")
                                }
                                Column(
                                    modifier = Modifier.padding(horizontal = 10.dp),
                                    verticalArrangement = Arrangement.SpaceEvenly,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text("Adet")
                                    Text("${orders[it].quantity}")
                                }
                                Column(
                                    verticalArrangement = Arrangement.SpaceEvenly,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text("Total Fiyat")
                                    Text("${orders[it].total_price} ₺")
                                }
                            }
                            IconButton(onClick = {
                                val json = Gson().toJson(orders[it])
                                navController.navigate("OrderDetail/${json}")
                            }) {
                                Icon(
                                    Icons.Default.KeyboardArrowRight,
                                    contentDescription = "rightIcon"
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetail(navController: NavController, order: OrdersNew, viewModel: EcommerceViewModel) {

    var control by remember { mutableStateOf(false) }
    var statusIndex by remember { mutableStateOf(0) }

    when (order.order_status) {
        "pending" -> statusIndex = 0
        "processing" -> statusIndex = 1
        "shipping" -> statusIndex = 2
        "delivered" -> statusIndex = 3

    }

    val statusList = listOf(
        "pending",
        "processing",
        "shipping",
        "delivered"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.tfColor),
                    titleContentColor = Color.White
                ),
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {
                            navController.navigate("AdminHome") {
                                popUpTo(route = "AdminHome") {
                                    inclusive = true
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = "left",
                                modifier = Modifier.size(30.dp)
                            )
                        }
                        Text("Sipariş ID: ${order.order_id}", fontSize = 18.sp, color = Color.White)
                    }
                },
                actions = {
                    Text(
                        "Durumu: ${order.order_status}",
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier.padding(end = 5.dp)
                    )
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(modifier = Modifier.fillMaxSize()) {
                Card(
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 16.dp,
                        bottomEnd = 16.dp,
                        bottomStart = 16.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(R.color.cardMusteri)
                    ), modifier = Modifier.padding(10.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text("Müşteri Bilgileri", color = Color.White)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Müşteri ID: ${order.user_id}", color = Color.White)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Müşteri Ad: ${order.user_name}", color = Color.White)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Müşteri Email: ${order.user_email}", color = Color.White)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Müşteri Telefon: ${order.user_phone}", color = Color.White)
                        }

                    }

                }
                Card(
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 16.dp,
                        bottomEnd = 16.dp,
                        bottomStart = 16.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(R.color.tfColor)
                    ), modifier = Modifier.padding(10.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text("Ürün Bilgileri", color = Color.White)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Ürün ID: ${order.product_id}", color = Color.White)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Ürün Ad: ${order.product_name}", color = Color.White)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Ürün Stok: ${order.product_stock}", color = Color.White)
                        }
                    }
                }
                Card(
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 16.dp,
                        bottomEnd = 16.dp,
                        bottomStart = 16.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(R.color.durumGuncelleme)
                    ), modifier = Modifier.padding(10.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text("Durum Güncelleme", color = Color.White)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box() {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.clickable {
                                        control = true
                                    }
                                ) {
                                    Text("${statusList[statusIndex]}")
                                    Icon(
                                        Icons.Default.ArrowDropDown,
                                        contentDescription = "expanded"
                                    )
                                }
                                DropdownMenu(
                                    expanded = control,
                                    onDismissRequest = { control = false }) {
                                    statusList.forEachIndexed { index, s ->
                                        DropdownMenuItem(text = { Text(s) }, onClick = {
                                            statusIndex = index
                                            control = false
                                        })
                                    }
                                }
                            }
                            Button(
                                onClick = {
                                    viewModel.updateOrderStatus(
                                        order_id = order.order_id,
                                        order_status = statusList[statusIndex]
                                    )
                                    navController.navigate("AdminHome") {
                                        popUpTo(route = "AdminHome") {
                                            inclusive = true
                                        }
                                    }
                                }, colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White,
                                    contentColor = Color.Black,
                                )
                            ) {
                                Text("Güncelle")
                            }
                        }

                    }

                }
            }
        }
    }
}


@Composable
fun AddPage(viewModel: EcommerceViewModel) {
    var tfName by remember { mutableStateOf("") }
    var tfDesc by remember { mutableStateOf("") }
    var tfResim by remember { mutableStateOf("") }
    var tfPrice by remember { mutableStateOf("") }
    var tfStock by remember { mutableStateOf("") }
    val context = LocalContext.current


    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Row {
                Card(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(end = 5.dp),
                    colors = CardDefaults.cardColors(
                        contentColor = Color.White,
                        containerColor = colorResource(R.color.yumusakYesil)
                    )
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Ürün İsmi")
                        TextField(
                            singleLine = true,          // Tek satır
                            maxLines = 1,
                            placeholder = { Text("Ürün İsmi") },
                            colors = TextFieldDefaults.colors(
                                disabledContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = colorResource(R.color.tfColor),
                                focusedLabelColor = Color.Black,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                unfocusedLabelColor = Color.Black,
                                unfocusedContainerColor = Color.Transparent
                            ),
                            modifier = Modifier.padding(
                                horizontal = 15.dp,
                                vertical = 5.dp
                            ), value = tfName, onValueChange = { tfName = it })
                    }
                }
                Card(
                    modifier = Modifier.weight(0.5f),
                    colors = CardDefaults.cardColors(
                        contentColor = Color.White,
                        containerColor = colorResource(R.color.yumusakLila)
                    )
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Ürün Detay")
                        TextField(
                            singleLine = true,          // Tek satır
                            maxLines = 1,
                            placeholder = { Text("Ürün Detay") },
                            colors = TextFieldDefaults.colors(
                                disabledContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = colorResource(R.color.tfColor),
                                focusedLabelColor = Color.Black,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                unfocusedLabelColor = Color.Black,
                                unfocusedContainerColor = Color.Transparent
                            ),
                            modifier = Modifier.padding(
                                horizontal = 15.dp,
                                vertical = 5.dp
                            ),
                            value = tfDesc, onValueChange = { tfDesc = it },
                        )
                    }
                }

            }
            Row(modifier = Modifier.padding(vertical = 10.dp)) {
                Card(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(end = 5.dp),
                    colors = CardDefaults.cardColors(
                        contentColor = Color.White,
                        containerColor = colorResource(R.color.yumusakSari)
                    )
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Ürün Resim")
                        TextField(
                            singleLine = true,          // Tek satır
                            maxLines = 1,
                            placeholder = { Text("Ürün resmi") },
                            colors = TextFieldDefaults.colors(
                                disabledContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = colorResource(R.color.tfColor),
                                focusedLabelColor = Color.Black,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                unfocusedLabelColor = Color.Black,
                                unfocusedContainerColor = Color.Transparent
                            ),
                            modifier = Modifier.padding(
                                horizontal = 15.dp,
                                vertical = 5.dp
                            ), value = tfResim, onValueChange = { tfResim = it })
                    }
                }
                Card(
                    modifier = Modifier.weight(0.5f),
                    colors = CardDefaults.cardColors(
                        contentColor = Color.White,
                        containerColor = colorResource(R.color.yumusakSeftali)
                    )
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Ürün Fiyat")
                        TextField(
                            singleLine = true,          // Tek satır
                            maxLines = 1,
                            placeholder = { Text("Ürün Fiyat") },
                            colors = TextFieldDefaults.colors(
                                disabledContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = colorResource(R.color.tfColor),
                                focusedLabelColor = Color.Black,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                unfocusedLabelColor = Color.Black,
                                unfocusedContainerColor = Color.Transparent
                            ),
                            modifier = Modifier.padding(
                                horizontal = 15.dp,
                                vertical = 5.dp
                            ),
                            value = tfPrice, onValueChange = { tfPrice = it },
                        )
                    }
                }
            }
            Row {
                Card(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(end = 5.dp),
                    colors = CardDefaults.cardColors(
                        contentColor = Color.White,
                        containerColor = colorResource(R.color.pastelmavi)
                    )
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Ürün Stok")
                        TextField(
                            singleLine = true,          // Tek satır
                            maxLines = 1,
                            placeholder = { Text("Ürün Stok") },
                            colors = TextFieldDefaults.colors(
                                disabledContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = colorResource(R.color.tfColor),
                                focusedLabelColor = Color.Black,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                unfocusedLabelColor = Color.Black,
                                unfocusedContainerColor = Color.Transparent
                            ),
                            modifier = Modifier.padding(
                                horizontal = 15.dp,
                                vertical = 5.dp
                            ), value = tfStock, onValueChange = { tfStock = it })
                    }
                }
            }
            //kaydetme kısmı
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .align(alignment = Alignment.CenterHorizontally), onClick = {
                viewModel.addProduct(
                    product_name = tfName,
                    product_desc = tfDesc,
                    product_image = tfResim,
                    product_price = tfPrice.toDouble(),
                    product_stock = tfStock.toInt()
                )
                tfName = ""
                tfDesc = ""
                tfResim = ""
                tfPrice = ""
                tfStock = ""

                Toast.makeText(context, "Ürün Eklendi", Toast.LENGTH_SHORT).show()
            }) {
                Text("Ürün Ekle")
            }

        }
    }
}

@Composable
fun AdminUserPage() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("AdminUserPage")
        }
    }
}


@Composable
fun AdminContentScreen(
    viewModel: EcommerceViewModel,
    selectedIndex: Int,
    currentUsers: Users,
    orders: List<OrdersNew>,
    navController: NavController
) {
    when (selectedIndex) {
        0 -> OrderPage(
            currentUsers,
            selectedIndex = selectedIndex,
            orders = orders,
            navController = navController
        )

        1 -> AddPage(viewModel)
        2 -> AdminUserPage()
    }
}
