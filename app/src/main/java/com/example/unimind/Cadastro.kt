package com.example.unimind

import com.example.unimind.viewmodel.LoginResult
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.unimind.ui.theme.Bege
import com.example.unimind.ui.theme.Nude
import com.example.unimind.ui.theme.UnimindTheme
import com.example.unimind.ui.theme.Vinho
import com.example.unimind.viewmodel.UsuarioViewModel

@Composable
fun Cadastro(navController: NavController, viewModel: UsuarioViewModel) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var senhaDnv by remember { mutableStateOf("") }

    val interFont = FontFamily(Font(R.font.inter))

    UnimindTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Nude
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CadastroHeader()

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Cadastrar",
                    fontFamily = interFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 42.sp,
                    color = Vinho
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Campos de texto
                Column(
                    modifier = Modifier.width(IntrinsicSize.Max),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CustomTextField("Digite seu nome:", nome) { nome = it }
                    CustomTextField("Digite seu email principal:", email) { email = it }
                    CustomPasswordField("Insira uma senha forte:", senha) { senha = it }
                    CustomPasswordField("Repita a senha:", senhaDnv) { senhaDnv = it }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Botão Cadastrar
                Button(
                    onClick = {
                        if (senha == senhaDnv) {
                            if (nome.isBlank() || email.isBlank() || senha.isBlank()) {
                                viewModel.setMensagem("Preencha todos os campos")
                            } else {
                                viewModel.criarUsuario(nome, email, senha) {
                                    // Após o sucesso, o login é feito automaticamente
                                    viewModel.verificarLogin(nome, senha)
                                }
                            }
                        } else {
                            viewModel.setMensagem("As senhas não coincidem")
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Bege,
                        contentColor = Vinho
                    ),
                    border = BorderStroke(1.dp, Vinho),
                    modifier = Modifier
                        .width(280.dp)
                        .height(48.dp)
                ) {
                    Text(text = "Cadastrar", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Link para Login
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Não tem uma conta?",
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                    TextButton(onClick = { navController.navigate("telaEntrar") }) {
                        Text(
                            text = "Faça login",
                            color = Vinho,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Observa o status do login para navegar após o cadastro
                val loginStatus by viewModel.loginStatus
                if (loginStatus is LoginResult.Sucesso) {
                    LaunchedEffect(Unit) {
                        navController.navigate("telaInicial") {
                            popUpTo("telaCadastro") { inclusive = true }
                            popUpTo("telaBloqueio") { inclusive = true }
                        }
                        viewModel.limparLoginStatus()
                    }
                }
            }
        }
    }
}

@Composable
private fun CadastroHeader() {
    val cuteFontFamily = FontFamily(Font(R.font.cute_letters))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
            .background(Vinho),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "UNIMIND",
                fontFamily = cuteFontFamily,
                color = Nude,
                fontSize = 50.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = R.drawable.bichinho),
                contentDescription = "Mascote Unimind",
                modifier = Modifier.size(60.dp)
            )
        }
    }
}

@Composable
private fun CustomTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = Vinho,
            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .width(280.dp)
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedBorderColor = Vinho.copy(alpha = 0.7f),
                focusedBorderColor = Vinho,
                cursorColor = Vinho
            ),
            singleLine = true,
        )
    }
}

@Composable
private fun CustomPasswordField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = Vinho,
            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .width(280.dp)
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedBorderColor = Vinho.copy(alpha = 0.7f),
                focusedBorderColor = Vinho,
                cursorColor = Vinho
            ),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CadastroPreview() {
    val navController = rememberNavController()
    val viewModel: UsuarioViewModel = viewModel()
    Cadastro(navController = navController, viewModel = viewModel)
}