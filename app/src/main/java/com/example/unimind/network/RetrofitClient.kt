package com.example.unimind.network

import ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    // O URL base da sua API, conforme fornecido
    private const val BASE_URL = "https://unimindapi.onrender.com/"

    // Configuração do OkHttpClient com timeouts para maior robustez
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    // Criação da instância do Retrofit usando lazy initialization
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Instância única e global do seu ApiService
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}