package com.example.planifyeventos.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planifyeventos.R
import com.example.planifyeventos.model.Evento
import com.example.planifyeventos.model.Usuario
import com.example.planifyeventos.service.RetrofitFactory
import com.example.planifyeventos.utils.SharedPrefHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CriarEvento(navegacao: NavHostController) {
    val context = LocalContext.current
    val idUsuario = SharedPrefHelper.obterIdUsuario(context)

    data class EventoFormState(
        var urlImagem: String = "",
        var titulo: String = "",
        var dataEvento: String = "",
        var horarioEvento: String = "",
        var localEvento: String = "",
        var limite: String = "",
        var categoria: String = "",
        var estado: String = "",
        var descricao: String = "",
        var valor: String = ""
    )

    val formState = remember { mutableStateOf(EventoFormState()) }

    // Função de validação dentro do escopo da composable
    fun validarCampos(): Boolean {
        return when {
            formState.value.titulo.isBlank() -> {
                Toast.makeText(context, "Título é obrigatório", Toast.LENGTH_SHORT).show()
                false
            }
            formState.value.dataEvento.isBlank() -> {
                Toast.makeText(context, "Data é obrigatória", Toast.LENGTH_SHORT).show()
                false
            }
//            !formState.value.dataEvento.matches(Regex("\\d{2}/\\d{2}/\\d{4}")) -> {
//                Toast.makeText(context, "Data deve estar no formato DD/MM/AAAA", Toast.LENGTH_SHORT).show()
//                false
//            }
//
//            !formState.value.horarioEvento.matches(Regex("\\d{2}:\\d{2}")) -> {
//                Toast.makeText(context, "Horário deve estar no formato HH:MM", Toast.LENGTH_SHORT).show()
//                false
//            }
            formState.value.horarioEvento.isBlank() -> {
                Toast.makeText(context, "Horário é obrigatório", Toast.LENGTH_SHORT).show()
                false
            }
            formState.value.localEvento.isBlank() -> {
                Toast.makeText(context, "Local é obrigatório", Toast.LENGTH_SHORT).show()
                false
            }
            formState.value.categoria.isBlank() -> {
                Toast.makeText(context, "Categoria é obrigatória", Toast.LENGTH_SHORT).show()
                false
            }
            formState.value.estado.isBlank() -> {
                Toast.makeText(context, "Estado é obrigatório", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(

            onClick = { navegacao.navigate("home") },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.width(150.dp).padding(top = 20.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier
                    .width(100.dp)
            )
        }

        OutlinedTextField(
            value = formState.value.urlImagem,
            onValueChange = { formState.value = formState.value.copy(urlImagem = it) },
            label = { Text("URL da Imagem do Evento") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = formState.value.titulo,
            onValueChange = { formState.value = formState.value.copy(titulo = it) },
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
                value = formState.value.dataEvento,
                onValueChange = { formState.value = formState.value.copy(dataEvento = it) },
                label = { Text("Data do Evento") },
                placeholder = { Text("DD/MM/AAAA") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp)
            )
            OutlinedTextField(
                value = formState.value.horarioEvento,
                onValueChange = { formState.value = formState.value.copy(horarioEvento = it) },
                label = { Text("Horário") },
                placeholder = { Text("HH:MM") },
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
                value = formState.value.localEvento,
                onValueChange = { formState.value = formState.value.copy(localEvento = it) },
                label = { Text("Local") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp)
            )
            OutlinedTextField(
                value = formState.value.limite,
                onValueChange = { formState.value = formState.value.copy(limite = it) },
                label = { Text("Limite") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.width(100.dp),
                shape = RoundedCornerShape(12.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = formState.value.categoria,
            onValueChange = { formState.value = formState.value.copy(categoria = it) },
            label = { Text("Categoria") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = formState.value.estado,
            onValueChange = { formState.value = formState.value.copy(estado = it) },
            label = { Text("Estado") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = formState.value.descricao,
            onValueChange = { formState.value = formState.value.copy(descricao = it) },
            label = { Text("Descrição do evento...") },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = formState.value.valor,
            onValueChange = { formState.value = formState.value.copy(valor = it) },
            label = { Text("Valor do evento") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (!validarCampos()) return@Button

                val evento = Evento(

                    titulo = formState.value.titulo,
                    descricao = formState.value.descricao,
                    data_evento = formState.value.dataEvento,
                    horario = formState.value.horarioEvento,
                    local = formState.value.localEvento,
                    imagem = if (formState.value.urlImagem.isBlank()) null else formState.value.urlImagem,
                    limite_participante = formState.value.limite.toIntOrNull() ?: 0,
                    valor_ingresso = formState.value.valor.toDoubleOrNull() ?: 0.0,
                    id_estado = formState.value.estado,
                    id_categoria = formState.value.categoria,
                    id_usuario = idUsuario,
                    participante = emptyList()
                )

                val call = RetrofitFactory().getEventoService().inserirEvento(evento)

                call.enqueue(object : Callback<Evento> {
                    override fun onResponse(call: Call<Evento>, response: Response<Evento>) {
                        if (response.isSuccessful) {
                            Log.i("API", "Evento cadastrado: ${response.body()}")
                            Toast.makeText(context, "Evento criado com sucesso!", Toast.LENGTH_LONG).show()
                            navegacao.popBackStack()
                        } else {
                            val errorBody = response.errorBody()?.string() ?: "Sem detalhes"
                            Log.e("API", "Erro ${response.code()}: $errorBody")
                            Toast.makeText(
                                context,
                                "Erro ao cadastrar: ${response.message()} - $errorBody",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<Evento>, t: Throwable) {
                        Log.e("API", "Falha na requisição: ${t.message}")
                        Toast.makeText(context, "Falha na conexão: ${t.message}", Toast.LENGTH_LONG).show()
                    }
                })
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007BFF)))
            {
                Text("Cadastrar evento", fontSize = 18.sp, color = Color.White)
            }

    }
}

@Preview
@Composable
private fun CriarEventoPreview() {
    val navController = rememberNavController()
    CriarEvento(navegacao = navController)
}
