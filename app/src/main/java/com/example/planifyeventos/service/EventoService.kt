package com.example.planifyeventos.service

import com.example.planifyeventos.model.Evento
import com.example.planifyeventos.model.ResultCategoria
import com.example.planifyeventos.model.ResultEstado
import com.example.planifyeventos.model.ResultEvento
import com.example.planifyeventos.model.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface EventoService {
    @GET("evento")
    fun listarEventos(): Call<ResultEvento>

    @POST("evento")
    @Headers("Content-Type: application/json")
    fun inserirEvento(@Body evento: Evento): Call<Evento>

    @GET("estado")
    fun listarEstados(): Call<ResultEstado>

    @GET("evento")
    fun listarTodosEventos(): Call<ResultEvento>

    @GET("categoria")
    fun listarCategorias(): Call<ResultCategoria>

    @GET("usuario/evento/{id}")
    fun listarEventoPorUsuario(@Path("id")id_usuario: Int): Call<Evento>
}