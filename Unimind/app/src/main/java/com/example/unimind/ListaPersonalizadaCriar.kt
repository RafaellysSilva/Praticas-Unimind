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
import com.example.usuarioapp.viewmodel.UsuarioViewModel
import java.text.DateFormatSymbols
import java.util.Calendar
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.platform.LocalContext
import com.example.unimind.data.Usuario
import com.example.unimind.data.UsuarioConfig
import com.example.unimind.network.RetrofitUsuario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaPersonalizadaCriar(navController: NavController) {
    UnimindTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Nude
        ) {
            // Coluna principal para conter o cabeçalho, o conteúdo e o rodapé
            Column(
                modifier = Modifier.fillMaxSize() // Faz a Coluna preencher a tela inteira para posicionar corretamente o cabeçalho e o rodapé
            ) {
                // INÍCIO DA IMPLEMENTAÇÃO DO CABEÇALHO

                // Este Box atuará como o plano de fundo do cabeçalho
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp) // Define uma altura fixa para o cabeçalho
                        .background(Vinho), // Usa uma cor semelhante à imagem (vermelho escuro/vinho)
                    contentAlignment = Alignment.CenterStart // Alinha o conteúdo ao início (esquerda)
                ) {
                    Text(
                        text = "Criar Lista Personalizada",
                        color = Color.White, // Define a cor do texto para branco para contraste
                        fontSize = 20.sp, // Ajusta o tamanho da fonte conforme necessário
                        fontWeight = FontWeight.Bold, // Deixa o texto em negrito
                        modifier = Modifier.padding(start = 20.dp) // Adiciona um preenchimento da borda esquerda
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth() // A Column das preferências ocupa a largura total
                        .padding(top = 250.dp)
                ) {
                    Box(
                        Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(10.dp))

                    Row {
                        Text(
                            "Nome:",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(start = 35.dp, end = 55.dp, top = 3.dp)
                        )

                        var expanded by remember { mutableStateOf(false) }
                        var selecionada by remember { mutableStateOf("") }
                        val opcoes = listOf("Opção 1", "Opção 2", "Opção 3")
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            OutlinedTextField(
                                value = selecionada,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                modifier = Modifier
                                    .menuAnchor()
                                    .height(30.dp)
                                    .width(210.dp)
                            )

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                opcoes.forEach { opcao ->
                                    DropdownMenuItem(
                                        text = { Text(opcao) },
                                        onClick = {
                                            selecionada = opcao
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(10.dp))

                    Row {
                        Text(
                            "Categoria:",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(start = 35.dp, end = 23.dp, top = 3.dp)
                        )
                        var expanded by remember { mutableStateOf(false) }
                        var selecionada by remember { mutableStateOf("") }
                        val opcoes = listOf("Opção 1", "Opção 2", "Opção 3")
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            OutlinedTextField(
                                value = selecionada,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                modifier = Modifier
                                    .menuAnchor()
                                    .height(30.dp)
                                    .width(210.dp)
                            )

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                opcoes.forEach { opcao ->
                                    DropdownMenuItem(
                                        text = { Text(opcao) },
                                        onClick = {
                                            selecionada = opcao
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(10.dp))

                    Row {
                        Text(
                            "Fonte:",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(start = 35.dp, end = 55.dp, top = 3.dp)
                        )

                        var expanded by remember { mutableStateOf(false) }
                        var selecionada by remember { mutableStateOf("") }
                        val opcoes = listOf("Opção 1", "Opção 2", "Opção 3")
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            OutlinedTextField(
                                value = selecionada,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                modifier = Modifier
                                    .menuAnchor()
                                    .height(30.dp)
                                    .width(210.dp)
                            )

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                opcoes.forEach { opcao ->
                                    DropdownMenuItem(
                                        text = { Text(opcao) },
                                        onClick = {
                                            selecionada = opcao
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(10.dp))

                    Row {
                        Text(
                            "Ano:",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(start = 40.dp, end = 55.dp, top = 3.dp)
                        )

                        var expanded by remember { mutableStateOf(false) }
                        var selecionada by remember { mutableStateOf("") }
                        val opcoes = listOf("Opção 1", "Opção 2", "Opção 3")
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            OutlinedTextField(
                                value = selecionada,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                modifier = Modifier
                                    .menuAnchor()
                                    .height(30.dp)
                                    .width(210.dp)
                            )

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                opcoes.forEach { opcao ->
                                    DropdownMenuItem(
                                        text = { Text(opcao) },
                                        onClick = {
                                            selecionada = opcao
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(10.dp))

                    Row {
                        Text(
                            "Questões:",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(start = 35.dp, end = 25.dp, top = 3.dp)
                        )

                        var expanded by remember { mutableStateOf(false) }
                        var selecionada by remember { mutableStateOf("") }
                        val opcoes = listOf("Opção 1", "Opção 2", "Opção 3")
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            OutlinedTextField(
                                value = selecionada,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                modifier = Modifier
                                    .menuAnchor()
                                    .height(30.dp)
                                    .width(210.dp)
                            )

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                opcoes.forEach { opcao ->
                                    DropdownMenuItem(
                                        text = { Text(opcao) },
                                        onClick = {
                                            selecionada = opcao
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(30.dp))

                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3E0D1)),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Vinho),
                        onClick = { navController.navigate("") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 100.dp)
                            .height(38.dp)
                    ) {
                        Text(
                            text = "Criar",
                            color = Vinho,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(Modifier.height(30.dp))

                }
                Footer(navController)
            }
        }
    }
}