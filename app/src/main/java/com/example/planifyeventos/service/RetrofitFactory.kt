package com.example.planifyeventos.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
   private val BASE_URL = "http://10.107.144.18:3030/v1/planify/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val RETROFIT_FACTORY = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getUsuarioService(): UsuarioService {
        return RETROFIT_FACTORY.create(UsuarioService::class.java)
    }
    fun getEventoService(): EventoService{
        return RETROFIT_FACTORY.create(EventoService::class.java)
    }
}