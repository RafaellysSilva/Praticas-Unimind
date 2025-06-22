package com.example.unimind

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.unimind.data.Competicao
import com.example.unimind.ui.theme.*
import com.example.unimind.viewmodel.CompeticaoViewModel
import com.example.unimind.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompeticaoCriar(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel,
    competicaoViewModel: CompeticaoViewModel
) {
    val context = LocalContext.current
    val usuarioAtual by usuarioViewModel.usuarioDetalhe

    // Estados para os campos de seleção
    var categoriaSelecionada by remember { mutableStateOf("") }
    var fonteSelecionada by remember { mutableStateOf("") }
    var tempoSelecionado by remember { mutableStateOf("60 minutos") }
    var questoesSelecionadas by remember { mutableStateOf("15 questões") }
    var usuarioParaCompetir by remember { mutableStateOf("") }

    // Opções para os dropdowns
    val categorias = listOf("Exatas", "Humanas", "Biológicas", "Linguagens")
    val fontes = listOf("Enem", "Fuvest", "Unicamp", "Outras")
    val tempos = listOf("30 minutos", "60 minutos", "90 minutos")
    val quantidadesQuestoes = listOf("10 questões", "15 questões", "20 questões")

    UnimindTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Nude)
        ) {
            Header("Competição")

            // Ícone do usuário sobreposto
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 185.dp, end = 24.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Vinho)
                        .border(BorderStroke(2.dp, Nude), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = usuarioAtual?.nome?.firstOrNull()?.toString() ?: "U",
                        color = Nude,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Scaffold(
                containerColor = Color.Transparent,
                bottomBar = { Footer(navController) }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(top = 220.dp) // Espaço para header + ícone
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SectionTitle("Preferências")
                    ConfigSection {
                        SelectableField(
                            label = "Categoria",
                            selectedValue = categoriaSelecionada,
                            options = categorias,
                            onValueChange = { categoriaSelecionada = it }
                        )
                        SelectableField(
                            label = "Fonte",
                            selectedValue = fonteSelecionada,
                            options = fontes,
                            onValueChange = { fonteSelecionada = it }
                        )
                        SelectableField(
                            label = "Tempo",
                            selectedValue = tempoSelecionado,
                            options = tempos,
                            onValueChange = { tempoSelecionado = it }
                        )
                        SelectableField(
                            label = "Questões",
                            selectedValue = questoesSelecionadas,
                            options = quantidadesQuestoes,
                            onValueChange = { questoesSelecionadas = it }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            Toast.makeText(context, "Preferências salvas!", Toast.LENGTH_SHORT).show()
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Bege),
                        border = BorderStroke(1.dp, Vinho),
                        modifier = Modifier
                            .width(220.dp)
                            .height(48.dp)
                    ) {
                        Text("Salvar alterações", color = Vinho, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Você ainda não está competindo com ninguém.\nConvide alguém do mesmo nível para apostar questões.",
                        color = Vinho.copy(alpha = 0.8f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Seção de Convite
                    InfoRow(
                        label = "Usuário para competir",
                        value = usuarioParaCompetir,
                        onValueChange = { usuarioParaCompetir = it }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (usuarioParaCompetir.isBlank()) {
                                Toast.makeText(context, "Digite o nome de um usuário para convidar.", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            if (usuarioAtual == null) {
                                Toast.makeText(context, "Erro: Faça login novamente.", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            // Lógica para criar a competição (simplificada)
                            val competicao = usuarioAtual?.idUsuario?.let {
                                Competicao(
                                    idCompeticao = 0, // O ID será gerado pelo backend
                                    data = "2025-06-22", // Usar data atual
                                    idUsuario1 = it,
                                    idUsuario2 = 2, // ID do usuário convidado (precisaria buscar pelo nome)
                                    idNivel = usuarioAtual?.idNivel ?: 1
                                )
                            }
                            if (competicao != null) {
                                competicaoViewModel.criarCompeticao(competicao) {
                                    navController.navigate("telaCompeticaoMomento")
                                }
                            }
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Rosinha),
                        modifier = Modifier
                            .width(220.dp)
                            .height(50.dp)
                    ) {
                        Text(
                            text = "Convidar",
                            color = Nude,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp)) // Espaço extra no final
                }
            }
        }
    }
}