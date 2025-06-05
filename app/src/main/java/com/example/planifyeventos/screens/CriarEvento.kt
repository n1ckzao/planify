package com.example.planifyeventos.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planifyeventos.exposed.Categoria
import com.example.planifyeventos.model.Evento
import com.example.planifyeventos.service.RetrofitFactory
import com.example.planifyeventos.utils.SharedPrefHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CriarEvento(navegacao: NavHostController, idUsuario: Int) {

    val context = LocalContext.current

    var urlImagem by remember { mutableStateOf(TextFieldValue("")) }
    var titulo by remember { mutableStateOf(TextFieldValue("")) }
    var dataEvento by remember { mutableStateOf(TextFieldValue("")) }
    var horarioEvento by remember { mutableStateOf(TextFieldValue("")) }
    var localEvento by remember { mutableStateOf(TextFieldValue("")) }
    var limite by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf(TextFieldValue("")) }
    var valor by remember { mutableStateOf(TextFieldValue("")) }

    val categorias = remember {
        listOf(
            Categoria(id = 1, nome = "Eletrônicos"),
            Categoria(id = 2, nome = "Roupas"),
            Categoria(id = 3, nome = "Alimentos")
        )
    }

    var categoriaSelecionadaId by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = urlImagem,
            onValueChange = { urlImagem = it },
            label = { Text("URL da Imagem do Evento") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Nome do Evento") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = dataEvento,
                onValueChange = { dataEvento = it },
                label = { Text("Data do Evento") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp)
            )
            OutlinedTextField(
                value = horarioEvento,
                onValueChange = { horarioEvento = it },
                label = { Text("Horário") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = localEvento,
                onValueChange = { localEvento = it },
                label = { Text("Local") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp)
            )
            OutlinedTextField(
                value = limite,
                onValueChange = { limite = it },
                label = { Text("Limite") },
                modifier = Modifier.width(100.dp),
                shape = RoundedCornerShape(12.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = categoria,
            onValueChange = { categoria = it },
            label = { Text("Categoria") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = estado,
            onValueChange = { estado = it },
            label = { Text("Estado") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = descricao,
            onValueChange = { descricao = it },
            label = { Text("Descrição do evento...") },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = valor,
            onValueChange = { valor = it },
            label = { Text("Valor do evento") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {

                val evento = Evento(
                    titulo = titulo.text,
                    descricao = descricao.text,
                    data_evento = dataEvento.text,
                    horario = horarioEvento.text,
                    local = localEvento.text,
                    imagem = urlImagem.text,
                    limite_participante = limite.toIntOrNull() ?: 0,
                    valor_ingresso = valor.text,
                    id_usuario = idUsuario
                )

                val call = RetrofitFactory().getEventoService().inserirEvento(evento)

                call.enqueue(object : Callback<Evento> {
                    override fun onResponse(call: Call<Evento>, response: Response<Evento>) {
                        if (response.isSuccessful) {
                            Log.i("API", "Evento cadastrado com sucesso: ${response.body()}")
                            Toast.makeText(context, "Evento cadastrado com sucesso!", Toast.LENGTH_LONG).show()
                            navegacao.navigate("perfil")
                        } else {
                            Log.e("API", "Erro ao cadastrar: ${response.code()}")
                            Toast.makeText(context, "Erro ao cadastrar evento.", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<Evento>, t: Throwable) {
                        Log.e("API", "Falha na requisição: ${t.message}")
                        Toast.makeText(context, "Falha na requisição.", Toast.LENGTH_LONG).show()
                    }
                })
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007BFF))
        ) {
            Text("Cadastrar evento", fontSize = 18.sp, color = Color.White)
        }
    }
}

@Preview
@Composable
private fun CriarEventoPreview() {
    val navController = rememberNavController()
    CriarEvento(navegacao = navController, idUsuario = 1)
}
