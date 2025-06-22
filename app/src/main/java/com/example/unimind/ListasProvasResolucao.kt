package com.example.unimind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.unimind.ui.theme.Nude
import com.example.unimind.ui.theme.UnimindTheme
import com.example.unimind.ui.theme.Vinho

@Composable
fun ListasProvasResolucao(navController: NavController) {
    UnimindTheme {
        Column(
            modifier = Modifier.fillMaxSize().background(Vinho)
        ) {
            // Cabeçalho Fixo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Nude)
                    .padding(vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Prova A - Conteúdo",
                    color = Vinho,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Conteúdo principal rolável
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Card da Questão
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Nude),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Questão 1",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Non nulla doloremque in odit unde a maxime recusandae in maxime perferendis. Sed similique voluptatum ea voluptatum internos ut eligendi impedit. Quo exercitationem illum aut sint internos et veritatis itaque et saepe eaque. Non dolores sunt et esse odio et iusto galisum ut quae quia eum atque rerum id rerum amet?",
                            color = Color.Black,
                            fontSize = 16.sp,
                            lineHeight = 22.sp
                        )
                    }
                }

                // Card das Alternativas
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Nude),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Alternativas",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        listOf("A", "B", "C", "D", "E").forEach { alternativa ->
                            Button(
                                onClick = { /* TODO: Lógica de seleção */ },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, Color(0xFFD9D9D9)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    Text(
                                        text = alternativa,
                                        color = Color.Black,
                                        modifier = Modifier.padding(start = 16.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // Botões de Navegação
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    NavButton(
                        text = "Questão anterior",
                        iconRes = R.drawable.baseline_arrow_forward_ios_vinho,
                        iconOnLeft = true,
                        onClick = { /* TODO: Navegar para questão anterior */ }
                    )
                    NavButton(
                        text = "Próxima questão",
                        iconRes = R.drawable.baseline_arrow_forward_ios_vinho,
                        iconOnLeft = false,
                        onClick = { /* TODO: Navegar para próxima questão */ }
                    )
                }

                // Botão Terminar
                Button(
                    onClick = { /* TODO: Terminar a prova */ },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Nude),
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(50.dp)
                ) {
                    Text(
                        text = "Terminar",
                        color = Vinho,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Rodapé Fixo (Cronômetro)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                    .background(Nude),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "45:00",
                    color = Vinho,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun NavButton(text: String, iconRes: Int, iconOnLeft: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(containerColor = Nude),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
    ) {
        if (iconOnLeft) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = Vinho,
                modifier = Modifier.size(18.dp).graphicsLayer(scaleX = -1f) // Inverte o ícone
            )
            Spacer(Modifier.width(8.dp))
        }

        Text(text = text, color = Vinho, fontWeight = FontWeight.SemiBold)

        if (!iconOnLeft) {
            Spacer(Modifier.width(8.dp))
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = Vinho,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListasProvasResolucaoPreview() {
    ListasProvasResolucao(navController = rememberNavController())
}