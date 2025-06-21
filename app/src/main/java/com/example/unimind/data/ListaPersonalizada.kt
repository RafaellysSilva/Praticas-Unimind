package com.example.unimind.data

import com.google.gson.annotations.SerializedName

data class ListaPersonalizada(
    @SerializedName("idLista") val idLista: Int,
    @SerializedName("idUsuario") val idUsuario: Int,
    @SerializedName("idCategoria") val idCategoria: Int,
    @SerializedName("idFonte") val idFonte: Int?,
    @SerializedName("ano") val ano: Int?
)