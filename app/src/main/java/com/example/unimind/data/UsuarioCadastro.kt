package com.example.unimind.data

import com.google.gson.annotations.SerializedName

data class UsuarioCadastro(
    @SerializedName("nome") val nome: String,
    @SerializedName("email") val email: String,
    @SerializedName("senha") val senha: String
)