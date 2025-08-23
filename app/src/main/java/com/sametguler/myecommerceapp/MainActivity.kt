package com.sametguler.myecommerceapp

import android.graphics.Paint.Align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.sametguler.myecommerceapp.model.NavItem
import com.sametguler.myecommerceapp.model.Products
import com.sametguler.myecommerceapp.ui.theme.MyEcommerceAppTheme
import com.sametguler.myecommerceapp.viewmodel.EcommerceViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyEcommerceAppTheme {
                PageChanges()
            }
        }
    }
}

@Composable
fun PageChanges() {
    val viewModel: EcommerceViewModel = viewModel()
    val navController = rememberNavController()

    NavHost(navController, startDestination = "login") {
        composable(route = "login") {
            LoginPage(navController, viewModel)
        }
        composable(route = "register") {
            RegisterPage(navController, viewModel)
        }
        composable(route = "admin") {
            AdminPage()
        }
        composable(route = "user") {
            UserPageChanges(viewModel)
        }
    }
}

@Composable
fun LoginPage(navController: NavController, viewModel: EcommerceViewModel) {
    val tfEmail = remember { mutableStateOf("") }
    val tfPassword = remember { mutableStateOf("") }
    val currentUserRole = viewModel.currentUserRole.observeAsState().value

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.shopping),
                    contentDescription = "logo",
                    modifier = Modifier.size(150.dp)
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        label = { Text("Email") },
                        placeholder = { Text("Email girin") },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorResource(R.color.tfColor),
                            unfocusedContainerColor = colorResource(R.color.tfColor),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.LightGray,
                        ),
                        modifier = Modifier.padding(0.dp, 10.dp),
                        value = tfEmail.value,
                        onValueChange = {
                            tfEmail.value = it
                        })
                    TextField(
                        visualTransformation = PasswordVisualTransformation(),
                        label = { Text("Şifre") },
                        placeholder = { Text("Şifre girin") },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorResource(R.color.tfColor),
                            unfocusedContainerColor = colorResource(R.color.tfColor),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.LightGray,
                        ),
                        value = tfPassword.value,
                        onValueChange = {
                            tfPassword.value = it
                        })

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = {
                        viewModel.kullaniciGiris(tfEmail.value, tfPassword.value)

                    }) {
                        Text("Giriş Yap")
                    }
                    Button(onClick = {
                        navController.navigate("register")
                    }) {
                        Text("Hesabın yok mu?")
                    }
                }
            }
        }
    }

    LaunchedEffect(currentUserRole) {
        when (currentUserRole) {
            "admin" -> {
                navController.navigate("admin") {
                    popUpTo("login") { inclusive = true }
                }
            }

            "user" -> {
                navController.navigate("user") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
    }
}

@Composable
fun RegisterPage(navController: NavController, viewModel: EcommerceViewModel) {
    val tfName = remember { mutableStateOf("") }
    val tfEmail = remember { mutableStateOf("") }
    val tfPassword = remember { mutableStateOf("") }
    val tfPhone = remember { mutableStateOf("") }
    val kayit = viewModel.kayitDurum.observeAsState().value

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            ) {
                TextField(
                    label = { Text("Name") },
                    placeholder = { Text("Name girin") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorResource(R.color.tfColor),
                        unfocusedContainerColor = colorResource(R.color.tfColor),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.LightGray,
                    ),
                    modifier = Modifier.padding(0.dp, 10.dp),
                    value = tfName.value,
                    onValueChange = {
                        tfName.value = it
                    })
                TextField(
                    label = { Text("Email") },
                    placeholder = { Text("Email girin") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorResource(R.color.tfColor),
                        unfocusedContainerColor = colorResource(R.color.tfColor),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.LightGray,
                    ),
                    modifier = Modifier.padding(0.dp, 10.dp),
                    value = tfEmail.value,
                    onValueChange = {
                        tfEmail.value = it
                    })
                TextField(
                    label = { Text("Password") },
                    placeholder = { Text("Password girin") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorResource(R.color.tfColor),
                        unfocusedContainerColor = colorResource(R.color.tfColor),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.LightGray,
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.padding(0.dp, 10.dp),
                    value = tfPassword.value,
                    onValueChange = {
                        tfPassword.value = it
                    })
                TextField(
                    label = { Text("Phone") },
                    placeholder = { Text("Phone girin") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorResource(R.color.tfColor),
                        unfocusedContainerColor = colorResource(R.color.tfColor),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.LightGray,
                    ),
                    modifier = Modifier.padding(0.dp, 10.dp),
                    value = tfPhone.value,
                    onValueChange = {
                        tfPhone.value = it
                    })
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = {
                        viewModel.kullaniciKayit(
                            name = tfName.value,
                            email = tfEmail.value,
                            password = tfPassword.value,
                            phone = tfPhone.value
                        )

                    }) {
                        Text("Kayıt ol")
                    }
                    Button(onClick = {
                        navController.navigate("login")
                    }) {
                        Text("Hesabın var mı?")
                    }
                }
            }
        }
    }
    LaunchedEffect(kayit) {
        when (kayit) {
            true -> {
                navController.navigate("login") {
                    popUpTo("login") { inclusive = false }
                }
            }

            false -> {

            }

            else -> {

            }
        }
    }
}

@Composable
fun AdminPage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Admin Page")
    }
}

@Composable
fun UserPageChanges(viewModel: EcommerceViewModel) {
    var navController = rememberNavController()
    NavHost(navController, startDestination = "userHome") {
        composable(route = "userHome") {
            UserHome(viewModel, navController)
        }
        composable(
            route = "detailItem/{item}", arguments = listOf(
                navArgument("item", {
                    type = NavType.StringType
                })
            )
        ) {
            val json = it.arguments?.getString("item")
            val itemG = Gson().fromJson(json, Products::class.java)
            DetailItemPage(navController, itemG, viewModel)
        }
    }
}

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserHome(viewModel: EcommerceViewModel, navController: NavController) {

    val navItemList = listOf(
        NavItem("Ana Sayfa", Icons.Default.Home),
        NavItem("Sepet", Icons.Default.ShoppingCart),
        NavItem("Hesabım", Icons.Default.AccountCircle)
    )

    val selectedIndex = remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.tfColor)
                ),
                title = { Text("Bilgisayarcım", color = Color.White) },
                actions = {
                    IconButton(onClick = {
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.sepet),
                            contentDescription = "sepet",
                            tint = Color.White,
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .size(30.dp)
                        )
                    }
                })
        },
        bottomBar = {
            NavigationBar(
                containerColor = colorResource(R.color.tfColor),
                contentColor = Color.White
            ) {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = Color.White,
                            unselectedIconColor = Color.DarkGray,
                            unselectedTextColor = Color.DarkGray
                        ),
                        selected = index == selectedIndex.value,
                        onClick = {
                            selectedIndex.value = index
                        },
                        icon = {
                            Icon(
                                imageVector = navItem.icon,
                                contentDescription = "${navItem.label}"
                            )
                        },
                        label = { Text(navItem.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            ContentScreen(index = selectedIndex.value, viewModel, navController)
        }
    }
}

@Composable
fun userHomePage(viewModel: EcommerceViewModel, navController: NavController) {
    val products = viewModel.products.observeAsState(emptyList())
    val listState = rememberLazyListState()
    val context = LocalContext.current
    val tfSearch = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.urunGetir()
    }



    LaunchedEffect(true) {
        var index = 0
        while (true) {
            delay(2000)
            index = (index + 1) % 3
            listState.animateScrollToItem(index)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2B68AE), // Top bar ile uyumlu lacivert
                        Color(0xFF4A90E2), // Daha canlı mavi
                        Color(0xFF7EB3F5)  // Açık mavi
                    )
                )
            )
    ) {
        LazyRow(
            state = listState,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp) // öğeler arası boşluk
        ) {
            item {
                Image(
                    painter = painterResource(R.drawable.banner1),
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .height(200.dp),
                    contentDescription = "banner1"
                )
            }
            item {
                Image(
                    painter = painterResource(R.drawable.banner2),
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .height(200.dp),
                    contentDescription = "banner2"
                )
            }
            item {
                Image(
                    painter = painterResource(R.drawable.banner3),
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .height(200.dp),
                    contentDescription = "banner3"
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 15.dp)
        ) {

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        2.dp,
                        color = colorResource(R.color.tfColor),
                        shape = RoundedCornerShape(16.dp)
                    ),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        tint = Color.Black,
                        contentDescription = "search"
                    )
                },
                shape = RoundedCornerShape(16.dp),
                placeholder = { Text("Arama yap", color = Color.LightGray) },
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                value = tfSearch.value,
                onValueChange = {
                    tfSearch.value = it
                },
            )

        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (products.value.isNotEmpty()) {
                items(products.value.size) {
                    val resId = context.resources.getIdentifier(
                        products.value[it].product_image,
                        "drawable",
                        context.packageName
                    )
                    if (resId != 0) {
                        Card(
                            border = BorderStroke(
                                width = 2.dp,
                                color = colorResource(R.color.tfColor),
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White,
                                contentColor = Color.Black,
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .height(250.dp)
                                .clickable {
                                    val item = products.value[it]
                                    val json = Gson().toJson(item)
                                    navController.navigate("detailItem/${json}")
                                }
                        ) {
                            Column(verticalArrangement = Arrangement.SpaceBetween) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Image(
                                        modifier = Modifier.size(150.dp),
                                        alignment = Alignment.Center,
                                        painter = painterResource(id = resId),
                                        contentDescription = "${products.value[it].product_image}"
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 5.dp),
                                    verticalArrangement = Arrangement.SpaceEvenly,
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(
                                        products.value[it].product_name,
                                        color = Color.Black,
                                        fontSize = 18.sp,
                                        modifier = Modifier.padding(horizontal = 5.dp)
                                    )
                                    Text(
                                        "${products.value[it].product_price} ₺",
                                        color = Color.Black,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 5.dp)
                                    )
                                }

                            }
                        }
                    } else {
                        Card {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("Ürünler yükleniyor...")
                            }
                        }
                    }

                }
            }
        }
    }


}

@Composable
fun userCartPage(viewModel: EcommerceViewModel) {


}

@Composable
fun userProfilePage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("User Profile Page")
    }
}

@Composable
fun ContentScreen(index: Int, viewModel: EcommerceViewModel, navController: NavController) {
    when (index) {
        0 -> userHomePage(viewModel, navController)
        1 -> userCartPage(viewModel)
        2 -> userProfilePage()
    }
}