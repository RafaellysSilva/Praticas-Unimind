package com.example.unimind

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
fun FlashcardsResposta(
    navController: NavController,
    pergunta: String,
    flashcardId: Int?,
    viewModel: FlashcardViewModel = viewModel(),
    usuarioViewModel: UsuarioViewModel = viewModel()
) {
    var resposta by remember { mutableStateOf("") }
    val flashcardDetalhe by viewModel.flashcardDetalhe
    val usuarioId = usuarioViewModel.usuarioDetalhe.value?.idUsuario
    val context = LocalContext.current

    // Preenche a resposta se estiver editando um flashcard existente
    LaunchedEffect(flashcardDetalhe) {
        if (flashcardId != null) {
            flashcardDetalhe?.let {
                resposta = it.respostaUsuario
            }
        }
    }

    UnimindTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Nude
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Resposta:",
                    color = Vinho,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                TextField(
                    value = resposta,
                    onValueChange = { resposta = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    placeholder = { Text("Digite ou cole a resposta aqui.\n*Resposta*", color = Nude.copy(alpha = 0.5f)) },
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Vinho,
                        unfocusedContainerColor = Vinho,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Nude,
                        focusedTextColor = Nude,
                        unfocusedTextColor = Nude
                    ),
                    textStyle = TextStyle(color = Nude, fontSize = 18.sp)
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Botões de Ação
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.size(width = 150.dp, height = 50.dp),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Rosinha)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Nude
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Voltar",
                            color = Nude,
                            fontSize = 18.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        if (resposta.isBlank()) {
                            Toast.makeText(context, "A resposta não pode estar vazia.", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        if (usuarioId == null) {
                            Toast.makeText(context, "Erro: Usuário não encontrado.", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        val flashcard = Flashcard(
                            idFlashcard = flashcardId ?: 0,
                            idUsuario = usuarioId,
                            perguntaUsuario = pergunta,
                            respostaUsuario = resposta
                        )

                        val onSucesso: () -> Unit = {
                            navController.navigate("telaFlashcardsArea") {
                                popUpTo("telaFlashcardsArea") { inclusive = true }
                            }
                        }

                        if (flashcardId == null) {
                            viewModel.criarFlashcard(flashcard, onSucesso)
                        } else {
                            viewModel.atualizarFlashcard(flashcard, onSucesso)
                        }
                    },
                    modifier = Modifier.size(width = 180.dp, height = 50.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Rosinha)
                ) {
                    Text(
                        text = if (flashcardId == null) "Terminar" else "Salvar",
                        color = Nude,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}