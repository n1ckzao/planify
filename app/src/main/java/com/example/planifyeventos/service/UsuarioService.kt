package com.example.planifyeventos.service

import com.example.planifyeventos.model.Result
import com.example.planifyeventos.model.Usuario
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UsuarioService {
    @GET("usuario")
    fun listAll(): Call<Result>

    @GET("usuario/{id}")
    fun findById(@Path("id") id:Int): Call<Usuario>
}