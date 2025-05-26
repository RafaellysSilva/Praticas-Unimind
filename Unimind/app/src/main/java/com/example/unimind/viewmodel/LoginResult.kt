package com.example.unimind.viewmodel


sealed class LoginResult {
    val mensagem: String
        get() {
            TODO()
        }

    object Sucesso : LoginResult()
    data class Erro(val mensagem: String) : LoginResult()
    object Nenhum : LoginResult()
}
