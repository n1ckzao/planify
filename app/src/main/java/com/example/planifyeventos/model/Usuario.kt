package com.example.planifyeventos.model

class Usuario {
    data class Character(
        var id: Int = 0,
        var nome: String = "",
        var email: String = "",
        var senha: String = "",
        var data_nascimento: String = "",
        var palavra_chave: String = "",
        var foto_perfil: String = ""
    )
}