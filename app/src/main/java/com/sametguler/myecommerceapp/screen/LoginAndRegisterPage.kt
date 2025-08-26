package com.sametguler.myecommerceapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sametguler.myecommerceapp.R
import com.sametguler.myecommerceapp.viewmodel.EcommerceViewModel

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