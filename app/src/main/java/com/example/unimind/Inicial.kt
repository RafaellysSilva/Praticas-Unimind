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
import com.example.unimind.viewmodel.UsuarioViewModel

@Composable
fun Inicial(navController: NavController, viewModel: UsuarioViewModel){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Nude
    ) {
        Column(
            Modifier
                .fillMaxSize(),
            //.verticalScroll(rememberScrollState()),
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
                    //horizontalArrangement = Arrangement.Center, // Centraliza na horizontal
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 15.dp, 0.dp, 0.dp)
                ) {
                    Image(
                        painterResource(id = R.drawable.bichinho),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 5.dp, end = 150.dp)
                            .width(70.dp)
                            .height(70.dp)
                    )
                    Text(
                        text = "Olá, usuário",
                        color = White,
                        fontSize = 20.sp,
                        /*modifier = Modifier
                            .padding(start = 10.dp)*/
                    )
                    Image(
                        painterResource(id = R.drawable.user), contentDescription = null

                    )
                }
            }

            //corpo

            Box(
                //modifier = Modifier.weight(1f)
                contentAlignment = Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize() .padding(top = 100.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .background(Color(0xFFBB8C94))
                            .padding(top = 24.dp, bottom = 24.dp, start = 10.dp, end = 10.dp)
                    )
                    {
                        OutlinedButton(
                            onClick = {
                                navController.navigate("telaConfiguracoes")
                            },
                            border = null
                        ) {
                            Image(
                                painterResource(id = R.drawable.config), contentDescription = null,
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                            )
                            Text(
                                "Configurações",
                                color = White,
                                modifier = Modifier.padding(start = 25.dp)
                            )
                        }
                    }

                    Spacer(Modifier.height(15.dp))

                    Row(
                        modifier = Modifier
                            .background(Color(0xFFBB8C94))
                            .padding(top = 24.dp, bottom = 24.dp, start = 22.dp, end = 22.dp)
                    )
                    {
                        OutlinedButton(
                            onClick = { navController.navigate("telaCalendario") },
                            border = null
                        ) {
                            Image(
                                painterResource(id = R.drawable.calendario),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                                    .padding(bottom = 4.dp)
                            )
                            Text(
                                "Calendário",
                                color = White,
                                modifier = Modifier.padding(start = 25.dp)
                            )
                        }
                    }
                    Footer(navController)
                }
                //Footer(navController)
            }

            //Footer(navController)
        }
        //Footer(navController)
    }
}