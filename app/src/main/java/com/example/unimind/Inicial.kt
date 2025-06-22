package com.example.unimind

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.unimind.ui.theme.Nude
import com.example.unimind.ui.theme.Rosinha
import com.example.unimind.ui.theme.UnimindTheme
import com.example.unimind.ui.theme.Vinho
import com.example.unimind.viewmodel.UsuarioViewModel

private data class HomeButtonInfo(
    val text: String,
    val iconRes: Int,
    val route: String
)

// Listas separadas para cada seção do layout
private val topButtons = listOf(
    HomeButtonInfo("Configurações", R.drawable.config, "telaConfiguracoes"),
    HomeButtonInfo("Calendário", R.drawable.calendario, "telaCalendario")
)

private val mainButtons = listOf(
    HomeButtonInfo("Flashcards", R.drawable.flashcards, "telaFlashcardsArea"),
    HomeButtonInfo("Listas e Provas", R.drawable.listasprovas, "telaListasProvasArea"),
    HomeButtonInfo("Competição", R.drawable.competicao, "telaCompeticaoCriar"),
    HomeButtonInfo("Estatísticas", R.drawable.estatisticas, "telaEstatisticas")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Inicial(navController: NavController, viewModel: UsuarioViewModel) {
    val usuario by viewModel.usuarioDetalhe
    val userName = usuario?.nome ?: "Usuário"

    UnimindTheme {
        Scaffold(
            containerColor = Nude,
            topBar = {
                HomeTopBar(userName = userName)
            },
            bottomBar = {
                Footer(navController = navController)
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp, vertical = 32.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()), // Permite rolagem se o conteúdo exceder a tela
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Linha superior com 2 botões
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    topButtons.forEach { buttonInfo ->
                        HomeButton(
                            buttonInfo = buttonInfo,
                            modifier = Modifier.weight(1f),
                            height = 130.dp, // Altura para os botões superiores
                            onClick = { navController.navigate(buttonInfo.route) }
                        )
                    }
                }

                // Coluna com os 4 botões principais
                mainButtons.forEach { buttonInfo ->
                    HomeButton(
                        buttonInfo = buttonInfo,
                        modifier = Modifier.fillMaxWidth(),
                        height = 110.dp, // Altura para os botões principais
                        onClick = { navController.navigate(buttonInfo.route) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopBar(userName: String) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bichinho),
                    contentDescription = "Mascote Unimind",
                    modifier = Modifier.size(50.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Olá, $userName",
                        color = Nude,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "Ícone do usuário",
                        tint = Nude,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Vinho)
    )
}

@Composable
private fun HomeButton(
    buttonInfo: HomeButtonInfo,
    modifier: Modifier = Modifier,
    height: Dp,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(height),
        shape = RoundedCornerShape(16.dp), // Botões mais quadrados
        colors = ButtonDefaults.buttonColors(containerColor = Rosinha)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = buttonInfo.iconRes),
                contentDescription = buttonInfo.text,
                tint = Color.Unspecified,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = buttonInfo.text,
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}