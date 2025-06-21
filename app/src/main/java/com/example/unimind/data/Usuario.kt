import com.google.gson.annotations.SerializedName

data class Usuario(
    @SerializedName("idUsuario") val idUsuario: Int,
    @SerializedName("nome") val nome: String,
    @SerializedName("email") val email: String,
    @SerializedName("senha") val senha: String,
    @SerializedName("idNivel") val idNivel: Int?,
    @SerializedName("acertosQuestoes") val acertosQuestoes: Int?,
    @SerializedName("errosQuestoes") val errosQuestoes: Int?,
    @SerializedName("tempoEstudo") val tempoEstudo: Int?,
    @SerializedName("competicoesRealizadas") val competicoesRealizadas: Int?
)