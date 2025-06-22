package com.example.unimind

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
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
import com.example.unimind.ui.theme.Nude
import com.example.unimind.ui.theme.UnimindTheme
import com.example.unimind.ui.theme.Vinho
import com.example.unimind.viewmodel.FlashcardViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun FlashcardsPergunta(
    navController: NavController,
    flashcardId: Int?,
    viewModel: FlashcardViewModel
) {
    var pergunta by remember { mutableStateOf("") }
    val flashcardDetalhe by viewModel.flashcardDetalhe
    val context = LocalContext.current

    LaunchedEffect(flashcardId) {
        if (flashcardId != null) {
            viewModel.buscarFlashcardPorId(flashcardId)
        } else {
            viewModel.limparDetalhe()
        }
    }

    LaunchedEffect(flashcardDetalhe) {
        flashcardDetalhe?.let {
            pergunta = it.perguntaUsuario
        }
    }

    UnimindTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Vinho
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
                    text = "Pergunta:",
                    color = Nude,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                TextField(
                    value = pergunta,
                    onValueChange = { pergunta = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    placeholder = { Text("Digite ou cole a pergunta aqui.\n*Pergunta*", color = Vinho.copy(alpha = 0.5f)) },
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Nude,
                        unfocusedContainerColor = Nude,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Vinho
                    ),
                    textStyle = TextStyle(color = Vinho, fontSize = 18.sp)
                )

                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    onClick = {
                        if (pergunta.isNotBlank()) {
                            val perguntaCodificada = URLEncoder.encode(pergunta, StandardCharsets.UTF_8.toString())
                            val rota = "telaFlashcardsResposta?pergunta=$perguntaCodificada" +
                                    (flashcardId?.let { "&flashcardId=$it" } ?: "")
                            navController.navigate(rota)
                        } else {
                            Toast.makeText(context, "Por favor, insira uma pergunta.", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.width(200.dp).height(60.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Nude)
                ) {
                    Text(
                        text = "Resposta",
                        color = Vinho,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Avançar",
                        tint = Vinho
                    )
                }
            }
        }
    }
}