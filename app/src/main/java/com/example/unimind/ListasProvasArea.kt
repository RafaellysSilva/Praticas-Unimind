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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.unimind.data.ListaPersonalizada
import com.example.unimind.ui.theme.Bege
import com.example.unimind.ui.theme.Nude
import com.example.unimind.ui.theme.Rosinha
import com.example.unimind.ui.theme.UnimindTheme
import com.example.unimind.ui.theme.Vinho
import com.example.unimind.viewmodel.ListaPersonalizadaViewModel
import com.example.unimind.viewmodel.UsuarioViewModel

@Composable
fun ListasPersonalizadasHeader() {
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
                text = "Listas Personalizadas",
                color = Nude,
                fontSize = 28.sp,
                modifier = Modifier.padding(bottom = 40.dp)
            )
        }
    }
}


@Composable
fun ListasProvasArea(navController: NavController, viewModel: ListaPersonalizadaViewModel, usuarioViewModel: UsuarioViewModel) {
    val usuario by usuarioViewModel.usuarioDetalhe
    val listas by viewModel.listas
    var textoPesquisa by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.listarListas()
    }

    val listasUsuario = listas.filter { it.idUsuario == usuario?.idUsuario }

    val listasFiltradas = if (textoPesquisa.isBlank()) {
        listasUsuario
    } else {
        listasUsuario.filter {
            it.fonte?.contains(textoPesquisa, ignoreCase = true) == true ||
                    it.titulo.contains(textoPesquisa)
        }
    }

    UnimindTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            ListasPersonalizadasHeader()

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

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 45.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(listasFiltradas) { lista ->
                            Button(
                                onClick = { navController.navigate("telaListasProvasResolucao/${lista.idLista}") },
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
                                        text = lista.titulo,
                                        color = Color.White,
                                        fontSize = 17.sp,
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