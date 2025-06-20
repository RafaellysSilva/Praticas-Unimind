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
import androidx.compose.material3.Icon
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.unimind.ui.theme.Bege
import com.example.unimind.ui.theme.Nude
import com.example.unimind.ui.theme.UnimindTheme
import com.example.unimind.ui.theme.Vinho
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.unimind.ui.theme.Rosinha

//import com.example.usuarioapp.viewmodel.LoginResult
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context
// Tipo de resultado do login

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            UnimindTheme {
                AppNavigation(navController)
            }
        }
    }
}

object Compartilhado {
    var idUserLogado: Int? = null
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "telaBloqueio") {
        composable("telaBloqueio") { Bloqueio(navController) }
        composable("telaCadastro") { Cadastro(navController) }
        composable("telaEntrar") { Entrar(navController) }
        composable("telaInicial") { Inicial(navController) }
        composable("telaConfiguracoes") { Configuracoes(navController) }
        composable("telaFlashcardsArea") { FlashcardsArea(navController) }
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

//função para chamar (tem várias telas que tem a mesma parte como o footer, fica mais facil assim)
@Composable
fun Header(nomePagina: String){
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            Modifier
                .height(220.dp)
                .clip(RoundedCornerShape(bottomStart = 1500.dp, bottomEnd = 1500.dp))
                .background(Bege)
                .fillMaxWidth()
        ) {
            Box(
                Modifier
                    .height(190.dp)
                    .clip(RoundedCornerShape(bottomStart = 1500.dp, bottomEnd = 1500.dp))
                    .background(Rosinha)
                    .fillMaxWidth()
            ) {
                Box(
                    Modifier
                        .height(160.dp)
                        .clip(RoundedCornerShape(bottomStart = 1500.dp, bottomEnd = 1500.dp))
                        .background(Vinho)
                        .fillMaxWidth(),
                    contentAlignment = Center
                ) {
                    Text(
                        text = nomePagina,
                        color = Nude,
                        fontSize = 20.sp,
                    )
                }
            }
        }
    }
}

@Composable
fun Footer(navController: NavController) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 50.dp),
        verticalArrangement = Arrangement.Bottom,
    ) {
        Box(
            contentAlignment = Center
        ) {
            Row(
                Modifier
                    .height(55.dp)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(Vinho),
            ) {
                Image(
                    painterResource(id = R.drawable.flashcards),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable(onClick = {navController.navigate("telaFlashcardsArea")})
                )

                Spacer(
                    Modifier
                        .padding(start = 10.dp)
                )

                Image(
                    painterResource(id = R.drawable.listasprovas),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable(onClick = {navController.navigate("telaListasProvasArea")})
                )

                Spacer(
                    Modifier
                        .padding(start = 10.dp)
                )

                Image(
                    painterResource(id = R.drawable.competicao),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable(onClick = {navController.navigate("telaCompeticaoCriar")})
                )

                Spacer(
                    Modifier
                        .padding(start = 10.dp)
                )

                Image(
                    painterResource(id = R.drawable.estatisticas),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable(onClick = { navController.navigate("telaEstatisticas") })
                )

                Spacer(
                    Modifier
                        .padding(start = 10.dp)
                )

                Image(
                    painterResource(id = R.drawable.bichinho),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable(onClick = {navController.navigate("telaInicial")})
                )
            }
        }
    }
}

@Composable
fun CompeticaoHeader() {
    @Composable
    fun HeaderUserProfile(color: Color, name: String) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .weight(1f)
                    .clip(CircleShape),
                contentAlignment = Center
            ) {
                Icon(
                    modifier = Modifier.size(80.dp),
                    painter = painterResource(R.drawable.fotouser2),
                    contentDescription = null,
                    tint = Color.Unspecified,
                )
            }

            Text(
                text = name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = color,
                modifier = Modifier
                    .padding(vertical = 6.dp)
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
            .background(Color(0xFFF3E0D1)),
        contentAlignment = Center
    ) {
        Image(
            painter = painterResource(R.drawable.em_cima_versus__1___1_),
            contentDescription = "Imagem de fundo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(65.dp),
        ) {

            HeaderUserProfile(color = Color(0xFF741C28), "Nome 1")

            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF741C28),
                            fontFamily = FontFamily.Serif
                        )
                    ) {
                        append("V")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFFFFF1E7),
                            fontFamily = FontFamily.Serif
                        )
                    ) {
                        append("S")
                    }
                },
                fontSize = 50.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.offset(x = (-7).dp)
            )

            HeaderUserProfile(color = Color(0xFFFFF1E7), name = "Nome 2")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AppPreview() {
    Bloqueio(rememberNavController())
}