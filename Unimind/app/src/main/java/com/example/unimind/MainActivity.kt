package com.example.unimind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.unimind.ui.theme.Bege
import com.example.unimind.ui.theme.Nude
import com.example.unimind.ui.theme.UnimindTheme
import com.example.unimind.ui.theme.Vinho

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.unimind.ui.theme.Rosinha


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnimindTheme {
                AppNavigation()
            }
        }
    }
}


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "telaBloqueio") {
        composable("telaBloqueio") { TelaBloqueio(navController) }
        composable("telaCadastro") { Cadastro(navController) }
        composable("telaEntrar") { Entrar(navController) }
        composable("telaFlashCardsArea") { FlashcardsArea(navController) }
    }
}


//função para chamar (tem várias telas que tem a mesma parte como o footer, fica mais facil assim)
@Composable
fun Footer() {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Bottom,
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Row(
                Modifier
                    .height(55.dp)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(Vinho),
            ) {
                Image(
                    painterResource(id = R.drawable.flashcards),
                    contentDescription = null,
                )

                Spacer(
                    Modifier
                        .padding(start = 10.dp)
                )

                Image(
                    painterResource(id = R.drawable.listasprovas),
                    contentDescription = null,
                )

                Spacer(
                    Modifier
                        .padding(start = 10.dp)
                )

                Image(
                    painterResource(id = R.drawable.competicao),
                    contentDescription = null,
                )

                Spacer(
                    Modifier
                        .padding(start = 10.dp)
                )

                Image(
                    painterResource(id = R.drawable.estatisticas),
                    contentDescription = null,
                )

                Spacer(
                    Modifier
                        .padding(start = 10.dp)
                )

                Image(
                    painterResource(id = R.drawable.bichinho),
                    contentDescription = null,
                )
            }
        }
    }
}


@Composable
fun TelaBloqueio(navController: NavController){
    UnimindTheme{
        Surface (
            modifier = Modifier.fillMaxSize(),
            color = Vinho
        ){
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    Modifier
                        .height(400.dp)
                        .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                        .background(Nude)
                        .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                ) {
                    Image(
                        painterResource(id = R.drawable.bichinho),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(bottom = 250.dp)
                    )

                    Text(
                        text = "UNIMIND",
                        color = Vinho,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(bottom = 100.dp)
                    )

                    Text(
                        text = "Mentalizou, realizou.",
                        color = Vinho,
                        fontSize = 17.sp,
                        modifier = Modifier
                            .padding(bottom = 40.dp)
                    )

                    Column (
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(bottom = 60.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        OutlinedButton (
                            onClick = { navController.navigate("telaCadastro") },
                            border = BorderStroke(2.dp, Vinho),
                            modifier = Modifier
                                .width(200.dp)
                        ) {
                            Text(
                                text = "Cadastrar",
                                color = Vinho
                            )
                        }

                        OutlinedButton (
                            onClick = { navController.navigate("telaEntrar") },
                            border = BorderStroke(2.dp, Vinho),
                            modifier = Modifier
                                .width(200.dp)
                        ) {
                            Text(
                                text = "Entrar",
                                color = Vinho
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Cadastro(navController: NavController) {
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
                    contentAlignment = Alignment.Center


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
                        .height(785.dp)
                        .clip(RoundedCornerShape(topStart = 45.dp, topEnd = 50.dp))
                        .background(Nude)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                }
            }
        }
    }
}


@Composable
fun Entrar(navController: NavController){
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
                    contentAlignment = Alignment.Center


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
                        .height(785.dp)
                        .clip(RoundedCornerShape(topStart = 45.dp, topEnd = 50.dp))
                        .background(Nude)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "tela entrar")
                }
            }
        }
    }
}


@Composable
fun FlashcardsArea(navController: NavController) {
    //colocar os buttons certo
    UnimindTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Nude
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top
            ) {
                Box(
                    Modifier
                        .height(220.dp)
                        .clip(RoundedCornerShape(bottomStart = 1500.dp, bottomEnd = 1500.dp))
                        .background(Bege)
                        .fillMaxWidth()
                ) {
                    Box(
                        Modifier
                            .height(190.dp)
                            .clip(RoundedCornerShape(bottomStart = 1500.dp, bottomEnd = 1500.dp))
                            .background(Rosinha)
                            .fillMaxWidth()
                    ) {
                        Box(
                            Modifier
                                .height(160.dp)
                                .clip(RoundedCornerShape(bottomStart = 1500.dp, bottomEnd = 1500.dp))
                                .background(Vinho)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Flashcards",
                                color = Nude,
                                fontSize = 20.sp,
                            )
                        }
                    }
                }
            }

            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    Modifier
                        .padding(start = 50.dp),
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

                    Box(
                        Modifier
                            .padding(start = 20.dp)
                            .height(40.dp)
                            .width(50.dp)
                            .clip(RoundedCornerShape(60.dp))
                            .background(Vinho)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
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
                    Box(
                        Modifier
                            .height(80.dp)
                            .width(140.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(Rosinha)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
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

                    Box(
                        Modifier
                            .height(80.dp)
                            .width(140.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(Rosinha)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Titulo\nCategoria",
                            color = White,
                            fontSize = 17.sp,
                        )
                    }
                }
            }

            Footer()
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun AppPreview(){
    TelaBloqueio(rememberNavController())
}