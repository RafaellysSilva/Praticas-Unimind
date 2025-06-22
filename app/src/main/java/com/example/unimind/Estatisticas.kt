package com.example.unimind

import Usuario
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.unimind.ui.theme.*
import com.example.unimind.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Estatisticas(navController: NavController, viewModel: UsuarioViewModel) {
    val usuario by viewModel.usuarioDetalhe

    // Valores padrão caso o usuário seja nulo
    val acertos = usuario?.acertosQuestoes ?: 0
    val erros = usuario?.errosQuestoes ?: 0
    val totalQuestoes = acertos + erros
    val tempoEstudo = usuario?.tempoEstudo ?: 0
    val competicoes = usuario?.competicoesRealizadas ?: 0

    UnimindTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Nude)
        ) {
            Header("Estatísticas")

            Scaffold(
                containerColor = Color.Transparent,
                bottomBar = { Footer(navController) }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(top = 180.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // Card de Questões
                    StatsCard(title = "Questões") {
                        StatBar(
                            label = "Corretas",
                            value = acertos.toFloat(),
                            maxValue = totalQuestoes.toFloat(),
                            color = Azul
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        StatBar(
                            label = "Incorretas",
                            value = erros.toFloat(),
                            maxValue = totalQuestoes.toFloat(),
                            color = Vinho
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Total: $totalQuestoes",
                            modifier = Modifier.align(Alignment.End),
                            color = Color.Gray
                        )
                    }

                    // Card de Tempo de Estudo
                    StatsCard(title = "Tempo de estudo") {
                        StatBar(
                            label = "Tempo",
                            value = tempoEstudo.toFloat(),
                            maxValue = 100f, // Defina um valor máximo de referência
                            color = Azul
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Total: $tempoEstudo minutos",
                            modifier = Modifier.align(Alignment.End),
                            color = Color.Gray
                        )
                    }

                    // Card de Competições
                    StatsCard(title = "Competições realizadas") {
                        StatBar(
                            label = "Competições",
                            value = competicoes.toFloat(),
                            maxValue = 50f, // Defina um valor máximo de referência
                            color = Azul
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Total: $competicoes",
                            modifier = Modifier.align(Alignment.End),
                            color = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
fun StatsCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Nude),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Vinho
            )
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}

@Composable
fun StatBar(label: String, value: Float, maxValue: Float, color: Color) {
    val progress = if (maxValue > 0) value / maxValue else 0f

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = Color.DarkGray,
            modifier = Modifier.width(90.dp)
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(30.dp)
                .clip(RoundedCornerShape(50))
                .background(Color.LightGray.copy(alpha = 0.5f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fraction = progress)
                    .clip(RoundedCornerShape(50))
                    .background(color)
            )
        }
    }
}