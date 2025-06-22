package com.example.unimind

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.unimind.data.Questao
import com.example.unimind.ui.theme.Nude
import com.example.unimind.ui.theme.UnimindTheme
import com.example.unimind.ui.theme.Vinho
import com.example.unimind.viewmodel.ListaPersonalizadaViewModel
import com.example.unimind.viewmodel.QuestaoViewModel
import kotlinx.coroutines.delay

@Composable
fun ListasProvasResolucao(
    navController: NavController,
    listaId: Int,
    listaViewModel: ListaPersonalizadaViewModel,
    questaoViewModel: QuestaoViewModel
) {
    val listaDetalhe by listaViewModel.listaDetalhe
    val todasQuestoes by questaoViewModel.questoes

    var questoesDaLista by remember { mutableStateOf<List<Questao>>(emptyList()) }
    var questaoAtualIndex by remember { mutableStateOf(0) }
    var tempoRestante by remember { mutableStateOf(0L) }
    var respostaSelecionada by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(listaId) {
        listaViewModel.buscarLista(listaId)
        questaoViewModel.listarQuestoes()
    }

    LaunchedEffect(listaDetalhe, todasQuestoes) {
        listaDetalhe?.let { lista ->
            tempoRestante = (lista.tempo ?: 0).toLong() * 60000 // Converte minutos para milissegundos

            questoesDaLista = todasQuestoes.filter { q ->
                (lista.idCategoria == q.idCategoria) &&
                        (lista.fonte == null || q.fonte == lista.fonte) &&
                        (lista.ano == null || q.ano == lista.ano) // Simplificado, pode ser ajustado para min/max
            }
        }
    }

    // Cronômetro
    LaunchedEffect(tempoRestante) {
        if (tempoRestante > 0) {
            delay(1000)
            tempoRestante -= 1000
        }
    }

    val questaoAtual = questoesDaLista.getOrNull(questaoAtualIndex)

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
                    text = listaDetalhe?.titulo ?: "Carregando...",
                    color = Vinho,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Conteúdo principal rolável
            if (questaoAtual != null) {
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
                                text = "Questão ${questaoAtualIndex + 1}",
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = questaoAtual.pergunta,
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
                            // A lógica de alternativas precisa ser ajustada se a API as fornecer separadamente
                            listOf("A", "B", "C", "D", "E").forEach { alternativa ->
                                AlternativaButton(
                                    text = alternativa,
                                    isSelected = respostaSelecionada == alternativa,
                                    onClick = { respostaSelecionada = alternativa }
                                )
                            }
                        }
                    }

                    // Botões de Navegação
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        NavButton(
                            text = "Anterior",
                            iconRes = R.drawable.baseline_arrow_forward_ios_vinho,
                            iconOnLeft = true,
                            enabled = questaoAtualIndex > 0,
                            onClick = {
                                if (questaoAtualIndex > 0) {
                                    questaoAtualIndex--
                                    respostaSelecionada = null
                                }
                            }
                        )
                        NavButton(
                            text = "Próxima",
                            iconRes = R.drawable.baseline_arrow_forward_ios_vinho,
                            iconOnLeft = false,
                            enabled = questaoAtualIndex < questoesDaLista.size - 1,
                            onClick = {
                                if (questaoAtualIndex < questoesDaLista.size - 1) {
                                    questaoAtualIndex++
                                    respostaSelecionada = null
                                }
                            }
                        )
                    }

                    // Botão Terminar
                    Button(
                        onClick = { navController.popBackStack() }, // Volta para a tela anterior
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
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Nude)
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
                val minutes = (tempoRestante / 1000) / 60
                val seconds = (tempoRestante / 1000) % 60
                Text(
                    text = String.format("%02d:%02d", minutes, seconds),
                    color = Vinho,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun AlternativaButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) Vinho.copy(alpha = 0.8f) else Color.White
    val textColor = if (isSelected) Color.White else Color.Black

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, if(isSelected) Color.Transparent else Color(0xFFD9D9D9)),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = text,
                color = textColor,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}


@Composable
private fun NavButton(
    text: String,
    iconRes: Int,
    iconOnLeft: Boolean,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = Nude,
            disabledContainerColor = Nude.copy(alpha = 0.5f)
        ),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
    ) {
        if (iconOnLeft) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = if(enabled) Vinho else Vinho.copy(alpha = 0.5f),
                modifier = Modifier.size(18.dp).graphicsLayer(scaleX = -1f) // Inverte o ícone
            )
            Spacer(Modifier.width(8.dp))
        }

        Text(
            text = text,
            color = if(enabled) Vinho else Vinho.copy(alpha = 0.5f),
            fontWeight = FontWeight.SemiBold
        )

        if (!iconOnLeft) {
            Spacer(Modifier.width(8.dp))
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = if(enabled) Vinho else Vinho.copy(alpha = 0.5f),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}