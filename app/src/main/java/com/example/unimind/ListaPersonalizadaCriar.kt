package com.example.unimind

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
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

@Composable
fun ListaPersonalizadaCriar(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel,
    questaoViewModel: QuestaoViewModel,
    listaViewModel: ListaPersonalizadaViewModel
) {

    var nomeLista by remember { mutableStateOf("") }
    var fonte by remember { mutableStateOf<String?>(null) }
    var categoria by remember { mutableStateOf<Int?>(null) }
    var anoMinimo by remember { mutableStateOf<Int?>(null) }
    var anoMaximo by remember { mutableStateOf<Int?>(null) }
    var numeroQuestoes by remember { mutableStateOf("") }

    // Carrega as questões e os filtros
    LaunchedEffect(Unit) {
        questaoViewModel.listarQuestoes()
    }

    val questoes by questaoViewModel.questoes

    val fontesDisponiveis = remember(questoes) {
        questoes.map { it.fonte }.distinct().sorted()
    }
    val categoriasDisponiveis = remember(questoes) {
        questoes.map { it.idCategoria }.distinct().sorted()
    }
    val anosDisponiveis = remember(questoes) {
        questoes.map { it.ano }.distinct().sorted()
    }

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

                DropdownCategorias(
                    label = "Categoria",
                    selectedCategoria = categoria,
                    onCategoriaSelected = { categoria = it },
                    categorias = categoriasDisponiveis
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 45.dp),
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

                CustomTextField(
                    value = numeroQuestoes,
                    onValueChange = { numeroQuestoes = it },
                    placeholder = "Número de Questões"
                )
                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        val idUsuario = usuarioViewModel.usuarioDetalhe.value?.idUsuario
                        if (idUsuario != null && nomeLista.isNotBlank() && categoria != null) {
                            val novaLista = ListaPersonalizada(
                                idLista = 0, // O ID será gerado pelo backend
                                idUsuario = idUsuario,
                                titulo = nomeLista,
                                idCategoria = categoria!!,
                                fonte = fonte,
                                ano = null // Você pode adaptar para usar anoMinimo e anoMaximo
                            )
                            listaViewModel.criarLista(novaLista) {
                                navController.navigate("telaListasProvasArea")
                            }
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
    selectedCategoria: Int?,
    onCategoriaSelected: (Int) -> Unit,
    categorias: List<Int>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier.fillMaxWidth().padding(horizontal = 45.dp)
    ) {
        OutlinedTextField(
            value = selectedCategoria?.toString() ?: "",
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