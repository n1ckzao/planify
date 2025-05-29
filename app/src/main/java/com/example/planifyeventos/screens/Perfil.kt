package com.example.planifyeventos.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.planifyeventos.screens.components.UsuarioCard
import com.example.planifyeventos.service.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun Perfil(navegacao: NavHostController) {
    val usuarioList = remember { mutableStateOf<List<Usuario>>(emptyList()) }

    LaunchedEffect (Unit) {
        val callUsuario = RetrofitFactory()
            .getUsuarioService()
            .listarUsuarios()

        callUsuario.enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                if (response.isSuccessful) {
                    response.body()?.results?.let {
                        usuarioList.value = it
                    }
                }
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
            }
        })
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            LazyColumn {
                items(usuarioList.value) {
                    UsuarioCard(
                        nome = it.nome,
                        email = it.email,
                        data_nascimento = it.data_nascimento,
                        palavra_chave = it.palavra_chave,
                        foto_perfil = it.foto_perfil
                    )
                }
            }
        }
    }
}
@Composable
fun UsuarioItem(usuario: Usuario) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "Nome: ${usuario.nome}")
        Text(text = "Email: ${usuario.email}")
    }
}

@Preview
@Composable
private fun PerfilPreview() {
    val fakeNavController = rememberNavController()
    Perfil(navegacao = fakeNavController)
}