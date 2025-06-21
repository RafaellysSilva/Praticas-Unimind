package com.example.unimind.data

import com.google.gson.annotations.SerializedName

data class Prova(
    @SerializedName("idProva") val idProva: Int,
    @SerializedName("idFonte") val idFonte: Int,
    @SerializedName("ano") val ano: Int,
    @SerializedName("qntdQuestoes") val qntdQuestoes: Int,
    @SerializedName("fase") val fase: String?
)