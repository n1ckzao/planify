package com.example.planifyeventos.screens.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.planifyeventos.model.Evento
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.request.ImageRequest
import com.example.planifyeventos.R
import com.example.planifyeventos.utils.isImagemValida
import java.text.SimpleDateFormat
import java.util.*

fun formatarData(data: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = inputFormat.parse(data)
        if (date != null) outputFormat.format(date) else "Data inválida"
    } catch (e: Exception) {
        "Data inválida"
    }
}

fun formatarHora(hora: String?): String {
    if (hora.isNullOrBlank()) return "Hora inválida"

    val formatosEntrada = listOf(
        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        "yyyy-MM-dd'T'HH:mm:ss",
        "HH:mm:ss",
        "HH:mm",
    )
    val formatoSaida = SimpleDateFormat("HH:mm", Locale.getDefault())

    for (formato in formatosEntrada) {
        try {
            val sdfEntrada = SimpleDateFormat(formato, Locale.getDefault())
            sdfEntrada.timeZone = TimeZone.getTimeZone("UTC")
            sdfEntrada.isLenient = false
            val date = sdfEntrada.parse(hora)
            if (date != null) return formatoSaida.format(date)
        } catch (_: Exception) {}
    }

    return "Hora inválida"
}

@Composable
fun EventoCard(evento: Evento, onClick: () -> Unit = {}) {
    val imagemUrl = if (isImagemValida(evento.imagem)) evento.imagem else "https://via.placeholder.com/400x200"

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFADD8E6)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imagemUrl)
                    .crossfade(true)
                    .error(R.drawable.logo)
                    .build(),
                contentDescription = evento.titulo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Fit,
                error = null
            )
            Column(Modifier.padding(12.dp)) {
                Text(evento.titulo, style = MaterialTheme.typography.titleMedium)
                Text(evento.descricao, fontSize = 14.sp, maxLines = 2)
                Spacer(Modifier.height(8.dp))
                Text("Local: ${evento.local}", fontSize = 12.sp)
                Text("Estado: ${evento.nomeEstado ?: evento.id_estado}", fontSize = 12.sp)
                Text("Categoria: ${evento.nomeCategoria ?: evento.id_categoria}", fontSize = 12.sp)
                Text("Data: ${formatarData(evento.data_evento)} às ${formatarHora(evento.horario)}", fontSize = 12.sp)

                val valor = evento.valor_ingresso
                Text(
                    text = if (valor != null) "Valor: R$ %.2f".format(valor) else "Valor: R$ --",
                    fontSize = 12.sp
                )
            }
        }
    }
}