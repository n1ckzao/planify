package com.example.planifyeventos.model

data class Result(
    val status: Boolean,
    val status_code: Int,
    val itens: Int,
    val usuario: List<Usuario>
)

data class ResultSenha(
    val status: Boolean,
    val status_code: Int,
    val message: String
)