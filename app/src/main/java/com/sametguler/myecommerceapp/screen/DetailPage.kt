package com.sametguler.myecommerceapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sametguler.myecommerceapp.R
import com.sametguler.myecommerceapp.model.Products
import com.sametguler.myecommerceapp.viewmodel.EcommerceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailItemPage(
    navController: NavController,
    products: Products,
    viewModel: EcommerceViewModel
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    Scaffold(
        containerColor = Color.LightGray,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.tfColor),
                    titleContentColor = Color.White,
                ),
                actions = {
                    IconButton(onClick = {

                    }, modifier = Modifier.padding(end = 5.dp)) {
                        Box(
                            modifier = Modifier
                                .size(75.dp)
                                .clip(
                                    CircleShape
                                )
                                .background(color = Color.White),
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(25.dp)
                                    .align(alignment = Alignment.Center),
                                imageVector = Icons.Default.Share,
                                tint = Color.Black,
                                contentDescription = "share"
                            )
                        }
                    }
                },
                title = {
                    IconButton(onClick = {
                        navController.navigate("userHome") {
                            popUpTo(route = "userHome") {
                                inclusive = true
                            }
                        }
                    }, modifier = Modifier.padding(end = 5.dp)) {
                        Box(
                            modifier = Modifier
                                .size(75.dp)
                                .clip(
                                    CircleShape
                                )
                                .background(color = Color.White),
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(25.dp)
                                    .align(alignment = Alignment.Center),
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                tint = Color.Black,
                                contentDescription = "back"
                            )
                        }
                    }
                })
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.LightGray)
                        .weight(0.5F)
                ) {
                    val resId = context.resources.getIdentifier(
                        products.product_image,
                        "drawable",
                        context.packageName
                    )
                    if (resId != 0) {
                        Image(
                            modifier = Modifier.align(Alignment.Center),
                            painter = painterResource(id = resId),
                            contentDescription = "itemImage"
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5F)
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(R.color.tfColor)
                        ),
                        shape = RoundedCornerShape(
                            topEnd = 32.dp,
                            topStart = 32.dp,
                            bottomEnd = 0.dp,
                            bottomStart = 0.dp
                        ),
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.LightGray)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(25.dp)
                        ) {
                            Text(
                                "${products.product_name}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(0.dp, 5.dp)
                            ) {
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White,
                                    ),
                                    modifier = Modifier
                                        .weight(0.4F)
                                        .padding(4.dp)
                                ) {
                                    Row(modifier = Modifier.padding(5.dp)) {
                                        Icon(
                                            modifier = Modifier.padding(end = 2.dp),
                                            imageVector = Icons.Default.Star,
                                            tint = Color.Green,
                                            contentDescription = "star"
                                        )
                                        Text(
                                            "4.5",
                                            color = Color.Black,
                                            modifier = Modifier.padding(end = 2.dp),
                                        )
                                        Text("115 gösterim", color = Color.LightGray)
                                    }
                                }
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White,
                                    ), modifier = Modifier.padding(4.dp)
                                ) {
                                    Row(modifier = Modifier.padding(5.dp)) {
                                        Icon(
                                            modifier = Modifier.padding(end = 2.dp),
                                            imageVector = Icons.Default.ThumbUp,
                                            tint = Color.Red,
                                            contentDescription = "star"
                                        )
                                        Text("93%", color = Color.Black)
                                    }
                                }
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White,
                                    ), modifier = Modifier.padding(4.dp)
                                ) {
                                    Row(modifier = Modifier.padding(5.dp)) {
                                        Icon(
                                            modifier = Modifier.padding(end = 5.dp),
                                            imageVector = Icons.Default.Info,
                                            tint = Color.LightGray,
                                            contentDescription = "comment"
                                        )
                                        Text("142", color = Color.Black)
                                    }
                                }
                            }
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 4.dp, vertical = 5.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.LightGray
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(5.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Row {
                                        Text(
                                            "${products.product_price} ₺",
                                            color = Color.Black,
                                            fontSize = 20.sp,
                                            modifier = Modifier.padding(end = 7.dp)
                                        )
                                        Text(
                                            text = "Peşin fiyatına 6 taksit",
                                            color = Color.DarkGray,
                                            fontSize = 14.sp
                                        )
                                    }
                                    IconButton(onClick = {}) {
                                        Icon(
                                            imageVector = Icons.Default.Info,
                                            tint = Color.DarkGray,
                                            contentDescription = "info"
                                        )
                                    }
                                }
                            }
                            Text(
                                modifier = Modifier.padding(vertical = 5.dp),
                                text = "${products.product_desc}",
                                color = Color.DarkGray,
                                fontSize = 17.sp,
                            )
                            Button(
                                shape = RoundedCornerShape(7.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White,
                                    contentColor = Color.Black,
                                ),
                                onClick = {
                                    val currentUserId = viewModel.currentUser.value.user_id
                                    viewModel.addShoppingCart(
                                        user_id = currentUserId,
                                        product_id = products.product_id,
                                        quantity = 1
                                    )
                                    navController.navigate("userHome") {
                                        popUpTo(route = "userHome") {
                                            inclusive = true
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 15.dp)
                            ) {
                                Text("Sepete ekle", color = Color.Black, fontSize = 22.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
