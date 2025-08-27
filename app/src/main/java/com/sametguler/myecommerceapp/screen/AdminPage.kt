package com.sametguler.myecommerceapp.screen

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sametguler.myecommerceapp.ContentScreen
import com.sametguler.myecommerceapp.R
import com.sametguler.myecommerceapp.model.NavItem
import com.sametguler.myecommerceapp.model.Orders
import com.sametguler.myecommerceapp.model.Users
import com.sametguler.myecommerceapp.viewmodel.EcommerceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminPage(viewModel: EcommerceViewModel) {

    val orders = viewModel.getOrdes.observeAsState(emptyList()).value
    val currentUser = viewModel.currentUser.observeAsState().value

    val navbItemList = listOf(
        NavItem(label = "Siparişler", icon = Icons.Default.ShoppingCart),
        NavItem(label = "Bakiye", icon = Icons.Default.Star),
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
                AdminContentScreen(selectedIndex.value, orders = orders, currentUsers = currentUser)
            } ?: Box(modifier = Modifier.fillMaxSize()) {
                Text("Yükleniyor...")
            }
        }

        // Siparişleri çekmek için sadece bir kere çalışsın
        LaunchedEffect(Unit) {
            viewModel.getOrders()
        }
    }
}

@Composable
fun OrderPage(currentUsers: Users, orders: List<Orders>, selectedIndex: Int) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (orders.isEmpty()) {
            // Sipariş yoksa ortada göster
            Text(
                text = "Sipariş yok",
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            // Sipariş varsa listele
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                orders.forEach { order ->
                    Text(
                        "Order Page"
                    )
                }
            }
        }
    }
}


@Composable
fun WalletPage() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("WalletPage")
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
fun AdminContentScreen(selectedIndex: Int, currentUsers: Users, orders: List<Orders>) {
    when (selectedIndex) {
        0 -> OrderPage(currentUsers, selectedIndex = selectedIndex, orders = orders)
        1 -> WalletPage()
        2 -> AdminUserPage()
    }
}
