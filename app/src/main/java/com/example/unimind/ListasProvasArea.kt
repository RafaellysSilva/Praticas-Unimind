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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.example.unimind.ui.theme.Azul
import com.example.unimind.ui.theme.Rosinha

data class ProvaItem(
    val id: Int,
    val titulo: String,
    val categoria: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListasProvasArea(navController: NavController) {

    // --- Lista de Dados de Exemplo ---
    val todasAsProvas = remember {
        listOf(
            ProvaItem(1, "Cálculo I", "Exatas"),
            ProvaItem(2, "História Antiga", "Humanas"),
            ProvaItem(3, "Biologia Celular", "Biológicas"),
            ProvaItem(4, "Álgebra Linear", "Exatas"),
            ProvaItem(5, "Redação", "Linguagens"),
            ProvaItem(6, "Química Orgânica", "Exatas"),
        )
    }

    // --- State para o Texto da Pesquisa ---
    var textoPesquisa by remember { mutableStateOf("") }

    // --- Lógica de Filtro da Lista ---
    val provasFiltradas = if (textoPesquisa.isBlank()) {
        todasAsProvas
    } else {
        todasAsProvas.filter {
            it.titulo.contains(textoPesquisa, ignoreCase = true) ||
                    it.categoria.contains(textoPesquisa, ignoreCase = true)
        }
    }


    UnimindTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Nude
        ) {
            // Header e Footer (presumindo que existam)
            // Header("Provas e listas")

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp), // Espaço para o header
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // --- Barra de Pesquisa Funcional ---
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 45.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = textoPesquisa,
                        onValueChange = { textoPesquisa = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Pesquisar por título ou categoria...") },
                        shape = RoundedCornerShape(50),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Vinho,
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Rosinha,
                        )
                    )

                    Button(
                        onClick = { navController.navigate("telaListaPersonalizadaCriar") },
                        modifier = Modifier.size(56.dp),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Vinho),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Adicionar Lista",
                            tint = White
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

                // --- Botão de Navegação ---
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3E0D1)),
                    shape = RoundedCornerShape(17.dp),
                    border = BorderStroke(2.dp, Color(0xFFBB8C94)),
                    onClick = { navController.navigate("telaFlashcardsPergunta") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 45.dp)
                        .height(50.dp)
                ) {
                    Text(
                        text = "Provas e listas já realizadas",
                        color = Color(0xFF9A575B),
                        fontSize = 17.sp
                    )
                }

                Spacer(Modifier.height(20.dp))

                // --- Grid Dinâmico e Filtrado ---
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 45.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 80.dp) // Espaço para o footer
                ) {
                    items(provasFiltradas) { prova ->
                        Button(
                            onClick = { /* Navegar para detalhes da prova, ex: navController.navigate("detalhes/${prova.id}") */ },
                            modifier = Modifier
                                .height(90.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Rosinha)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = prova.titulo,
                                    color = White,
                                    fontSize = 17.sp,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = prova.categoria,
                                    color = White,
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
            //Footer(navController)
        }
    }
}