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
import java.text.DateFormatSymbols
import java.util.Calendar
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.platform.LocalContext
import com.example.unimind.data.UsuarioConfig
import com.example.unimind.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Entrar(navController: NavController, viewModel: UsuarioViewModel){

    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val cuteFont = FontFamily(
        Font(R.font.cute_letters) // Nome do arquivo sem a extensão .ttf ou .otf
    )

    val inter = FontFamily(
        Font(R.font.inter)
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
                            fontFamily = cuteFont,
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
                    Column {
                        Text(
                            text = "Login",
                            fontFamily = inter,
                            fontWeight = FontWeight.Bold,
                            fontSize = 50.sp,
                            color = Color(0xFF741C28),
                            modifier = Modifier.padding(0.dp, 40.dp, 0.dp, 0.dp)
                            //color = R.color.ic_launcher_background
                        )
                        Spacer(
                            modifier = Modifier.height(50.dp)
                        )

                        //permite que a caixa de texto mantenha e atualize o valor
                        //digitado pelo usuário dinamicamente.

                        Text(text = "Digite seu usuário:", fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(3.dp))
                        OutlinedTextField(
                            value = user,      //o valor q o usuário digitar será armazenado na var texto
                            onValueChange = { user = it },  //atualiza o valor da variável texto sempre que o usuário digitar algo.
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedTextColor = Color.Black, // Cor do texto quando em foco
                                unfocusedTextColor = Color.Black,  // Cor do texto quando não está em foco
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Black,
                                cursorColor = Color.Black
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(text = "Digite sua senha:", fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(3.dp))
                        OutlinedTextField(
                            value = password,
                            onValueChange = {password = it},
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedTextColor = Color.Black, // Cor do texto quando em foco
                                unfocusedTextColor = Color.Black,  // Cor do texto quando não está em foco
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Black,
                                cursorColor = Color.Black
                            )

                        )

                        Spacer(modifier = Modifier.height(50.dp))

                        //TEM QUE COLOCAR A FUNÇÃO VERIFICAR LOGIN AQUI NESSE BOTÃO
                        val context = LocalContext.current

                        OutlinedButton (
                            onClick = {
                                if (user.isNotEmpty() && password.isNotEmpty()) {
                                    viewModel.verificarLogin(user, password)
                                }
                            },
                            border = BorderStroke(2.dp, Vinho),
                            modifier = Modifier
                                .width(280.dp)
                        )
                        {
                            Text(text = "Entrar",
                                color = Vinho,
                                fontSize = 20.sp)
                        }

                        when (val status = viewModel.loginStatus.value) {
                            is LoginResult.Sucesso -> {
                                LaunchedEffect(Unit) {
                                    viewModel.limparLoginStatus()
                                    navController.navigate("telaInicial") {
                                        popUpTo("telaEntrar") { inclusive = true }
                                        popUpTo("telaBloqueio") { inclusive = true }
                                    }
                                }
                            }

                            is LoginResult.Erro -> {
                                viewModel.setMensagem("O usuário e/ou a senha está(ão) incorreto(s) ")
                                AlertDialog(
                                    onDismissRequest = { viewModel.limparLoginStatus() },
                                    title = { Text("Erro") },
                                    text = { Text(status.mensagem + "\nUsuário e/ou senha incorreto(s)") },
                                    confirmButton = {
                                        TextButton(onClick = { viewModel.limparLoginStatus() }) {
                                            Text("OK")
                                        }
                                    }
                                )
                            }

                            LoginResult.Nenhum -> {
                                // Nada visível
                            }
                        }


                    }

                }
                Box(contentAlignment = Alignment.TopCenter,
                    modifier = Modifier.height(140.dp)) {
                    Column {
                        Row{
                            Text(
                                text = "Não tem uma conta?",
                                fontSize = 20.sp
                            )
                        }
                        Row{
                            OutlinedButton(
                                onClick = {navController.navigate("telaCadastro")},
                                border = null
                            ) {
                                Text(text = "   Cadastre-se", color = Vinho, fontSize = 20.sp)
                                //esse text nao ta ficando no meio, por isso os espaços
                            }
                        }
                    }
                }
            }
        }
    }
}