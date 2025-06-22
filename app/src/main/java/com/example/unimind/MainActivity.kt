package com.example.unimind

import com.example.unimind.viewmodel.LoginResult
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import com.example.unimind.ui.theme.Bege
import com.example.unimind.ui.theme.Nude
import com.example.unimind.ui.theme.UnimindTheme
import com.example.unimind.ui.theme.Vinho
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.unimind.data.Flashcard
import com.example.unimind.ui.theme.Rosinha
import com.example.unimind.viewmodel.CompeticaoViewModel
import com.example.unimind.viewmodel.FlashcardViewModel
import com.example.unimind.viewmodel.ListaPersonalizadaViewModel
import com.example.unimind.viewmodel.QuestaoViewModel
import com.example.unimind.viewmodel.UsuarioViewModel

//import com.example.usuarioapp.viewmodel.LoginResult
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context
// Tipo de resultado do login

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            val usuarioViewModel: UsuarioViewModel = viewModel()
            val flashcardViewModel: FlashcardViewModel = viewModel()
            val competicaoViewModel: CompeticaoViewModel = viewModel()
            val listaPersonalizadaViewModel: ListaPersonalizadaViewModel = viewModel()
            val questaoViewModel: QuestaoViewModel = viewModel()

            UnimindTheme {
                AppNavigation(navController, usuarioViewModel, flashcardViewModel, competicaoViewModel, listaPersonalizadaViewModel, questaoViewModel)
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController, usuarioViewModel: UsuarioViewModel, flashcardViewModel: FlashcardViewModel, competicaoViewModel: CompeticaoViewModel, listaPersonalizadaViewModel: ListaPersonalizadaViewModel, questaoViewModel: QuestaoViewModel) {
    NavHost(navController = navController, startDestination = "telaBloqueio") {
        composable("telaBloqueio") { Bloqueio(navController) }
        composable("telaCadastro") { Cadastro(navController, usuarioViewModel) }
        composable("telaEntrar") { Entrar(navController, usuarioViewModel) }
        composable("telaInicial") { Inicial(navController, usuarioViewModel) }
        composable("telaConfiguracoes") { Configuracoes(navController, usuarioViewModel) }
        composable("telaFlashcardsArea") { FlashcardsArea(navController, flashcardViewModel, usuarioViewModel) }
        composable("telaFlashcards/{flashcardId}",
            arguments = listOf(navArgument("flashcardId") {
                type = NavType.IntType
                defaultValue = -1
            })
        ) { backStackEntry ->
            val flashcardId = backStackEntry.arguments?.getInt("flashcardId")
            FlashcardsPergunta(
                navController,
                flashcardId = if (flashcardId == -1) null else flashcardId,
                viewModel = flashcardViewModel
            )
        }
        composable(
            route = "telaFlashcardsResposta/{flashcardId}?pergunta={pergunta}",
            arguments = listOf(
                navArgument("flashcardId") {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument("pergunta") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val flashcardId = backStackEntry.arguments?.getInt("flashcardId")
            val pergunta = backStackEntry.arguments?.getString("pergunta") ?: ""
            FlashcardsResposta(
                navController,
                pergunta,
                if (flashcardId == -1) null else flashcardId,
                flashcardViewModel,
                usuarioViewModel
            )
        }
        composable("telaListasProvasArea") { ListasProvasArea(navController, listaPersonalizadaViewModel, usuarioViewModel) }
        composable(
            route = "telaListasProvasResolucao/{listaId}",
            arguments = listOf(navArgument("listaId") { type = NavType.IntType })
        ) { backStackEntry ->
            val listaId = backStackEntry.arguments?.getInt("listaId")
            if (listaId != null) {
                ListasProvasResolucao(navController = navController, listaId = listaId, listaPersonalizadaViewModel, questaoViewModel)
            } else {
                navController.popBackStack()
            }
        }
        composable("telaListaPersonalizadaCriar") { ListaPersonalizadaCriar(navController, usuarioViewModel, questaoViewModel, listaPersonalizadaViewModel) }
        composable("telaCompeticaoCriar") { CompeticaoCriar(navController, usuarioViewModel, competicaoViewModel) }
        composable("telaCompeticaoMomento") { CompeticaoMomento(navController) }
        composable("telaCompeticaoRelatorio") { CompeticaoRelatorio(navController) }
        composable("telaEstatisticas") { Estatisticas(navController, usuarioViewModel) }
        composable("telaCalendario") { Calendario(navController) }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AppPreview() {
    Bloqueio(rememberNavController())
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