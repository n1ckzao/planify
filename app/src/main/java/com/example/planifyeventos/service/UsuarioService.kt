package com.example.planifyeventos.service

import com.example.planifyeventos.model.Result
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface UsuarioService {

    data class Usuario(
        val id: Int? = null,
        val nome: String,
        val email: String,
        val senha: String,
        val data_nascimento: String,
        val palavra_chave: String,
        val foto_perfil: String
    )

    @Headers("Content-Type: application/json")
    fun registerUsuaio(@Body user: com.example.planifyeventos.model.Usuario): retrofit2.Call<Usuario>

    @POST("usuario")
    fun inserirUsuario(@Body usuario: Usuario): Call<Usuario>

    @GET("usuario")
    fun listarUsuarios(): Call<Result>

    @GET("usuario/{id}")
    fun listarUsuarioPorId(@Path("id") id:Int): Call<Usuario>
}