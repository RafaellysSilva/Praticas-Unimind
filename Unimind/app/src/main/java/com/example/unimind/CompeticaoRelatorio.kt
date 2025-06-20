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
fun CompeticaoRelatorio(navController: NavController) {
    @Composable
    fun PlayerCard(questoesText: String) {
        Card(
            border = BorderStroke(width = 2.dp, color = Color(0xFFBFC8CD)),
            modifier = Modifier
                .width(170.dp)
                .height(120.dp)
                .padding(vertical = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(color = Color(0xFFAFE5FF))
                    .padding(vertical = 6.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(CircleShape),
                    contentAlignment = Center
                ) {
                    Icon(
                        modifier = Modifier.size(80.dp),
                        painter = painterResource(R.drawable.fotouser1),
                        contentDescription = null,
                        tint = Color.Unspecified,
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = questoesText,
                    fontSize = 16.sp,
                    color = Color(0xFF3E6B88),
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    @Composable
    fun CorrectQuestionsCard() {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f).shadow(5.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E0D1)),
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Questões corretas",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Nome 1",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Box(
                        modifier = Modifier
                            .height(30.dp)
                            .fillMaxWidth(0.5f)
                            .background(Color(0xFF4A90E2))
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Nome 2",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Box(
                        modifier = Modifier
                            .height(30.dp)
                            .fillMaxWidth(0.8f)
                            .background(Color(0xFFD32F2F))
                    )
                }
            }
        }
    }

    @Composable
    fun ProgressionCard() {
        @Composable
        fun MultiColorProgressBar(progress1: Float, progress2: Float) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(White)
            ) {
                Row(modifier = Modifier.fillMaxSize().border(width = 5.dp, color = White)) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(progress1)
                            .background(Color(0xFF4A90E2))
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(progress2)
                            .background(Color(0xFFD32F2F))
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f).shadow(5.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E0D1)),
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(vertical = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(85.dp)
                ) {
                    Text(
                        "Desafio",
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Bold,
                        color = Black
                    )

                    Text("7 dias restantes", fontSize = 16.sp, color = Color.Gray)
                }

                Text("Resolver 100 questões", fontSize = 18.sp, color = Black)

                Spacer(modifier = Modifier.height(25.dp))

                MultiColorProgressBar(0.5f, 0.5f)

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    PlayerCard("34 questões")

                    Spacer(modifier = Modifier.width(6.dp))

                    PlayerCard("25 questões")
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFBB8C8C))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CompeticaoHeader()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProgressionCard()

                Spacer(modifier = Modifier.height(21.dp))

                CorrectQuestionsCard()
            }
        }
    }

    Footer(rememberNavController())
}