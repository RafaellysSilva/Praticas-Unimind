package com.example.unimind

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.unimind.data.Flashcard
import com.example.unimind.ui.theme.Nude
import com.example.unimind.ui.theme.Rosinha
import com.example.unimind.ui.theme.UnimindTheme
import com.example.unimind.ui.theme.Vinho
import com.example.unimind.viewmodel.FlashcardViewModel
import com.example.unimind.viewmodel.UsuarioViewModel

@Composable
fun FlashcardsArea(navController: NavController, viewModel: FlashcardViewModel, usuarioViewModel: UsuarioViewModel) {
    // Carrega a lista de flashcards quando a tela é iniciada
    LaunchedEffect(Unit) {
        viewModel.listarFlashcards()
    }

    val usuario by usuarioViewModel.usuarioDetalhe
    val flashcards by viewModel.flashcards
    var textoPesquisa by remember { mutableStateOf("") }

    val flashcardsUsuario = remember(flashcards){
        flashcards.filter {
            it.idUsuario == usuario?.idUsuario
        }
    }

    val flashcardsFiltrados = remember(textoPesquisa, flashcardsUsuario) {
        if (textoPesquisa.isBlank()) {
            flashcardsUsuario
        } else {
            flashcardsUsuario.filter {
                it.perguntaUsuario.contains(textoPesquisa, ignoreCase = true)
            }
        }
    }

    UnimindTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Nude)
        ) {
            Header("Flashcards")

            Scaffold(
                containerColor = Color.Transparent,
                bottomBar = { Footer(navController) }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(top = 180.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Barra de Pesquisa e Botão Adicionar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedTextField(
                            value = textoPesquisa,
                            onValueChange = { textoPesquisa = it },
                            modifier = Modifier.weight(1f),
                            placeholder = { Text("Pesquisar...", color = Nude) },
                            shape = RoundedCornerShape(50),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Vinho,
                                focusedContainerColor = Vinho,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Rosinha,
                                unfocusedTextColor = Nude,
                                focusedTextColor = Nude,
                                cursorColor = Nude,
                                unfocusedPlaceholderColor = Nude,
                                focusedPlaceholderColor = Nude,
                            )
                        )

                        Button(
                            onClick = { navController.navigate("telaFlashcards/-1") },
                            modifier = Modifier.size(56.dp),
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = Vinho),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Adicionar Flashcard",
                                tint = Nude,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    // Grade de Flashcards
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(flashcardsFiltrados) { flashcard ->
                            FlashcardItem(flashcard = flashcard) {
                                // Navega para a pergunta passando o ID do flashcard
                                navController.navigate("telaFlashcards/${flashcard.idFlashcard}")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FlashcardItem(flashcard: Flashcard, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Rosinha)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = flashcard.perguntaUsuario,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 2 // Limita o título a 2 linhas
            )
            Text(
                text = "Card!",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}