package com.example.planifyeventos.screens.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.planifyeventos.R
import com.example.planifyeventos.utils.isImagemValida

@Composable
fun UsuarioCard(
    nome: String = "Nome de usuário",
    email: String = "Email de usuário",
    senha: String = "Senha",
    data_nascimento: String = "2000-01-01",
    foto_perfil: String = ""
) {
    val imagemUrl = if (isImagemValida(foto_perfil)) foto_perfil else "https://via.placeholder.com/180"

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(650.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xaaffffff)),
        border = BorderStroke(
            width = 16.dp,
            color = Color(0xFF008EFF)
        )
    ) {
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ){
            Card(
                modifier = Modifier
                    .padding(top = 26.dp)
                    .size(180.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF037EF7)),
                shape = CircleShape
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imagemUrl)
                        .crossfade(true)
                        .error(R.drawable.pe)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            }
            Column (
                modifier = Modifier.height(300.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ){
                Spacer(modifier = Modifier.height(30.dp))
                Text(text = "Nome de usuário:")
                Text(text = nome)
                Spacer(modifier = Modifier.height(8.dp))
                Divider(modifier = Modifier.width(300.dp), color = Color.Black)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Meu email:")
                Text(text = email)
                Spacer(modifier = Modifier.height(8.dp))
                Divider(modifier = Modifier.width(300.dp), color = Color.Black)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Data de nascimento:")
                Text(text = formatarData(data_nascimento))
                Spacer(modifier = Modifier.height(8.dp))
                Divider(modifier = Modifier.width(300.dp), color = Color.Black)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Minha senha:")
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