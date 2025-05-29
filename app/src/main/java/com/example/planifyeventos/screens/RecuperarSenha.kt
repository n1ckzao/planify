package com.example.planifyeventos.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.planifyeventos.R
import com.example.planifyeventos.model.Result
import com.example.planifyeventos.service.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun RecuperarSenha(navegacao: NavHostController?) {
    val email = remember { mutableStateOf(TextFieldValue("")) }
    val palavraChave = remember { mutableStateOf(TextFieldValue("")) }
    val mensagem = remember { mutableStateOf<String?>(null) }
    val loading = remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .background(
                    Color.White,
                    shape = RoundedCornerShape(40.dp)
                )
                .border(
                    15.dp,
                    Color(0xFF037EF7),
                    shape = RoundedCornerShape(40.dp)
                )
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.width(230.dp).height(90.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Esqueceu a senha?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF037EF7)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Email field
            Text(text = "Digite seu email:", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                placeholder = { Text("example@gmail.com") },
                shape = RoundedCornerShape(30.dp),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(30.dp))
                    .background(Color(0xFFF2F9FF), RoundedCornerShape(30.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Palavra-chave field
            Text(text = "Palavra-chave:", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = palavraChave.value,
                onValueChange = { palavraChave.value = it },
                placeholder = { Text("exemplo123") },
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(30.dp),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(10.dp, RoundedCornerShape(30.dp))
                    .background(Color(0xFFF2F9FF), RoundedCornerShape(30.dp))
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    if (email.value.text.isBlank() || palavraChave.value.text.isBlank()) {
                        mensagem.value = "Por favor, preencha todos os campos"
                        return@Button
                    }
                    loading.value = true
                    mensagem.value = null

                    val call = RetrofitFactory().getUsuarioService().listarUsuarios()
                    call.enqueue(object : Callback<Result> {
                        override fun onResponse(call: Call<Result>, response: Response<Result>) {
                            loading.value = false
                            if (response.isSuccessful) {
                                val usuarios = response.body()?.usuario
                                val usuarioEncontrado = usuarios?.find {
                                    it.email == email.value.text && it.palavra_chave == palavraChave.value.text
                                }
                                if (usuarioEncontrado != null) {
                                    mensagem.value = "Sua senha é: ${usuarioEncontrado.senha}"
                                } else {
                                    mensagem.value = "Email ou palavra-chave incorretos"
                                }
                            } else {
                                mensagem.value = "Erro no servidor: ${response.code()}"
                            }
                        }

                        override fun onFailure(call: Call<Result>, t: Throwable) {
                            loading.value = false
                            mensagem.value = "Falha na conexão: ${t.message}"
                        }
                    })
                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .shadow(8.dp, RoundedCornerShape(50.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF037EF7),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = if (loading.value) "Carregando..." else "Recuperar Senha",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Display message
            mensagem.value?.let {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = it,
                    color = if (it.startsWith("Sua senha é")) Color(0xFF037EF7) else Color.Red,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun RecuperarSenhaPreview() {
    val fakeNavController = rememberNavController()
    RecuperarSenha(navegacao = fakeNavController)
}
