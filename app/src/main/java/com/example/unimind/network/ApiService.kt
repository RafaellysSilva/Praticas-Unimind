import com.example.unimind.data.Competicao
import com.example.unimind.data.Flashcard
import com.example.unimind.data.ListaPersonalizada
import com.example.unimind.data.Questao
import com.example.unimind.data.UsuarioCadastro
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // Endpoints de Usuário
    @GET("/usuarios")
    suspend fun listarUsuarios(): List<Usuario>

    @GET("/usuarios/{id}")
    suspend fun buscarUsuarioPorId(@Path("id") id: Int): Usuario?

    @GET("/login/{user}/{password}")
    suspend fun buscarUsuario(
        @Path("user") nome: String,
        @Path("password") senha: String
    ): Usuario?

    @POST("/usuarios/add")
    suspend fun criarUsuario(@Body usuario: UsuarioCadastro): Usuario

    @PUT("/usuarios/{id}")
    suspend fun atualizarUsuario(@Path("id") id: Int, @Body usuario: Usuario): Response<Unit>

    @DELETE("/usuarios/del/{id}")
    suspend fun deletarUsuario(@Path("id") id: Int): Response<Unit>

// Endpoints de Questão

    @GET("/questoes")
    suspend fun listarQuestoes(): List<Questao>

    @GET("/questoes/{id}")
    suspend fun buscarQuestao(@Path("id") id: Int): Questao?

    @GET("/questoes/ano/{ano}")
    suspend fun listarQuestoesPorAno(@Path("ano") ano: Int): List<Questao>

    @GET("/questoes/categoria/{idCategoria}")
    suspend fun listarQuestoesPorCategoria(@Path("idCategoria") idCategoria: Int): List<Questao>

    @GET("/questoes/fonte/{fonte}")
    suspend fun listarQuestoesPorFonte(@Path("fonte") fonte: String): List<Questao>

    @POST("/questoes/add")
    suspend fun criarQuestao(@Body questao: Questao): Questao

    @PUT("/questoes/{id}")
    suspend fun atualizarQuestao(@Path("id") id: Int, @Body questao: Questao): Response<Unit>

    @DELETE("/questoes/del/{id}")
    suspend fun deletarQuestao(@Path("id") id: Int): Response<Unit>

    // Endpoints de Lista Personalizada
    @GET("/listas")
    suspend fun listarListasPersonalizadas(): List<ListaPersonalizada>

    @GET("/listas/{id}")
    suspend fun buscarListaPersonalizada(@Path("id") id: Int): ListaPersonalizada?

    @POST("/listas/add")
    suspend fun criarListaPersonalizada(@Body lista: ListaPersonalizada): ListaPersonalizada

    @PUT("/listas/{id}")
    suspend fun atualizarListaPersonalizada(@Path("id") id: Int, @Body lista: ListaPersonalizada): Response<Unit>

    @DELETE("/listas/del/{id}")
    suspend fun deletarListaPersonalizada(@Path("id") id: Int): Response<Unit>

    // Endpoints de Flashcard
    @GET("/flashcards")
    suspend fun listarFlashcards(): List<Flashcard>

    @GET("/flashcards/{id}")
    suspend fun buscarFlashcard(@Path("id") id: Int): Flashcard?

    @POST("/flashcards/add")
    suspend fun criarFlashcard(@Body flashcard: Flashcard): Flashcard

    @PUT("/flashcards/{id}")
    suspend fun atualizarFlashcard(@Path("id") id: Int, @Body flashcard: Flashcard): Response<Unit>

    @DELETE("/flashcards/del/{id}")
    suspend fun deletarFlashcard(@Path("id") id: Int): Response<Unit>

    // Endpoints de Competição
    @GET("/competicoes")
    suspend fun listarCompeticoes(): List<Competicao>

    @GET("/competicoes/{id}")
    suspend fun buscarCompeticao(@Path("id") id: Int): Competicao?

    @POST("/competicoes/add")
    suspend fun criarCompeticao(@Body competicao: Competicao): Competicao

    @PUT("/competicoes/{id}")
    suspend fun atualizarCompeticao(@Path("id") id: Int, @Body competicao: Competicao): Response<Unit>

    @DELETE("/competicoes/del/{id}")
    suspend fun deletarCompeticao(@Path("id") id: Int): Response<Unit>
}