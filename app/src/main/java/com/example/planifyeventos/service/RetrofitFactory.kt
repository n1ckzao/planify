package com.example.planifyeventos.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
    private val BASE_URL = "http://localhost:5050/v1/planify/"

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