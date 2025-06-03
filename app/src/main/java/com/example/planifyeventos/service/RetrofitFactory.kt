package com.example.planifyeventos.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
    private val BASE_URL = "http://10.107.134.12:8080/v1/planify/"//no mac é 10.107.144.25, no windows é 10.107.134.12

    private val RETROFIT_FACTORY = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getUsuarioService(): UsuarioService{
        return RETROFIT_FACTORY
            .create(UsuarioService::class.java)
    }
}