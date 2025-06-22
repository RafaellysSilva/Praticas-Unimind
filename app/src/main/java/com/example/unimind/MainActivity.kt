package com.example.unimind

import com.example.unimind.viewmodel.LoginResult
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.unimind.ui.theme.Bege
import com.example.unimind.ui.theme.Nude
import com.example.unimind.ui.theme.UnimindTheme
import com.example.unimind.ui.theme.Vinho
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.unimind.data.Flashcard
import com.example.unimind.ui.theme.Rosinha
import com.example.unimind.viewmodel.FlashcardViewModel
import com.example.unimind.viewmodel.UsuarioViewModel

//import com.example.usuarioapp.viewmodel.LoginResult
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context
// Tipo de resultado do login

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            val usuarioViewModel: UsuarioViewModel = viewModel()
            val flashcardViewModel: FlashcardViewModel = viewModel()

            UnimindTheme {
                AppNavigation(navController, usuarioViewModel, flashcardViewModel)
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController, usuarioViewModel: UsuarioViewModel, flashcardViewModel: FlashcardViewModel) {
    NavHost(navController = navController, startDestination = "telaBloqueio") {
        composable("telaBloqueio") { Bloqueio(navController) }
        composable("telaCadastro") { Cadastro(navController, usuarioViewModel) }
        composable("telaEntrar") { Entrar(navController, usuarioViewModel) }
        composable("telaInicial") { Inicial(navController, usuarioViewModel) }
        composable("telaConfiguracoes") { Configuracoes(navController, usuarioViewModel) }
        composable("telaFlashcardsArea") { FlashcardsArea(navController, flashcardViewModel) }
        composable("telaFlashcardsPergunta") { FlashcardsPergunta(navController) }
        composable("telaFlashcardsResposta") { FlashcardsResposta(navController) }
        composable("telaListasProvasArea") { ListasProvasArea(navController) }
        composable("telaListasProvasResolucao") { ListasProvasResolucao(navController) }
        composable("telaListaPersonalizadaCriar") { ListaPersonalizadaCriar(navController) }
        composable("telaCompeticaoCriar") { CompeticaoCriar(navController) }
        composable("telaCompeticaoMomento") { CompeticaoMomento(navController) }
        composable("telaCompeticaoRelatorio") { CompeticaoRelatorio(navController) }
        composable("telaEstatisticas") { Estatisticas(navController) }
        composable("telaCalendario") { Calendario(navController) }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AppPreview() {
    Bloqueio(rememberNavController())
}