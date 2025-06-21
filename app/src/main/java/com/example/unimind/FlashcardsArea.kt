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

@Composable
fun FlashcardsArea(navController: NavController) {
    UnimindTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Nude
        ) {
            Header("Flashcards")

            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    Modifier
                        .padding(start = 50.dp)
                ) {
                    Box(
                        Modifier
                            .height(40.dp)
                            .width(200.dp)
                            .clip(RoundedCornerShape(60.dp))
                            .background(Vinho)
                            .fillMaxWidth(),
                    ) {
                        Text(
                            text = "Pesquisar...",
                            color = Nude,
                            fontSize = 15.sp,
                        )
                    }

                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Vinho),
                        onClick = { navController.navigate("telaFlashcardsPergunta") },
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .height(40.dp)
                            .width(50.dp)
                            .clip(RoundedCornerShape(60.dp))
                            .fillMaxWidth(),
                    ) {
                        Text(
                            text = "+",
                            color = White,
                            fontSize = 20.sp,
                        )
                    }
                }

                Spacer(
                    Modifier
                        .padding(20.dp)
                )

                Row (
                    Modifier
                        .padding(start = 50.dp)
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Rosinha),
                        onClick = { navController.navigate("telaFlashcardsPergunta") },
                        modifier = Modifier
                            .height(80.dp)
                            .width(140.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .fillMaxWidth(),
                    ) {
                        Text(
                            text = "Titulo\nCategoria",
                            color = White,
                            fontSize = 17.sp,
                        )
                    }

                    Spacer(
                        Modifier
                            .padding(10.dp)
                    )

                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Rosinha),
                        onClick = { navController.navigate("telaFlashcardsPergunta") },
                        modifier = Modifier
                            .height(80.dp)
                            .width(140.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .fillMaxWidth(),
                    ) {
                        Text(
                            text = "Titulo\nCategoria",
                            color = White,
                            fontSize = 17.sp,
                        )
                    }
                }
            }
            Footer(navController)
        }
    }
}