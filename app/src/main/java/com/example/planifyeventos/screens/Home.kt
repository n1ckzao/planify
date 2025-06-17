package com.example.planifyeventos.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.planifyeventos.R
import com.example.planifyeventos.model.*
import com.example.planifyeventos.screens.components.EventoCard
import com.example.planifyeventos.screens.components.MultiSelectDialog
import com.example.planifyeventos.service.RetrofitFactory
import com.example.planifyeventos.model.Categoria
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Home(navegacao: NavHostController?) {
    val pagerState = rememberPagerState { 5 }
    val pagerItems = listOf(
        R.drawable.carrossel1, R.drawable.carrossel2, R.drawable.carrossel3,
        R.drawable.carrossel4, R.drawable.carrossel5
    )

    val context = LocalContext.current
    val eventosState = remember { mutableStateOf<List<Evento>>(emptyList()) }
    val eventosFiltrados = remember { mutableStateOf<List<Evento>>(emptyList()) }
    var textoPesquisa by remember { mutableStateOf("") }

    var showCategoriaDialog by remember { mutableStateOf(false) }
    var showEstadoDialog by remember { mutableStateOf(false) }

    var categorias by remember { mutableStateOf<List<Categoria>>(emptyList()) }
    var estados by remember { mutableStateOf<List<Estado>>(emptyList()) }

    var categoriasSelecionadas by remember { mutableStateOf<List<String>>(emptyList()) }
    var estadosSelecionados by remember { mutableStateOf<List<String>>(emptyList()) }

    fun aplicarFiltro() {
        eventosFiltrados.value = eventosState.value.filter { evento ->
            val pesquisaOk = textoPesquisa.isBlank() || evento.titulo.contains(textoPesquisa, ignoreCase = true)

            val eventoCategoriaNome = categorias.find { it.id_categoria.toString() == evento.id_categoria }?.categoria ?: ""
            val categoriaOk = categoriasSelecionadas.isEmpty() ||
                    categoriasSelecionadas.any { it.equals(eventoCategoriaNome, ignoreCase = true) }

            val eventoEstadoNome = estados.find { it.id_estado.toString() == evento.id_estado }?.estado ?: ""
            val estadoOk = estadosSelecionados.isEmpty() ||
                    estadosSelecionados.any { it.equals(eventoEstadoNome, ignoreCase = true) }

            pesquisaOk && categoriaOk && estadoOk
        }
    }

    LaunchedEffect(Unit) {
        RetrofitFactory().getEventoService().listarEstados().enqueue(object : Callback<ResultEstado> {
            override fun onResponse(call: Call<ResultEstado>, response: Response<ResultEstado>) {
                if (response.isSuccessful) {
                    estados = response.body()?.estado ?: emptyList()

                    RetrofitFactory().getEventoService().listarCategorias().enqueue(object : Callback<ResultCategoria> {
                        override fun onResponse(call: Call<ResultCategoria>, response: Response<ResultCategoria>) {
                            if (response.isSuccessful) {
                                categorias = response.body()?.categoria ?: emptyList()

                                RetrofitFactory().getEventoService().listarEventos()
                                    .enqueue(object : Callback<ResultEvento> {
                                        override fun onResponse(call: Call<ResultEvento>, response: Response<ResultEvento>) {
                                            if (response.isSuccessful) {
                                                val eventosRecebidos = response.body()?.eventos ?: emptyList()


                                                val eventosComNomes = eventosRecebidos.map { evento ->
                                                    val eventoIdCategoria = evento.id_categoria?.trim()?.toIntOrNull()
                                                    val eventoIdEstado = evento.id_estado?.trim()?.toIntOrNull()

                                                    val nomeCategoria = categorias.find {
                                                        it.id_categoria == eventoIdCategoria
                                                    }?.categoria ?: "Desconhecido"

                                                    Log.d("DEBUG", "Evento '${evento.titulo}' → Categoria encontrada: $nomeCategoria")

                                                    val nomeEstado = estados.find {
                                                        it.id_estado == eventoIdEstado
                                                    }?.estado ?: "Estado desconhecido"

                                                    Log.d("DEBUG", "Evento '${evento.titulo}' → Categoria encontrada: $nomeCategoria")

                                                    evento.copy(
                                                        id_categoria = evento.id_categoria ?: "",
                                                        participante = evento.participante ?: emptyList(),
                                                        nomeEstado = nomeEstado,
                                                        nomeCategoria = nomeCategoria
                                                    )
                                                }
                                                eventosState.value = eventosComNomes
                                                eventosFiltrados.value = eventosComNomes
                                            } else {
                                                Log.e("Home", "Erro no código de resposta: ${response.code()}")
                                            }
                                        }

                                        override fun onFailure(call: Call<ResultEvento>, t: Throwable) {
                                            Log.e("Home", "Falha na requisição: ${t.message}")
                                        }
                                    })

                            } else {
                                Log.e("Home", "Erro ao carregar categorias")
                            }
                        }

                        override fun onFailure(call: Call<ResultCategoria>, t: Throwable) {
                            Log.e("Home", "Falha ao carregar categorias: ${t.message}")
                        }
                    })
                } else {
                    Log.e("Home", "Erro ao carregar estados")
                }
            }

            override fun onFailure(call: Call<ResultEstado>, t: Throwable) {
                Log.e("Home", "Falha ao carregar estados: ${t.message}")
            }
        })
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(90.dp)
                )

                OutlinedTextField(
                    value = textoPesquisa,
                    onValueChange = {
                        textoPesquisa = it
                        aplicarFiltro()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    placeholder = { Text("Pesquisar eventos...") },
                    singleLine = true,
                    shape = RoundedCornerShape(20.dp)
                )

                Button(
                    onClick = { navegacao?.navigate("perfil") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.size(64.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.pe),
                        contentDescription = "Perfil"
                    )
                }
            }
        }

        item {
            HorizontalPager(
                state = pagerState,
                pageSize = PageSize.Fill,
                contentPadding = PaddingValues(horizontal = 32.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) { page ->
                Image(
                    painter = painterResource(pagerItems[page]),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(30.dp))
                        .clickable {
                            Toast.makeText(context, "Imagem: $page", Toast.LENGTH_SHORT).show()
                        },
                    contentScale = ContentScale.Crop
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { index ->
                    val color = if (pagerState.currentPage == index) Color.DarkGray else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(3.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(10.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { showCategoriaDialog = true }) { Text("Categoria") }
                Button(onClick = { showEstadoDialog = true }) { Text("Estado") }
                Button(onClick = { navegacao?.navigate("criar_evento") }) { Text("Criar Evento") }
            }

            Text(
                text = "Conheça os Eventos:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )
        }

        items(eventosFiltrados.value) { evento ->
            EventoCard(evento)
        }
    }

    if (showCategoriaDialog) {
        MultiSelectDialog(
            titulo = "Categorias",
            opcoes = categorias.map { it.categoria },
            selecionados = categoriasSelecionadas,
            onConfirmar = {
                categoriasSelecionadas = it
                showCategoriaDialog = false
                aplicarFiltro()
            },
            onDismiss = { showCategoriaDialog = false }
        )
    }

    if (showEstadoDialog) {
        MultiSelectDialog(
            titulo = "Estados",
            opcoes = estados.map { it.estado },
            selecionados = estadosSelecionados,
            onConfirmar = {
                estadosSelecionados = it
                showEstadoDialog = false
                aplicarFiltro()
            },
            onDismiss = { showEstadoDialog = false }
        )
    }
}

@Preview
@Composable
private fun HomePreview() {
    val navController = rememberNavController()
    Home(navegacao = navController)
}