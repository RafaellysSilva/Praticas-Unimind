package com.example.unimind.data

import com.google.gson.annotations.SerializedName

data class Questao(
    @SerializedName("idQuestao") val idQuestao: Int,
    @SerializedName("idProva") val idProva: Int,
    @SerializedName("idCategoria") val idCategoria: Int,
    @SerializedName("questao") val pergunta: String,
    @SerializedName("resposta") val resposta: String
)