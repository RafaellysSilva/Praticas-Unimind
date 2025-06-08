package com.example.unimind.network

import UsuarioCadastro
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

    @GET("/login/{user}/{password}")
    suspend fun buscarUsuario(
        @Path("user") nome: String,
        @Path("password") senha: String
    ): Usuario?

    @POST("/usuarios/add")
    suspend fun criarUsuario(@Body usuario: UsuarioCadastro): Usuario

    @PUT("/usuarios/{id}")
    suspend fun atualizarUsuario(@Path("id") id: Int, @Body usuario: Usuario): retrofit2.Response<Unit>

    @DELETE("/usuarios/del/{id}")
    suspend fun deletarUsuario(@Path("id") id: Int): retrofit2.Response<Unit>
}