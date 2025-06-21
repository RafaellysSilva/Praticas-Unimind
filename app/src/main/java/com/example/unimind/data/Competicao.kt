package com.example.unimind.data
import com.google.gson.annotations.SerializedName

data class Competicao(
    @SerializedName("idCompeticao") val idCompeticao: Int,
    @SerializedName("data") val data: String,
    @SerializedName("idUsuario1") val idUsuario1: Int,
    @SerializedName("idUsuario2") val idUsuario2: Int,
    @SerializedName("idNivel") val idNivel: Int
)
