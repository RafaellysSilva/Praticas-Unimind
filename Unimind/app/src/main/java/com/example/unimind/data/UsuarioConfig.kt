package com.example.unimind.data

data class UsuarioConfig(
    val nome: String,
    val email: String,
    val senha: String,
    val nivel: Int? = null,
    val idUsuario: Int? = null
)