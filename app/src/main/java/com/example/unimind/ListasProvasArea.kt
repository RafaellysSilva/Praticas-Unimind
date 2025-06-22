package com.example.unimind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.unimind.ui.theme.Bege
import com.example.unimind.ui.theme.Nude
import com.example.unimind.ui.theme.Rosinha
import com.example.unimind.ui.theme.UnimindTheme
import com.example.unimind.ui.theme.Vinho

data class ProvaItem(
    val id: Int,
    val titulo: String,
    val categoria: String
)

@Composable
fun ListasProvasHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(bottomStart = 1500.dp, bottomEnd = 1500.dp))
                .background(Bege)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 25.dp)
                .clip(RoundedCornerShape(bottomStart = 1500.dp, bottomEnd = 1500.dp))
                .background(Rosinha)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 50.dp)
                .clip(RoundedCornerShape(bottomStart = 1500.dp, bottomEnd = 1500.dp))
                .background(Vinho),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Listas & Provas",
                color = Nude,
                fontSize = 28.sp,
                modifier = Modifier.padding(bottom = 40.dp)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListasProvasArea(navController: NavController) {

    val todasAsProvas = remember {
        listOf(
            ProvaItem(1, "Cálculo I", "Exatas"),
            ProvaItem(2, "História Antiga", "Humanas"),
            ProvaItem(3, "Biologia Celular", "Biológicas"),
            ProvaItem(4, "Álgebra Linear", "Exatas"),
            ProvaItem(5, "Redação", "Linguagens"),
            ProvaItem(6, "Química Orgânica", "Exatas"),
            ProvaItem(7, "Física II", "Exatas"),
            ProvaItem(8, "Literatura", "Linguagens"),
        )
    }

    var textoPesquisa by remember { mutableStateOf("") }

    val provasFiltradas = if (textoPesquisa.isBlank()) {
        todasAsProvas
    } else {
        todasAsProvas.filter {
            it.titulo.contains(textoPesquisa, ignoreCase = true) ||
                    it.categoria.contains(textoPesquisa, ignoreCase = true)
        }
    }

    UnimindTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            // Header personalizado fica no fundo
            ListasProvasHeader()

            // Scaffold gerencia o layout principal, incluindo o footer
            Scaffold(
                containerColor = Color.Transparent, // Fundo transparente para ver o header
                bottomBar = { Footer(navController) } // Footer no local correto
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding) // Aplica o padding para não sobrepor o footer
                        .padding(top = 180.dp), // Padding para o header personalizado
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

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
                            placeholder = { Text("Pesquisar...", color = Color.White) },
                            shape = RoundedCornerShape(50),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Vinho,
                                focusedContainerColor = Vinho,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Rosinha,
                                unfocusedTextColor = Color.White,
                                focusedTextColor = Color.White,
                                cursorColor = Color.White,
                                unfocusedPlaceholderColor = Color.White,
                                focusedPlaceholderColor = Color.White,
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
                                tint = Color.White,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Bege),
                        shape = RoundedCornerShape(17.dp),
                        border = BorderStroke(2.dp, Color.Transparent),
                        onClick = { /* TODO: Navegar para a tela de listas realizadas */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 45.dp)
                            .height(50.dp)
                    ) {
                        Text(
                            text = "Provas e listas já realizadas",
                            color = Vinho,
                            fontSize = 17.sp
                        )
                    }

                    Spacer(Modifier.height(20.dp))

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 45.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                        // Não é mais necessário padding de conteúdo no bottom,
                        // pois o Scaffold já cuida disso.
                    ) {
                        items(provasFiltradas) { prova ->
                            Button(
                                onClick = { navController.navigate("telaListasProvasResolucao") },
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
                                        color = Color.White,
                                        fontSize = 17.sp,
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = prova.categoria,
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListasProvasAreaPreview() {
    ListasProvasArea(navController = rememberNavController())
}