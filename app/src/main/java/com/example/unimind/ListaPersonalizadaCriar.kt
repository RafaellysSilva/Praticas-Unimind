package com.example.unimind

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.unimind.data.ListaPersonalizada
import com.example.unimind.ui.theme.Bege
import com.example.unimind.ui.theme.Nude
import com.example.unimind.ui.theme.Rosinha
import com.example.unimind.ui.theme.UnimindTheme
import com.example.unimind.ui.theme.Vinho
import com.example.unimind.viewmodel.ListaPersonalizadaViewModel
import com.example.unimind.viewmodel.QuestaoViewModel
import com.example.unimind.viewmodel.UsuarioViewModel

fun mapCategoriaIdToNome(id: Int): String {
    return when (id) {
        1 -> "História"
        2 -> "Sociologia"
        3 -> "Geografia"
        4 -> "Filosofia"
        5 -> "Matemática"
        else -> "Desconhecida"
    }
}


@Composable
fun ListaPersonalizadaCriar(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel,
    questaoViewModel: QuestaoViewModel,
    listaViewModel: ListaPersonalizadaViewModel
) {
    val context = LocalContext.current

    var nomeLista by remember { mutableStateOf("") }
    var fonte by remember { mutableStateOf<String?>(null) }
    var categoriaNome by remember { mutableStateOf<String?>(null) }
    var anoMinimo by remember { mutableStateOf<Int?>(null) }
    var anoMaximo by remember { mutableStateOf<Int?>(null) }
    var numeroQuestoes by remember { mutableStateOf("") }
    var tempo by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(Unit) {
        questaoViewModel.listarQuestoes()
    }

    val todasQuestoes by questaoViewModel.questoes

    val fontesDisponiveis = remember(todasQuestoes) {
        todasQuestoes.mapNotNull { it.fonte }.distinct().sorted()
    }

    // Mapa de ID para Nome e lista de Nomes para o Dropdown
    val categoriasMap = remember(todasQuestoes) {
        todasQuestoes.map { it.idCategoria }.distinct().associateWith { mapCategoriaIdToNome(it) }
    }
    val categoriasDisponiveis = remember(categoriasMap) {
        categoriasMap.values.sorted()
    }

    val anosDisponiveis = remember(todasQuestoes) {
        todasQuestoes.map { it.ano }.distinct().sorted()
    }
    val temposDisponiveis = listOf(15, 30, 45, 60)

    val questoesFiltradas = remember(fonte, categoriaNome, anoMinimo, anoMaximo, todasQuestoes) {
        // Encontra o ID correspondente ao nome da categoria selecionada para filtrar
        val categoriaId = categoriasMap.entries.find { it.value == categoriaNome }?.key

        todasQuestoes.filter { q ->
            (fonte == null || q.fonte == fonte) &&
                    (categoriaId == null || q.idCategoria == categoriaId) &&
                    (anoMinimo == null || q.ano >= anoMinimo!!) &&
                    (anoMaximo == null || q.ano <= anoMaximo!!)
        }
    }
    val maxQuestoes = questoesFiltradas.size

    UnimindTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Nude)
        ) {
            Header("Criar Lista Personalizada")

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 100.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                CustomTextField(
                    value = nomeLista,
                    onValueChange = { nomeLista = it },
                    placeholder = "Nome da Lista"
                )
                Spacer(modifier = Modifier.height(16.dp))

                DropdownFontes(
                    label = "Fonte",
                    selectedFonte = fonte,
                    onFonteSelected = { fonte = it },
                    fontes = fontesDisponiveis
                )
                Spacer(modifier = Modifier.height(16.dp))

                // O Dropdown agora usa os nomes das categorias
                DropdownCategorias(
                    label = "Matéria",
                    selectedCategoria = categoriaNome,
                    onCategoriaSelected = { categoriaNome = it },
                    categorias = categoriasDisponiveis
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 45.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DropdownAnos(
                        label = "Ano Mínimo",
                        selectedYear = anoMinimo,
                        onYearSelected = { anoMinimo = it },
                        years = anosDisponiveis,
                        modifier = Modifier.weight(1f)
                    )
                    DropdownAnos(
                        label = "Ano Máximo",
                        selectedYear = anoMaximo,
                        onYearSelected = { anoMaximo = it },
                        years = anosDisponiveis,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                DropdownTempo(
                    label = "Tempo Limite",
                    selectedTempo = tempo,
                    onTempoSelected = { tempo = it },
                    tempos = temposDisponiveis
                )
                Spacer(modifier = Modifier.height(16.dp))

                CustomTextFieldWithValidation(
                    value = numeroQuestoes,
                    onValueChange = {
                        if (it.isEmpty() || (it.toIntOrNull() ?: 0) <= maxQuestoes) {
                            numeroQuestoes = it
                        }
                    },
                    placeholder = "Número de Questões",
                    helperText = "Máximo: $maxQuestoes questões"
                )
                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        val idUsuario = usuarioViewModel.usuarioDetalhe.value?.idUsuario
                        val numQuestoesInt = numeroQuestoes.toIntOrNull()

                        // Encontra o ID da categoria a partir do nome selecionado
                        val categoriaId = categoriasMap.entries.find { it.value == categoriaNome }?.key

                        if (idUsuario == null) {
                            Toast.makeText(context, "Erro: Usuário não encontrado", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        if (nomeLista.isBlank()) {
                            Toast.makeText(context, "Por favor, insira um nome para a lista", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        if (categoriaId == null) {
                            Toast.makeText(context, "Por favor, selecione uma matéria", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        if (numQuestoesInt == null || numQuestoesInt == 0) {
                            Toast.makeText(context, "Por favor, insira um número de questões válido", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        if (numQuestoesInt > maxQuestoes) {
                            Toast.makeText(context, "Número de questões excede o máximo disponível ($maxQuestoes)", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        val novaLista = ListaPersonalizada(
                            idLista = 0,
                            idUsuario = idUsuario,
                            titulo = nomeLista,
                            idCategoria = categoriaId, // Usa o ID encontrado
                            fonte = fonte,
                            ano = null, // Lógica de ano min/max pode ser tratada no backend
                            tempo = tempo
                        )
                        listaViewModel.criarLista(novaLista) {
                            navController.navigate("telaListasProvasArea")
                        }
                    },
                    modifier = Modifier
                        .width(200.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Vinho)
                ) {
                    Text(
                        text = "Gerar Lista",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
fun CustomTextFieldWithValidation(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    helperText: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 45.dp)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder, color = Vinho) },
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Rosinha,
                focusedContainerColor = Rosinha,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedTextColor = Vinho,
                focusedTextColor = Vinho,
                cursorColor = Vinho,
                unfocusedPlaceholderColor = Vinho,
                focusedPlaceholderColor = Vinho,
            ),
            textStyle = TextStyle(color = Vinho),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )
        Text(
            text = helperText,
            color = Vinho.copy(alpha = 0.7f),
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownTempo(
    label: String,
    selectedTempo: Int?,
    onTempoSelected: (Int) -> Unit,
    tempos: List<Int>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 45.dp)
    ) {
        OutlinedTextField(
            value = selectedTempo?.toString()?.let { "$it min" } ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(label, color = Vinho) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Rosinha,
                focusedContainerColor = Rosinha,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedTextColor = Vinho,
                focusedTextColor = Vinho,
            ),
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Rosinha)
        ) {
            tempos.forEach { tempo ->
                DropdownMenuItem(
                    text = { Text("$tempo minutos", color = Vinho) },
                    onClick = {
                        onTempoSelected(tempo)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownFontes(
    label: String,
    selectedFonte: String?,
    onFonteSelected: (String) -> Unit,
    fontes: List<String>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier.fillMaxWidth().padding(horizontal = 45.dp)
    ) {
        OutlinedTextField(
            value = selectedFonte ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(label, color = Vinho) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Rosinha,
                focusedContainerColor = Rosinha,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedTextColor = Vinho,
                focusedTextColor = Vinho,
            ),
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Rosinha)
        ) {
            fontes.forEach { fonte ->
                DropdownMenuItem(
                    text = { Text(fonte, color = Vinho) },
                    onClick = {
                        onFonteSelected(fonte)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownCategorias(
    label: String,
    selectedCategoria: String?,
    onCategoriaSelected: (String?) -> Unit,
    categorias: List<String>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier.fillMaxWidth().padding(horizontal = 45.dp)
    ) {
        OutlinedTextField(
            value = selectedCategoria ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(label, color = Vinho) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Rosinha,
                focusedContainerColor = Rosinha,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedTextColor = Vinho,
                focusedTextColor = Vinho,
            ),
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Rosinha)
        ) {
            categorias.forEach { categoria ->
                DropdownMenuItem(
                    text = { Text("Categoria $categoria", color = Vinho) }, // Adapte para mostrar o nome da categoria se tiver
                    onClick = {
                        onCategoriaSelected(categoria)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownAnos(
    label: String,
    selectedYear: Int?,
    onYearSelected: (Int) -> Unit,
    years: List<Int>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedYear?.toString() ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(label, color = Vinho) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            shape = RoundedCornerShape(15.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Rosinha,
                focusedContainerColor = Rosinha,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedTextColor = Vinho,
                focusedTextColor = Vinho,
            ),
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Rosinha)
        ) {
            years.forEach { year ->
                DropdownMenuItem(
                    text = { Text("$year", color = Vinho) },
                    onClick = {
                        onYearSelected(year)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 45.dp),
        placeholder = { Text(placeholder, color = Vinho) },
        shape = RoundedCornerShape(15.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Rosinha,
            focusedContainerColor = Rosinha,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedTextColor = Vinho,
            focusedTextColor = Vinho,
            cursorColor = Vinho,
            unfocusedPlaceholderColor = Vinho,
            focusedPlaceholderColor = Vinho,
        ),
        textStyle = TextStyle(color = Vinho)
    )
}