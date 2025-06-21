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
fun Cadastro(navController: NavController) {
    val viewModel: UsuarioViewModel = viewModel()
    val mensagem by viewModel.mensagem

    val inter = FontFamily(
        Font(R.font.inter)
    )
    val customFontFamily = FontFamily(
        Font(R.font.cute_letters) // Nome do arquivo sem a extensão .ttf ou .otf
    )
    UnimindTheme{
        //É o cabeçário da página
        Surface (
            modifier = Modifier.fillMaxSize(),
            color = Nude
        ){
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top
            ) {
                Box(
                    //mexer no alinhamento do texto e da imagem
                    Modifier
                        .height(95.dp)
                        .background(Vinho)
                        .fillMaxWidth(),
                    contentAlignment = Center

                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically, // Alinha os itens no centro verticalmente
                        horizontalArrangement = Arrangement.Center, // Centraliza na horizontal
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 15.dp, 0.dp, 0.dp)
                    ) {
                        Text(
                            text = "UNIMIND",
                            fontFamily = customFontFamily,
                            color = Nude,
                            fontSize = 50.sp,
                            /*modifier = Modifier
                                .padding(start = 10.dp)*/
                        )
                        Image(
                            painterResource(id = R.drawable.bichinho),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 5.dp)
                                .width(80.dp)
                                .height(80.dp)
                        )
                    }
                }
            }
            //é o corpo da página
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    Modifier
                        .height(650.dp)
                        .clip(RoundedCornerShape(topStart = 45.dp, topEnd = 50.dp))
                        .background(Nude)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column(/*modifier = Modifier.background(color = Color.Blue)*/) {
                        Text(
                            text = "Cadastro",
                            fontFamily = inter,
                            fontWeight = FontWeight.Bold,
                            fontSize = 50.sp,
                            //background = null,
                            color = Color(0xFF741C28),
                            modifier = Modifier.padding(0.dp, 40.dp, 0.dp, 0.dp)
                        )

                        var nome by remember { mutableStateOf("") }
                        var email by remember { mutableStateOf("") }
                        var senha by remember { mutableStateOf("") }
                        var senhaDnv by remember { mutableStateOf("") }

                        Spacer(modifier = Modifier.height(50.dp))
                        Text(text = "Digite seu nome:", fontSize = 20.sp,
                            /*modifier = Modifier. padding(0.dp, 40.dp, 0.dp, 0.dp)*/)
                        OutlinedTextField(
                            value = nome,
                            onValueChange = { nome = it },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedTextColor = Color.Black, // Cor do texto quando em foco
                                unfocusedTextColor = Color.Black,  // Cor do texto quando não está em foco
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Black,
                                cursorColor = Color.Black
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Digite seu email:", fontSize = 20.sp)
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedTextColor = Color.Black, // Cor do texto quando em foco
                                unfocusedTextColor = Color.Black,  // Cor do texto quando não está em foco
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Black,
                                cursorColor = Color.Black
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Digite sua senha:", fontSize = 20.sp)
                        OutlinedTextField(
                            value = senha,
                            onValueChange = { senha = it },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedTextColor = Color.Black, // Cor do texto quando em foco
                                unfocusedTextColor = Color.Black,  // Cor do texto quando não está em foco
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Black,
                                cursorColor = Color.Black
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Digite sua senha:", fontSize = 20.sp)
                        OutlinedTextField(
                            value = senhaDnv,
                            onValueChange = { senhaDnv = it },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedTextColor = Color.Black, // Cor do texto quando em foco
                                unfocusedTextColor = Color.Black,  // Cor do texto quando não está em foco
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Black,
                                cursorColor = Color.Black
                            )

                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        OutlinedButton(
                            onClick = {
                                if (senha == senhaDnv){
                                    if (nome.isBlank() || email.isBlank() || senha.isBlank() || senhaDnv.isBlank()) {
                                        viewModel.setMensagem("Preencha todos os campos")
                                        //avisar por um pop-up
                                    }
                                    else {
                                        viewModel.criarUsuario(nome, email, senha) {
                                            navController.navigate("telaInicial") {
                                                popUpTo("telaCadastro") { inclusive = true }
                                            }
                                        }
                                    }
                                }
                                else{
                                    //as senhas não coincidem
                                    viewModel.setMensagem("As senhas não coincidem")

                                }
                            },
                            border = BorderStroke(2.dp, Vinho),
                            modifier = Modifier.width(280.dp)
                        ) {
                            Text(text = "Cadastrar", color = Vinho, fontSize = 20.sp)
                        }
                        mensagem?.let {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = it, color = Color.Red)
                        }

                    }
                }
                Box(contentAlignment = Alignment.TopCenter,
                    modifier = Modifier.height(140.dp)) {
                    Column {
                        Row{
                            Text(
                                text = "Já tem uma conta?",
                                fontSize = 20.sp
                            )
                        }
                        Row{
                            OutlinedButton(
                                onClick = {navController.navigate("telaEntrar")},
                                border = null
                            ) {
                                Text(text = "   Faça login", color = Vinho, fontSize = 20.sp)
                                //esse text nao ta ficando no meio, por isso os espaços
                            }
                        }
                    }
                }
            }
        }
    }
}
