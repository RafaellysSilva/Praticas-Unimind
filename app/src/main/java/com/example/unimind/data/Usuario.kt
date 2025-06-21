package com.example.unimind.data

data class Usuario(
    val nome: String,
    val email: String,
    val senha: String,
    val idUsuario: Int? = null,
    val nivel: Int? = null
)