package com.example.planifyeventos.service

import com.example.planifyeventos.model.Evento
import com.example.planifyeventos.model.Result
import com.example.planifyeventos.model.ResultSenha
import com.example.planifyeventos.model.SenhaRequest
import com.example.planifyeventos.model.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsuarioService {
    @Headers("Content-Type: application/json")

    @POST("usuario")
    fun inserirUsuario(@Body usuario: Usuario): Call<Usuario>

    @GET("usuario")
    fun listarUsuarios(): Call<Result>

    @GET("usuario/{id}")
    fun listarUsuarioPorId(@Path("id") id: Int): Call<Usuario>

    // Função para alterar a senha

    @PUT("usuario/senha/{id}")
    @Headers("Content-Type: application/json")
    fun atualizarSenha(
        @Path("id") id: Int,
        @Body senhaRequest: SenhaRequest
    ): Call<ResultSenha>
}

interface EventoService {
    @POST("evento")
    @Headers("Content-Type: application/json")
    fun inserirEvento(@Body evento: Evento): Call<Evento>
}