package com.example.planifyeventos.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.planifyeventos.R
import com.example.planifyeventos.model.Usuario
import com.example.planifyeventos.service.RetrofitFactory
import com.example.planifyeventos.utils.SharedPrefHelper


@Composable
fun Cadastro(navegacao:NavHostController) {

    val nome = remember { mutableStateOf("") }
    val dataNascimento = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val senha = remember { mutableStateOf("") }
    val confirmarSenha = remember { mutableStateOf("") }
    val fotoPerfil = remember { mutableStateOf("") }
    val palavraChave = remember { mutableStateOf("") }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Card(
                modifier = Modifier
                    .fillMaxSize(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(30.dp),
                border = BorderStroke(
                    width = 14.dp,
                    color = Color(0xFF008EFF)
                )
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(40.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ){
                    Image(
                        painter = painterResource(R.drawable.logo),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    )
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        Text(
                            text = "Nome:",
                            fontSize = 15.sp
                        )
                        OutlinedTextField(
                            value = nome.value,
                            onValueChange = { nome.value = it },
                            shape = RoundedCornerShape(33.dp),
                            singleLine = true,
                            modifier = Modifier
                                .height(50.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Data de nascimento:",
                            fontSize = 15.sp
                        )
                        OutlinedTextField(
                            value = dataNascimento.value,
                            onValueChange = { dataNascimento.value = it },
                            shape = RoundedCornerShape(33.dp),
                            singleLine = true,
                            modifier = Modifier
                                .height(50.dp)
                                .width(150.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Email:",
                            fontSize = 15.sp
                        )
                        OutlinedTextField(
                            value = email.value,
                            onValueChange = { email.value = it },
                            shape = RoundedCornerShape(33.dp),
                            singleLine = true,
                            modifier = Modifier
                                .height(50.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Next
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Senha:",
                            fontSize = 15.sp
                        )
                        OutlinedTextField(
                            value = senha.value,
                            onValueChange = { senha.value = it },
                            shape = RoundedCornerShape(33.dp),
                            singleLine = true,
                            modifier = Modifier
                                .height(50.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Next
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Confirmar Senha:",
                            fontSize = 15.sp
                        )
                        OutlinedTextField(
                            value = confirmarSenha.value,
                            onValueChange = {confirmarSenha.value = it},
                            shape = RoundedCornerShape(33.dp),
                            singleLine = true,
                            modifier = Modifier
                                .height(50.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Next
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Palavra Chave:",
                            fontSize = 15.sp
                        )
                        OutlinedTextField(
                            value = palavraChave.value,
                            onValueChange = { palavraChave.value = it },
                            shape = RoundedCornerShape(33.dp),
                            singleLine = true,
                            modifier = Modifier
                                .height(50.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "URL da foto de perfil:",
                            fontSize = 15.sp
                        )
                        OutlinedTextField(
                            value = fotoPerfil.value,
                            onValueChange = { fotoPerfil.value = it },
                            shape = RoundedCornerShape(33.dp),
                            singleLine = true,
                            modifier = Modifier
                                .height(50.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            )
                        )
                    }
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Button(
                            onClick = {
                                val Usuario = Usuario(
                                    nome = nome.value,
                                    email = email.value,
                                    senha = senha.value,
                                    data_nascimento = dataNascimento.value,
                                    foto_perfil = fotoPerfil.value,
                                    palavra_chave = palavraChave.value
                                )

                                val call = RetrofitFactory().getUsuarioService().inserirUsuario(Usuario)

                                call.enqueue(object : retrofit2.Callback<Usuario> {
                                    override fun onResponse(
                                        call: retrofit2.Call<Usuario>,
                                        response: retrofit2.Response<Usuario>,

                                    ) {
                                        if (response.isSuccessful) {
                                            Log.i("API", "Usuário cadastrado com sucesso: ${response.body()}")
                                            SharedPrefHelper.salvarEmail(context, email.value)
                                            navegacao.navigate(route = "perfil")
                                        } else {
                                            Log.e("API", "Erro ao cadastrar: ${response.code()}")
                                        }
                                    }

                                    override fun onFailure(call: retrofit2.Call<Usuario>, t: Throwable) {
                                        Log.e("API", "Falha na requisição: ${t.message}")
                                    }
                                })
                            },

                            modifier = Modifier
                                .width(200.dp)
                                .align(Alignment.CenterHorizontally),
                            shape = RoundedCornerShape(48.dp),
                            colors = ButtonDefaults.buttonColors(
                                Color(0xFF008EFF)
                            )
                        ) {
                            Text(
                                text = "Cadastrar",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Row (
                            modifier = Modifier
                                .height(60.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(
                                text = "Já tem uma conta?",
                                fontSize = 15.sp
                            )
                            Button(
                                modifier = Modifier.height(45.dp),
                                colors = ButtonDefaults.buttonColors(Color.Transparent),
                                onClick = {
                                navegacao.navigate(route = "login")
                            }) {
                                Text(
                                    text = "Entrar",
                                    fontSize = 15.sp,
                                    color = Color(0xFF0C75FF)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CadastroPreview() {
    val navController = rememberNavController()
    Cadastro(navegacao = navController)
}