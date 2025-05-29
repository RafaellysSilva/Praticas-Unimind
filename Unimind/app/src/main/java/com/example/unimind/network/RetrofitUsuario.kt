package com.example.unimind.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUsuario {
    private const val BASE_URL = "http://100.112.58.246:5133/" // Use o endereço da sua API

    val instance: UsuarioApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UsuarioApi::class.java)
    }
}