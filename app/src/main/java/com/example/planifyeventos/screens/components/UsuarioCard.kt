package com.example.planifyeventos.screens.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun UsuarioCard(
    nome: String = "Nome de usuário",
    email: String = "Email de usuário",
    senha: String = "Senha",
    data_nascimento: String = "Data de nascimento",
    palavra_chave: String = "Palavra chave",
    foto_perfil: String = "Url da imagem",
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(300.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xaaffffff))
    ) {
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .size(90.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Blue),
                shape = CircleShape
            ) {
                AsyncImage(
                    model = foto_perfil,
                    contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = nome)
                Text(text = email)
                Text(text = data_nascimento)
                Text(text = palavra_chave)
                Text(text = senha)
            }
        }
    }
}

@Preview
@Composable
private fun UsurioCardPreview() {
    UsuarioCard()
}