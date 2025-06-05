package com.example.planifyeventos.model

data class Evento(
    val titulo : String = "",
    val descricao :String = "",
    val data_evento :String = "",
    val horario :String = "",
    val local :String = "",
    val imagem :String = "",
    val limite_participante :Int = 0,
    val valor_ingresso :String = "",
    val id_usuario :Int = 0
)