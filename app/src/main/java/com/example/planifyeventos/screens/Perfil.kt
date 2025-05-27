package com.example.planifyeventos.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.planifyeventos.model.Usuario
import com.example.planifyeventos.model.Result
import com.example.planifyeventos.service.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun Perfil(navegacao:NavHostController) {


    //Variável que guarda a lista de personagens
    //devolvidas pela api
    var UsuarioList = remember {
        mutableStateOf(listOf<Usuario>())
    }

    //Obter um retrofit factory
    var callUsuario = RetrofitFactory()
        .getUsuarioService()
        .listarUsuarios()

    //enviar a requisição
    callUsuario.enqueue(object : Callback<Result> {
        override fun onResponse(p0: Call<Result>, response: Response<Result>) {
            UsuarioList.value = response.body()!!.results
        }

        override fun onFailure(p0: Call<Result>, p1: Throwable) {
            TODO("Not yet implemented")
        }

    })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ){
        }
    }
}

@Preview
@Composable
private fun PerfilPreview() {
    val fakeNavController = rememberNavController()
    Perfil(navegacao = fakeNavController)
}