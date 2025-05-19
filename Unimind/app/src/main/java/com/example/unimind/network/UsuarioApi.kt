package com.example.unimind.network

import com.example.unimind.data.Usuario

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.DELETE


interface UsuarioApi {
    @GET("/usuarios")
    suspend fun listarUsuarios(): List<Usuario>

    @GET("/usuarios/{id}")
    suspend fun buscarUsuario(@Path("nome, senha") nome: String, senha: String): Usuario?

    @POST("/usuarios")
    suspend fun criarUsuario(@Body usuario: Usuario): Usuario

    @PUT("/usuarios/{id}")
    suspend fun atualizarUsuario(@Path("id") id: Int, @Body usuario: Usuario): retrofit2.Response<Unit>

    @DELETE("/usuarios/{id}")
    suspend fun deletarUsuario(@Path("id") id: Int): retrofit2.Response<Unit>
}