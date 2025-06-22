package com.example.unimind

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.unimind.ui.theme.Bege
import com.example.unimind.ui.theme.Nude
import com.example.unimind.ui.theme.UnimindTheme
import com.example.unimind.ui.theme.Vinho

@Composable
fun Bloqueio(navController: NavController) {
    val cuteFont = FontFamily(Font(R.font.cute_letters))
    val interFont = FontFamily(Font(R.font.inter))

    UnimindTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Vinho),
            contentAlignment = Alignment.Center
        ) {
            // Fundo principal com a cor Vinho
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Vinho
            ) {}

            // Container inferior com cantos arredondados
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp) // Ajuste a altura conforme necessário
                        .clip(RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp))
                        .background(Nude),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Spacer(modifier = Modifier.height(80.dp)) // Espaço para a imagem do mascote

                        Text(
                            text = "UNIMIND",
                            fontFamily = cuteFont,
                            fontSize = 64.sp,
                            color = Vinho
                        )

                        Text(
                            text = "Mentalizou, realizou.",
                            fontFamily = interFont,
                            fontSize = 20.sp,
                            color = Vinho,
                            fontWeight = FontWeight.Light
                        )

                        Spacer(modifier = Modifier.height(40.dp))

                        Button(
                            onClick = { navController.navigate("telaCadastro") },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Bege,
                                contentColor = Vinho
                            ),
                            border = BorderStroke(1.dp, Vinho),
                            modifier = Modifier
                                .width(240.dp)
                                .height(48.dp)
                        ) {
                            Text(text = "Cadastrar", fontSize = 18.sp)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { navController.navigate("telaEntrar") },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Bege,
                                contentColor = Vinho
                            ),
                            border = BorderStroke(1.dp, Vinho),
                            modifier = Modifier
                                .width(240.dp)
                                .height(48.dp)
                        ) {
                            Text(text = "Entrar", fontSize = 18.sp)
                        }
                        Spacer(modifier = Modifier.height(60.dp))
                    }
                }
            }

            // Imagem do Mascote sobreposta
            Image(
                painter = painterResource(id = R.drawable.bichinho),
                contentDescription = "Mascote Unimind",
                modifier = Modifier
                    .size(250.dp)
                    .offset(y = (-60).dp) // Desloca a imagem para cima
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun BloqueioPreview() {
    Bloqueio(rememberNavController())
}