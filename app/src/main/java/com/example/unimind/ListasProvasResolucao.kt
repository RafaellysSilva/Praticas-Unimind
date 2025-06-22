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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.unimind.data.Alternativa
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
    // Observa os estados do ViewModel
    val listaDetalhe by listaViewModel.listaDetalhe
    val todasQuestoes by questaoViewModel.questoes
    val questaoAtualIndex by questaoViewModel.indiceQuestaoAtual
    val alternativaSelecionada by questaoViewModel.alternativaSelecionada
    val pontuacao by questaoViewModel.pontuacao
    val quizFinalizado by questaoViewModel.quizFinalizado

    var questoesDaLista by remember { mutableStateOf<List<Questao>>(emptyList()) }
    var tempoRestante by remember { mutableStateOf(0L) }

    // Efeito para buscar os dados da lista e das questões quando o ID da lista muda
    LaunchedEffect(listaId) {
        listaViewModel.buscarLista(listaId)
        questaoViewModel.listarQuestoes()
        // Reinicia o quiz no ViewModel para garantir que um novo quiz comece limpo
        questaoViewModel.reiniciarQuiz()
    }

    // Filtra as questões relevantes para a lista atual
    LaunchedEffect(listaDetalhe, todasQuestoes) {
        listaDetalhe?.let { lista ->
            tempoRestante = (lista.tempo ?: 0).toLong() * 60000 // Minutos para ms

            questoesDaLista = todasQuestoes.filter { q ->
                (lista.idCategoria == q.idCategoria) &&
                        (lista.fonte == null || q.fonte == lista.fonte) &&
                        (lista.ano == null || q.ano == q.ano)
            }
        }
    }

    // Cronômetro
    LaunchedEffect(key1 = tempoRestante, key2 = quizFinalizado) {
        if (tempoRestante > 0 && !quizFinalizado) {
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
            Cabecalho(titulo = listaDetalhe?.titulo ?: "Carregando...")

            // Conteúdo principal
            if (quizFinalizado) {
                TelaResultados(
                    pontuacao = pontuacao,
                    totalQuestoes = questoesDaLista.size,
                    onRefazer = {
                        questaoViewModel.reiniciarQuiz()
                        // Reinicia o tempo também
                        listaDetalhe?.let { lista ->
                            tempoRestante = (lista.tempo ?: 0).toLong() * 60000
                        }
                    },
                    onTerminar = {
                        // Limpa o estado antes de voltar
                        questaoViewModel.reiniciarQuiz()
                        navController.popBackStack()
                    }
                )
            } else if (questaoAtual != null) {
                ConteudoQuiz(
                    modifier = Modifier.weight(1f),
                    questaoAtual = questaoAtual,
                    questaoAtualIndex = questaoAtualIndex,
                    alternativaSelecionada = alternativaSelecionada,
                    onAlternativaSelected = { alternativa ->
                        questaoViewModel.selecionarAlternativa(alternativa)
                    },
                    onAnteriorClicked = {
                        // A lógica do ViewModel não suporta voltar, mas podemos implementar aqui se necessário
                        // Por enquanto, esta ação não fará nada para não dessincronizar com o ViewModel
                    },
                    onProximaClicked = {
                        questaoViewModel.verificarResposta()
                        questaoViewModel.proximaQuestao()
                    },
                    onTerminarClicked = {
                        questaoViewModel.reiniciarQuiz()
                        navController.popBackStack()
                    },
                    isAnteriorEnabled = questaoAtualIndex > 0, // Apenas controle de UI
                    isProximaEnabled = questaoAtualIndex < questoesDaLista.size - 1
                )
            } else {
                // Tela de Carregamento
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Nude)
                }
            }

            // Rodapé Fixo (Cronômetro)
            RodapeCronometro(tempoRestante = tempoRestante)
        }
    }
}

@Composable
private fun Cabecalho(titulo: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Nude)
            .padding(vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = titulo,
            color = Vinho,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ConteudoQuiz(
    modifier: Modifier = Modifier,
    questaoAtual: Questao,
    questaoAtualIndex: Int,
    alternativaSelecionada: Alternativa?,
    onAlternativaSelected: (Alternativa) -> Unit,
    onAnteriorClicked: () -> Unit,
    onProximaClicked: () -> Unit,
    onTerminarClicked: () -> Unit,
    isAnteriorEnabled: Boolean,
    isProximaEnabled: Boolean
) {
    Column(
        modifier = modifier
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
                // Agora usa as alternativas da questão
                questaoAtual.alternativas.forEach { alternativa ->
                    AlternativaButton(
                        text = alternativa.texto,
                        isSelected = alternativa == alternativaSelecionada,
                        onClick = { onAlternativaSelected(alternativa) }
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
                enabled = isAnteriorEnabled,
                onClick = onAnteriorClicked
            )
            NavButton(
                text = "Próxima",
                iconRes = R.drawable.baseline_arrow_forward_ios_vinho,
                iconOnLeft = false,
                enabled = isProximaEnabled,
                onClick = onProximaClicked
            )
        }

        // Botão Terminar
        Button(
            onClick = onTerminarClicked,
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
}

@Composable
private fun TelaResultados(
    pontuacao: Int,
    totalQuestoes: Int,
    onRefazer: () -> Unit,
    onTerminar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Nude),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(32.dp)
            ) {
                Text(
                    text = "Quiz Finalizado!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Vinho
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Sua pontuação:",
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Text(
                    text = "$pontuacao / $totalQuestoes",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Vinho
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onRefazer,
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Nude),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp)
        ) {
            Text("Refazer Quiz", color = Vinho, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = onTerminar) {
            Text("Terminar", color = Nude, fontSize = 16.sp)
        }
    }
}

@Composable
private fun RodapeCronometro(tempoRestante: Long) {
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
            .defaultMinSize(minHeight = 48.dp) // Usa minHeight para permitir que o texto quebre a linha
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = text,
                color = textColor,
                modifier = Modifier.padding(start = 16.dp),
                textAlign = TextAlign.Start
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