package com.example.planifyeventos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.planifyeventos.screens.Cadastro
import com.example.planifyeventos.screens.Home
import com.example.planifyeventos.screens.Login

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navegacao = rememberNavController()
            NavHost(
                navController = navegacao,
                startDestination = "login"
            ){
                composable(route = "cadastro") { Cadastro(navegacao) }
                composable(route = "login") { Login(navegacao) }
                composable(route = "home") { Home(navegacao) }
            }
        }
    }
}