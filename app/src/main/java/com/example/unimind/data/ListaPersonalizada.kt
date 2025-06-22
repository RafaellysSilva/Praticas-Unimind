package com.example.unimind.data

import com.google.gson.annotations.SerializedName

data class ListaPersonalizada(
    @SerializedName("idLista") val idLista: Int,
    @SerializedName("idUsuario") val idUsuario: Int,
    @SerializedName("titulo") val titulo: String,
    @SerializedName("idCategoria") val idCategoria: Int,
    @SerializedName("fonte") val fonte: String?,
    @SerializedName("ano") val ano: Int?,
    @SerializedName("tempo") val tempo: Int?
)