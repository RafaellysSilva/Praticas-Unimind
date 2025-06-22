package com.example.unimind

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
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

    val questoesDaLista by remember { derivedStateOf {
        todasQuestoes.filter { q ->
            listaDetalhe?.let { lista ->
                (lista.idCategoria == q.idCategoria) &&
                        (lista.fonte == null || q.fonte == lista.fonte) &&
                        (lista.ano == null || q.ano == q.ano)
            } ?: false
        }
    } }
    val questaoAtual = questoesDaLista.getOrNull(questaoAtualIndex)

    var tempoRestante by remember { mutableStateOf(0L) }

    // Efeito para buscar os dados da lista e das questões quando o ID da lista muda
    LaunchedEffect(listaId) {
        listaViewModel.buscarLista(listaId)
        questaoViewModel.listarQuestoes()
        questaoViewModel.reiniciarQuiz()
    }

    LaunchedEffect(listaDetalhe) {
        listaDetalhe?.let { lista ->
            tempoRestante = (lista.tempo ?: 0).toLong() * 60000 // Minutos para ms
        }
    }

    LaunchedEffect(key1 = tempoRestante, key2 = quizFinalizado) {
        if (tempoRestante > 0 && !quizFinalizado) {
            delay(1000)
            tempoRestante -= 1000
        }
    }

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
                    respostasUsuario = questaoViewModel.respostasUsuario,
                    acertos = questaoViewModel.acertos,
                    questoes = questoesDaLista,
                    onRefazer = {
                        questaoViewModel.reiniciarQuiz()
                        listaDetalhe?.let { lista ->
                            tempoRestante = (lista.tempo ?: 0).toLong() * 60000
                        }
                    },
                    onTerminar = {
                        questaoViewModel.reiniciarQuiz()
                        navController.popBackStack()
                    }
                )
            } else if (questaoAtual != null) {
                ConteudoQuiz(
                    modifier = Modifier.weight(1f),
                    questoesDaLista = questoesDaLista,
                    questaoAtual = questaoAtual,
                    questaoAtualIndex = questaoAtualIndex,
                    alternativaSelecionada = alternativaSelecionada,
                    onAlternativaSelected = { alternativa ->
                        questaoViewModel.selecionarAlternativa(alternativa)
                    },
                    onAnteriorClicked = {
                        questaoViewModel.anteriorQuestao()
                    },
                    onProximaClicked = {
                        questaoAtual.let { questaoViewModel.verificarResposta(it) }
                        questaoViewModel.proximaQuestao(questoesDaLista.size)
                    },
                    onTerminarClicked = {
                        questaoViewModel.reiniciarQuiz()
                        navController.popBackStack()
                    },
                    isAnteriorEnabled = questaoAtualIndex > 0, // Apenas controle de UI
                    isProximaEnabled = alternativaSelecionada != null
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
    questoesDaLista: List<Questao>,
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
            val proximaText = if (questaoAtualIndex == questoesDaLista.size - 1) "Finalizar" else "Próxima"
            NavButton(
                text = proximaText,
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
    respostasUsuario: List<Alternativa?>,
    acertos: List<Boolean>,
    questoes: List<Questao>,
    onRefazer: () -> Unit,
    onTerminar: () -> Unit
) {
    // Usamos um Column com weight para empurrar os botões para o final da tela
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Padding geral menor
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Card da Pontuação (sem alterações)
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Nude),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 24.dp, horizontal = 16.dp)
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
                    color = Color.Black.copy(alpha = 0.8f)
                )
                Text(
                    text = "$pontuacao / $totalQuestoes",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Vinho
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- CONTAINER PARA OS RESULTADOS ---
        // Este Card agrupa a lista de questões, como você pediu
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Nude),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Ocupa o espaço disponível, empurrando os botões para baixo
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 20.dp)
            ) {
                Text(
                    text = "Resumo das Questões",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Vinho,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                questoes.forEachIndexed { index, questao ->
                    ResultadoQuestao(
                        index = index + 1, // Passa o número da questão
                        questao = questao,
                        respostaUsuario = respostasUsuario.getOrNull(index),
                        acertou = acertos.getOrNull(index) ?: false
                    )
                    if (index < questoes.size - 1) {
                        Divider(
                            color = Color.Black.copy(alpha = 0.1f),
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botões de Ação
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onTerminar) {
                Text("Terminar", color = Nude, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = onRefazer,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Nude),
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
            ) {
                Text("Refazer Quiz", color = Vinho, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun ResultadoQuestao(
    index: Int,
    questao: Questao,
    respostaUsuario: Alternativa?,
    acertou: Boolean
) {
    val corIcone = if (acertou) Color(0xFF1E8E3E) else Color(0xFFD93025) // Verde e Vermelho mais sóbrios
    val icone = if (acertou) Icons.Filled.CheckCircle else Icons.Filled.Close

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp) // Espaçamento entre os textos
    ) {
        // Cabeçalho da questão (Número, Ícone e Pergunta)
        Row(verticalAlignment = Alignment.Top) {
            Text(
                text = "$index.",
                fontWeight = FontWeight.Bold,
                color = Vinho,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 2.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = icone,
                contentDescription = if (acertou) "Acertou" else "Errou",
                tint = corIcone,
                modifier = Modifier.size(20.dp).padding(top = 4.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = questao.pergunta,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black.copy(alpha = 0.9f),
                fontSize = 16.sp,
                lineHeight = 22.sp
            )
        }

        // Detalhes das respostas (Sua resposta e a correta)
        Column(modifier = Modifier.padding(start = 48.dp)) { // Alinhado com o texto da pergunta
            // Mostra a resposta do usuário
            Text(
                text = "Sua resposta: ${respostaUsuario?.texto ?: "Não respondido"}",
                color = if (acertou) Color.Gray else corIcone, // Destaca em vermelho se errou
                fontSize = 14.sp,
                fontStyle = if (acertou) FontStyle.Normal else FontStyle.Italic,
                textDecoration = if (acertou) TextDecoration.None else TextDecoration.LineThrough
            )

            // Se errou, mostra qual era a correta
            if (!acertou) {
                val respostaCorreta = questao.alternativas.find { it.correta }?.texto ?: "Não definida"
                Text(
                    text = "Resposta correta: $respostaCorreta",
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            }
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