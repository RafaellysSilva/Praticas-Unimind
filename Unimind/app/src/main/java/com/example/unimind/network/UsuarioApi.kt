package com.example.unimind.network

import com.example.unimind.data.Usuario

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.DELETE


interface UsuarioApi {
    @GET("/clientes")
    suspend fun listarClientes(): List<Usuario>

    @GET("/clientes/{id}")
    suspend fun buscarCliente(@Path("id") id: Int): Usuario?

    @POST("/clientes")
    suspend fun criarCliente(@Body cliente: Usuario): Usuario

    @PUT("/clientes/{id}")
    suspend fun atualizarCliente(@Path("id") id: Int, @Body cliente: Usuario): retrofit2.Response<Unit>

    @DELETE("/clientes/{id}")
    suspend fun deletarCliente(@Path("id") id: Int): retrofit2.Response<Unit>
}