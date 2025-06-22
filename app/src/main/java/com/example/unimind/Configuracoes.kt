package com.example.unimind

import Usuario
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.unimind.ui.theme.Bege
import com.example.unimind.ui.theme.Nude
import com.example.unimind.ui.theme.UnimindTheme
import com.example.unimind.ui.theme.Vinho
import com.example.unimind.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Configuracoes(navController: NavController, viewModel: UsuarioViewModel) {
    val usuario by viewModel.usuarioDetalhe
    val context = LocalContext.current

    var nome by remember(usuario) { mutableStateOf(usuario?.nome ?: "") }
    var email by remember(usuario) { mutableStateOf(usuario?.email ?: "") }
    var senha by remember { mutableStateOf("") }
    var nivel by remember(usuario) { mutableStateOf(usuario?.idNivel?.toString() ?: "Não definido") }

    var dificuldade by remember { mutableStateOf("Média") }
    var facilidade by remember { mutableStateOf("Normal") }
    var tempoListas by remember { mutableStateOf("30 minutos") }

    UnimindTheme {
        Scaffold(
            containerColor = Nude,
            topBar = {
                ConfigTopAppBar(usuario?.nome ?: "Usuário")
            },
            bottomBar = {
                Footer(navController = navController)
            }
        ) { innerPadding ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                // Imagem de Perfil Estática
                Box(
                    modifier = Modifier
                        .padding(top = innerPadding.calculateTopPadding() + 16.dp), // Ajuste o valor para a posição desejada
                    contentAlignment = Alignment.Center
                ) {
                    ProfileImageWithEdit()
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(top = 180.dp) // Espaço para a imagem e um pouco mais
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = usuario?.nome ?: "Usuário",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Vinho,
                        modifier = Modifier.padding(top = 70.dp) // Espaçamento abaixo da imagem
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    SectionTitle("Perfil")
                    ConfigSection {
                        InfoRow(label = "Usuário", value = nome, onValueChange = { nome = it })
                        InfoRow(label = "Nome", value = nome, onValueChange = { nome = it })
                        InfoRow(label = "E-mail", value = email, onValueChange = { email = it })
                        InfoRow(label = "Senha", value = senha, onValueChange = { senha = it }, isPassword = true)
                        InfoRow(label = "Nível", value = nivel, onValueChange = {}, isEditable = false)
                    }

                    SectionTitle("Preferências")
                    ConfigSection {
                        PreferenceRow(
                            label = "Dificuldade",
                            selectedValue = dificuldade,
                            options = listOf("Fácil", "Média", "Difícil"),
                            onSelectionChanged = { dificuldade = it }
                        )
                        PreferenceRow(
                            label = "Facilidade",
                            selectedValue = facilidade,
                            options = listOf("Baixa", "Normal", "Alta"),
                            onSelectionChanged = { facilidade = it }
                        )
                        PreferenceRow(
                            label = "Tempo em resolução de listas",
                            selectedValue = tempoListas,
                            options = listOf("15 minutos", "30 minutos", "45 minutos", "60 minutos"),
                            onSelectionChanged = { tempoListas = it }
                        )
                    }

                    SectionTitle("Outros")
                    ConfigSection {
                        OtherOptionRow(text = "Excluir conta") {
                            viewModel.usuarioDetalhe.value?.idUsuario?.let { id ->
                                viewModel.deletarUsuario(id)
                                Toast.makeText(context, "Conta excluída", Toast.LENGTH_SHORT).show()
                                navController.navigate("telaBloqueio") {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                }
                            }
                        }
                        OtherOptionRow(text = "Nosso contato") {
                            Toast.makeText(context, "unimind@gmail.com", Toast.LENGTH_SHORT).show()
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            viewModel.usuarioDetalhe.value?.let { currentUser ->
                                viewModel.atualizarUsuario(
                                    nome = nome,
                                    email = email,
                                    senha = if (senha.isNotBlank()) senha else currentUser.senha,
                                    idNivel = nivel.toIntOrNull() ?: currentUser.idNivel
                                )
                                Toast.makeText(context, "Alterações salvas!", Toast.LENGTH_SHORT).show()
                            }
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
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ConfigTopAppBar(userName: String) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { /* Ação de voltar se necessário */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.bichinho),
                        contentDescription = "Mascote",
                        modifier = Modifier.size(40.dp),
                        tint = Color.Unspecified
                    )
                }
                Text("Configurações", color = Nude, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.engrenagem),
                        contentDescription = "Ícone de configurações",
                        tint = Nude,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Vinho)
    )
}

@Composable
private fun ProfileImageWithEdit() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(120.dp)) {
        Image(
            painter = painterResource(id = R.drawable.user),
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .border(BorderStroke(4.dp, Nude), CircleShape)
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(4.dp)
                .size(32.dp)
                .clip(CircleShape)
                .background(Vinho)
                .border(BorderStroke(2.dp, Nude), CircleShape)
                .clickable { /* Ação de editar foto */ },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Editar foto",
                tint = Nude,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Vinho,
        modifier = Modifier
            .fillMaxWidth()
            .background(Bege)
            .padding(vertical = 8.dp, horizontal = 16.dp)
    )
}

@Composable
private fun ConfigSection(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        content = content
    )
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isEditable: Boolean = true,
    isPassword: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.Bold, color = Vinho)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = isEditable,
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            trailingIcon = { if (!isEditable) Icon(Icons.Default.Lock, contentDescription = "Bloqueado") },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.LightGray.copy(alpha = 0.5f),
                focusedContainerColor = Color.White,
                disabledContainerColor = Color.LightGray.copy(alpha = 0.5f),
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Vinho
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.width(200.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PreferenceRow(
    label: String,
    selectedValue: String,
    options: List<String>,
    onSelectionChanged: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.Bold, color = Vinho)
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedValue,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null) },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.LightGray.copy(alpha = 0.5f),
                    focusedContainerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Vinho
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .menuAnchor()
                    .width(200.dp)
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onSelectionChanged(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun OtherOptionRow(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        color = Vinho,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp)
    )
}