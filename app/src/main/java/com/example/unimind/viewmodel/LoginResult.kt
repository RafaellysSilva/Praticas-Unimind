package com.example.unimind.viewmodel


sealed class LoginResult {
    open val mensagem: String
        get() {
            TODO()
        }

    object Sucesso : LoginResult()
    data class Erro(override val mensagem: String) : LoginResult()
    object Nenhum : LoginResult()
}
