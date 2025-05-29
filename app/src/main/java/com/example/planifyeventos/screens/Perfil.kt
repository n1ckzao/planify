package com.example.planifyeventos.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment

@Composable
fun Perfil(navegacao: NavHostController) {
    val usuarioList = remember { mutableStateOf<List<Usuario>>(emptyList()) }
    val loading = remember { mutableStateOf(true) }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        val callUsuario = RetrofitFactory()
            .getUsuarioService()
            .listarUsuarios()

        callUsuario.enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                loading.value = false
                if (response.isSuccessful) {
                    response.body()?.results?.let {
                        usuarioList.value = it
                        errorMessage.value = null
                    } ?: run {
                        errorMessage.value = "Nenhum usuário encontrado"
                    }
                } else {
                    errorMessage.value = "Erro ao carregar usuários: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                loading.value = false
                errorMessage.value = "Falha na conexão: ${t.message}"
            }
        })
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        when {
            loading.value -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            errorMessage.value != null -> {
                Text(
                    text = errorMessage.value ?: "Erro desconhecido",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                LazyColumn {
                    items(usuarioList.value) { usuario ->
                        UsuarioCard(
                            nome = usuario.nome,
                            email = usuario.email,
                            data_nascimento = usuario.data_nascimento,
                            palavra_chave = usuario.palavra_chave,
                            foto_perfil = usuario.foto_perfil,
                            senha = usuario.senha
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PerfilPreview() {
    val fakeNavController = rememberNavController()
    Perfil(navegacao = fakeNavController)
}