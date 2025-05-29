package com.example.planifyeventos.model


data class Usuario(
    val nome: String = "",
    val email: String = "",
    val senha: String = "",
    val data_nascimento: String = "",
    val palavra_chave: String = "",
    val foto_perfil: String = ""
)
