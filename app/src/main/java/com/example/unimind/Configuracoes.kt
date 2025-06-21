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
import java.util.Calendar
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.platform.LocalContext
import com.example.unimind.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Configuracoes(navController: NavController, viewModel: UsuarioViewModel) {
    val usuario by viewModel.usuarioDetalhe

    var user by remember(usuario) { mutableStateOf(usuario?.nome ?: "") }
    var email by remember(usuario) { mutableStateOf(usuario?.email ?: "") }
    var senha by remember(usuario) { mutableStateOf("") }
    var nivel by remember(usuario) { mutableStateOf(usuario?.idNivel?.toString() ?: "") }

    LaunchedEffect(viewModel.usuarioDetalhe.value) {
        viewModel.usuarioDetalhe.value?.let { usuario ->
            user = usuario.nome
            email = usuario.email
            senha = usuario.senha
            nivel = usuario.idNivel.toString()
        }
    }

    val inter = FontFamily(
        Font(R.font.inter)
    )

    UnimindTheme {
        //É o cabeçário da página
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Nude
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                //horizontalAlignment = Alignment.CenterHorizontally
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
                        //horizontalArrangement = Arrangement.Center, // Centraliza na horizontal
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 15.dp, 0.dp, 0.dp)
                    ) {
                        Image(
                            painterResource(id = R.drawable.bichinho),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 5.dp, end = 170.dp)
                                .width(50.dp)
                                .height(50.dp)
                        )
                        Text(
                            text = "Configurações",
                            fontFamily = inter,
                            color = White,
                            fontSize = 20.sp,
                            /*modifier = Modifier
                                .padding(start = 10.dp)*/
                        )
                        Image(
                            painterResource(id = R.drawable.engranagem),
                            contentDescription = null,
                            modifier = Modifier
                                .height(30.dp)
                                .width(30.dp)
                                .padding(start = 10.dp)
                        )
                    }
                    Box(
                        Modifier
                            .padding(top = 40.dp)
                            .fillMaxWidth()
                    ) {
                        Image(
                            painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = null,
                            modifier = Modifier
                                .clip(shape = CircleShape)
                                .align(BottomCenter)
                                .background(Blue)
                        )
                    }
                }
                //corpo da tela
                Spacer(Modifier.height(70.dp))
                Box{
                    Column{
                        Text(text = "Perfil",
                            fontSize = 23.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFf3d1c2))
                                .padding(top = 5.dp, bottom = 5.dp, start = 10.dp)
                        )
                        Spacer(Modifier.height(15.dp))

                        Row{
                            Text("Usuário:", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(start = 10.dp)
                            )
                            OutlinedTextField(
                                value = user,
                                onValueChange = {user = it},
                                //modifier = Modifier
                                //.width(280.dp)
                                //.height(10.dp)
                                //.padding(start = 20.dp, top = 10.dp),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedTextColor = Color.Black, // Cor do texto quando em foco
                                    unfocusedTextColor = Color.Black,  // Cor do texto quando não está em foco
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Black,
                                    cursorColor = Color.Black
                                )
                            )
                        }

                        Spacer(Modifier.height(15.dp))

                        Row{
                            Text("E-mail:", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 12.dp)
                            )
                            OutlinedTextField(
                                value = email,
                                onValueChange = {email = it},
                                //modifier = Modifier
                                //  .width(280.dp)
                                //.height(10.dp)
                                //.padding(start = 20.dp, top = 10.dp),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedTextColor = Color.Black, // Cor do texto quando em foco
                                    unfocusedTextColor = Color.Black,  // Cor do texto quando não está em foco
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Black,
                                    cursorColor = Color.Black
                                )
                            )
                        }

                        Spacer(Modifier.height(15.dp))

                        Row{
                            Text("Senha:", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 14.dp)
                            )
                            OutlinedTextField(
                                value = senha,
                                onValueChange = {senha = it},
                                //modifier = Modifier
                                //  .width(280.dp)
                                //.height(10.dp)
                                //.padding(start = 20.dp, top = 10.dp),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedTextColor = Color.Black, // Cor do texto quando em foco
                                    unfocusedTextColor = Color.Black,  // Cor do texto quando não está em foco
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Black,
                                    cursorColor = Color.Black
                                )
                            )
                        }

                        Spacer(Modifier.height(15.dp))

                        Row{
                            Text("Nível:", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 24.dp)
                            )
                            OutlinedTextField(
                                value = nivel,
                                onValueChange = { novoValor -> nivel = novoValor },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Black,
                                    cursorColor = Color.Black
                                )
                            )
                        }

                        Spacer(Modifier.height(20.dp))
                        Text(text = "Outros",
                            fontSize = 23.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFf3d1c2))
                                .padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
                        )

                        Spacer(Modifier.height(20.dp))

                        Row{
                            Text("Excluir conta:", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 23.dp, top = 8.dp)
                            )
                            OutlinedButton (
                                onClick = {
                                    viewModel.usuarioDetalhe.value?.idUsuario?.let { id ->
                                        viewModel.deletarUsuario(id)
                                        // Adicione a navegação para a tela de bloqueio após excluir
                                        navController.navigate("telaBloqueio") {
                                            popUpTo(0) // Limpa toda a pilha de navegação
                                        }
                                    }
                                },

                                //border = BorderStroke(2.dp, Vinho),
                                modifier = Modifier
                                    .width(200.dp)
                                    .height(40.dp)
                            ) {
                                Image(
                                    painterResource(id = R.drawable.lixeira),
                                    contentDescription = null
                                )
                            }
                        }

                        Spacer(Modifier.height(10.dp))

                        Row{
                            Text("Nosso contato:", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 23.dp, top = 8.dp)
                            )
                            Text(text = "unimind@gmail.com", fontSize = 20.sp, modifier = Modifier
                                .padding(top = 7.dp))
                        }

                        Spacer(Modifier.height(15.dp))

                        Row(
                            Modifier.padding(start = 120.dp)
                        ){
                            OutlinedButton(
                                onClick = {
                                    viewModel.usuarioDetalhe.value?.let { currentUser ->
                                        viewModel.atualizarUsuario(
                                            user,
                                            email,
                                            senha, // Considere como gerenciar a atualização de senha
                                            nivel.toInt() ?: currentUser.idNivel
                                        )
                                    }
                                }
                            ) {
                                Text(text = "Salvar alterações", color = Black)
                            }
                        }
                    }
                }
            }
        }
    }
}
