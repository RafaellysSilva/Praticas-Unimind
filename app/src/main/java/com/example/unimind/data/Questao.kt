// com/example/unimind/data/Questao.kt
package com.example.unimind.data

import com.google.gson.annotations.SerializedName

data class Questao(
    @SerializedName("idQuestao") val idQuestao: Int,
    @SerializedName("ano") val ano: Int,
    @SerializedName("idCategoria") val idCategoria: Int,
    @SerializedName("fonte") val fonte: String,
    @SerializedName("questao") val pergunta: String,
    @SerializedName("resposta") val resposta: String
)