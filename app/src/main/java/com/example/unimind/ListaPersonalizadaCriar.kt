package com.example.unimind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.unimind.ui.theme.Bege
import com.example.unimind.ui.theme.Nude
import com.example.unimind.ui.theme.UnimindTheme
import com.example.unimind.ui.theme.Vinho

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaPersonalizadaCriar(navController: NavController) {

    var nome by remember { mutableStateOf("") }
    var categoriaSelecionada by remember { mutableStateOf("") }
    var fonteSelecionada by remember { mutableStateOf("") }
    var anoSelecionado by remember { mutableStateOf("") }
    var tempoSelecionado by remember { mutableStateOf("") }
    var questoesSelecionadas by remember { mutableStateOf("") }

    val isFonteEnabled = categoriaSelecionada.isNotEmpty()
    val isAnoEnabled = fonteSelecionada.isNotEmpty()

    // Dados de exemplo para os dropdowns (substituir pela lógica da API)
    val categorias = listOf("Matemática", "Português", "História", "Geografia")
    val fontes = listOf("Enem", "Fuvest", "Unicamp")
    val anos = listOf("2024", "2023", "2022", "2021")
    val tempos = listOf("30 minutos", "60 minutos", "90 minutos")
    val quantidadesQuestoes = listOf("10 questões", "15 questões", "20 questões")


    UnimindTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(Vinho),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Criar Lista Personalizada",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Footer
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clip(RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp))
                        .background(Vinho),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bichinho),
                        contentDescription = "Mascote",
                        modifier = Modifier
                            .size(80.dp)
                            .padding(end = 16.dp, bottom = 8.dp)
                    )
                }
            }

            // Formulário Central
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .background(Nude)
                        .padding(24.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        EditableField("Nome", nome) { nome = it }

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
                            onValueChange = { fonteSelecionada = it },
                            enabled = isFonteEnabled
                        )

                        SelectableField(
                            label = "Ano",
                            selectedValue = anoSelecionado,
                            options = anos,
                            onValueChange = { anoSelecionado = it },
                            enabled = isAnoEnabled
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

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { navController.navigate("telaListasProvasResolucao") },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Bege),
                            border = BorderStroke(1.dp, Vinho),
                            modifier = Modifier
                                .width(200.dp)
                                .height(48.dp)
                        ) {
                            Text(
                                text = "Criar",
                                color = Vinho,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EditableField(label: String, value: String, onValueChange: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Vinho
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .width(180.dp)
                .defaultMinSize(minHeight = 48.dp), // Usa altura mínima
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFD9D9D9),
                focusedContainerColor = Color(0xFFD9D9D9),
                unfocusedIndicatorColor = Color.Gray,
                focusedIndicatorColor = Vinho,
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                cursorColor = Vinho
            ),
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectableField(
    label: String,
    selectedValue: String,
    options: List<String>,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = if (enabled) Vinho else Color.Gray
        )
        ExposedDropdownMenuBox(
            expanded = expanded && enabled,
            onExpandedChange = { if (enabled) expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedValue,
                onValueChange = {},
                readOnly = true,
                enabled = enabled,
                modifier = Modifier
                    .menuAnchor()
                    .width(180.dp)
                    .defaultMinSize(minHeight = 48.dp), // Usa altura mínima
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    // Enabled state
                    unfocusedContainerColor = Color(0xFFD9D9D9),
                    focusedContainerColor = Color(0xFFD9D9D9),
                    unfocusedIndicatorColor = Color.Gray,
                    focusedIndicatorColor = Vinho,
                    unfocusedTextColor = Color.Black,
                    focusedTextColor = Color.Black,
                    disabledContainerColor = Color(0xFFBDBDBD),
                    disabledIndicatorColor = Color.Gray,
                    disabledTextColor = Color.DarkGray,
                    disabledTrailingIconColor = Color.Gray,
                    unfocusedTrailingIconColor = Color.Black
                ),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown"
                    )
                },
                singleLine = true
            )

            ExposedDropdownMenu(
                expanded = expanded && enabled,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onValueChange(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListaPersonalizadaCriarPreview() {
    ListaPersonalizadaCriar(navController = rememberNavController())
}