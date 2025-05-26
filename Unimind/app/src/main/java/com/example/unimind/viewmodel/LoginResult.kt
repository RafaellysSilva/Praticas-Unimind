package com.example.unimind.viewmodel

sealed class LoginResult {
    object Sucesso : LoginResult()
    data class Erro(val mensagem: String) : LoginResult()
}
