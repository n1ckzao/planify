package com.example.planifyeventos.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.planifyeventos.model.Evento
import com.example.planifyeventos.model.ResultEvento
import com.example.planifyeventos.screens.components.EventoCard
import com.example.planifyeventos.service.RetrofitFactory
import com.example.planifyeventos.utils.SharedPrefHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun EventosCriados(navegacao: NavHostController) {
    val context = LocalContext.current
    val idUsuarioLogado = SharedPrefHelper.obterIdUsuario(context)

    val eventosUsuario = remember { mutableStateOf<List<Evento>>(emptyList()) }
    val loading = remember { mutableStateOf(true) }
    val erro = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        RetrofitFactory().getEventoService().listarEventos().enqueue(object : Callback<ResultEvento> {
            override fun onResponse(call: Call<ResultEvento>, response: Response<ResultEvento>) {
                loading.value = false
                if (response.isSuccessful) {
                    val eventosFiltrados = response.body()?.eventos?.filter {
                        it.id_usuario == idUsuarioLogado
                    } ?: emptyList()
                    eventosUsuario.value = eventosFiltrados
                } else {
                    erro.value = "Erro ao carregar eventos: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<ResultEvento>, t: Throwable) {
                loading.value = false
                erro.value = "Falha na conexão: ${t.message}"
            }
        })
    }


    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        when {
            loading.value -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            erro.value != null -> {
                Text(text = erro.value ?: "", color = Color.Red, modifier = Modifier.align(Alignment.Center))
            }
            else -> {
                if (eventosUsuario.value.isEmpty()) {
                    Text("Você ainda não criou eventos.", modifier = Modifier.align(Alignment.Center))
                } else {
                    LazyColumn {
                        items(eventosUsuario.value) { evento ->
                            EventoCard(evento = evento)
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun EventosCriadosPreview() {
    val navController = rememberNavController()
    EventosCriados(navegacao = navController)
}