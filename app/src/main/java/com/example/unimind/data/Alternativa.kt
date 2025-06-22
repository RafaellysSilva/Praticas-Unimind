package com.example.unimind.data

import com.google.gson.annotations.SerializedName

data class Alternativa(
    @SerializedName("idAlternativa")
    val idAlternativa: Int,

    @SerializedName("texto")
    val texto: String,

    @SerializedName("correta")
    val correta: Boolean,

    @SerializedName("idQuestao")
    val idQuestao: Int
)