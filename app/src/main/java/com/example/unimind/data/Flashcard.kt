package com.example.unimind.data

import com.google.gson.annotations.SerializedName

data class Flashcard(
    @SerializedName("idFlashcard") val idFlashcard: Int,
    @SerializedName("idUsuario") val idUsuario: Int,
    @SerializedName("perguntaUsuario") val perguntaUsuario: String,
    @SerializedName("respostaUsuario") val respostaUsuario: String
)