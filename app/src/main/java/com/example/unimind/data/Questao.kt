package com.example.unimind.data

import com.google.gson.annotations.SerializedName

data class Questao(
    @SerializedName("idQuestao")
    val id: Int,

    @SerializedName("ano")
    val ano: Int,

    @SerializedName("idCategoria")
    val idCategoria: Int,

    @SerializedName("fonte")
    val fonte: String,

    @SerializedName("pergunta")
    val pergunta: String,

    @SerializedName("alternativas")
    val alternativas: List<Alternativa>
)