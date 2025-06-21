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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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

@Composable
fun ListasProvasResolucao(navController: NavController) {
    UnimindTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Nude
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Parte superior fixa (Cabeçalho e Cronômetro)
                Column(
                    modifier = Modifier
                        .background(Nude)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        Modifier
                            .height(60.dp)
                            .fillMaxWidth(),
                        contentAlignment = Center
                    ) {
                        Text(
                            text = "Prova A - Conteúdo",
                            color = Vinho,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // Conteúdo principal scrollável (incluindo os botões)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                        .background(Vinho)
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(50.dp))

                    // Caixa da questão
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .background(Nude, shape = RoundedCornerShape(16.dp))
                            .padding(14.dp)
                    ) {
                        Text("Qual é a capital da França?")
                    }


                    Spacer(modifier = Modifier.height(16.dp))


                    // Caixa das alternativas
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .background(Nude, shape = RoundedCornerShape(20.dp))
                            .padding(15.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            listOf("Paris", "Brasília", "Moçambique", "Judiaí", "Genova").forEach { alternativa ->
                                Button(
                                    onClick = { /* Ação ao clicar */ },
                                    colors = ButtonDefaults.buttonColors(containerColor = White),
                                    shape = RoundedCornerShape(20.dp),
                                    border = BorderStroke(1.dp, Color(0xFFB9B9B9)),
                                    contentPadding = PaddingValues(0.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(35.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 16.dp),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        Text(
                                            text = alternativa,
                                            color = Black,
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                        }
                    }


                    Spacer(modifier = Modifier.height(32.dp))


                    // Botões de navegação (ESPAÇAMENTO E TAMANHO DAS SETAS AJUSTADOS)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Botão esquerdo (questão anterior)
                        Button(
                            onClick = { /* Anterior */ },
                            colors = ButtonDefaults.buttonColors(Nude),
                            modifier = Modifier.height(50.dp),
                            contentPadding = PaddingValues(all = 8.dp)
                        ) {
                            Box(modifier = Modifier.size(24.dp), contentAlignment = Center) {
                                Image(
                                    painterResource(id = R.drawable.baseline_arrow_forward_ios_vinho),
                                    contentDescription = null,
                                    modifier = Modifier.graphicsLayer { scaleX = -1f }
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Questão anterior",
                                color = Vinho
                            )
                        }


                        Spacer(modifier = Modifier.width(16.dp)) // Espaço entre os botões


                        // Botão direito (próxima questão)
                        Button(
                            onClick = { /* Próxima */ },
                            colors = ButtonDefaults.buttonColors(Nude),
                            modifier = Modifier.height(50.dp),
                            contentPadding = PaddingValues(all = 8.dp)
                        ) {
                            Text(
                                text = "Próxima questão",
                                color = Vinho
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(modifier = Modifier.size(24.dp), contentAlignment = Center) {
                                Image(
                                    painterResource(id = R.drawable.baseline_arrow_forward_ios_vinho),
                                    contentDescription = null
                                )
                            }
                        }
                    }


                    Spacer(modifier = Modifier.height(8.dp))


                    // Botão Terminar
                    Button(
                        onClick = { /* Terminar prova */ },
                        colors = ButtonDefaults.buttonColors(Nude),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 120.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = "Terminar",
                            color = Vinho,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }


                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
