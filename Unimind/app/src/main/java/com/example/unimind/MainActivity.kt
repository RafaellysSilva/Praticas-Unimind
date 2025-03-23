package com.example.unimind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.unimind.ui.theme.Bege
import com.example.unimind.ui.theme.Nude
import com.example.unimind.ui.theme.UnimindTheme
import com.example.unimind.ui.theme.Vinho

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.unimind.ui.theme.Rosinha


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnimindTheme {
                AppNavigation()
            }
        }
    }
}


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "telaBloqueio") {
        composable("telaBloqueio") { TelaBloqueio(navController) }
        composable("telaCadastro") { Cadastro(navController) }
        composable("telaEntrar") { Entrar(navController) }
        composable("telaFlashCardsArea") { FlashcardsArea(navController) }
        composable("telaFlashCardsPergunta") { FlashcardsPergunta(navController) }
        composable("telaFlashcardsResposta") { FlashcardsResposta(navController) }
    }
}


//função para chamar (tem várias telas que tem a mesma parte como o footer, fica mais facil assim)
@Composable
fun Footer() {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Bottom,
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Row(
                Modifier
                    .height(55.dp)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(Vinho),
            ) {
                Image(
                    painterResource(id = R.drawable.flashcards),
                    contentDescription = null,
                )

                Spacer(
                    Modifier
                        .padding(start = 10.dp)
                )

                Image(
                    painterResource(id = R.drawable.listasprovas),
                    contentDescription = null,
                )

                Spacer(
                    Modifier
                        .padding(start = 10.dp)
                )

                Image(
                    painterResource(id = R.drawable.competicao),
                    contentDescription = null,
                )

                Spacer(
                    Modifier
                        .padding(start = 10.dp)
                )

                Image(
                    painterResource(id = R.drawable.estatisticas),
                    contentDescription = null,
                )

                Spacer(
                    Modifier
                        .padding(start = 10.dp)
                )

                Image(
                    painterResource(id = R.drawable.bichinho),
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
fun Header(nomePagina: String){
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            Modifier
                .height(220.dp)
                .clip(RoundedCornerShape(bottomStart = 1500.dp, bottomEnd = 1500.dp))
                .background(Bege)
                .fillMaxWidth()
        ) {
            Box(
                Modifier
                    .height(190.dp)
                    .clip(RoundedCornerShape(bottomStart = 1500.dp, bottomEnd = 1500.dp))
                    .background(Rosinha)
                    .fillMaxWidth()
            ) {
                Box(
                    Modifier
                        .height(160.dp)
                        .clip(RoundedCornerShape(bottomStart = 1500.dp, bottomEnd = 1500.dp))
                        .background(Vinho)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = nomePagina,
                        color = Nude,
                        fontSize = 20.sp,
                    )
                }
            }
        }
    }
}


@Composable
fun TelaBloqueio(navController: NavController){
    UnimindTheme{
        Surface (
            modifier = Modifier.fillMaxSize(),
            color = Vinho
        ){
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    Modifier
                        .height(400.dp)
                        .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                        .background(Nude)
                        .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                ) {
                    Image(
                        painterResource(id = R.drawable.bichinho),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(bottom = 250.dp)
                    )

                    Text(
                        text = "UNIMIND",
                        color = Vinho,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(bottom = 100.dp)
                    )

                    Text(
                        text = "Mentalizou, realizou.",
                        color = Vinho,
                        fontSize = 17.sp,
                        modifier = Modifier
                            .padding(bottom = 40.dp)
                    )

                    Column (
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(bottom = 60.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        OutlinedButton (
                            onClick = { navController.navigate("telaCadastro") },
                            border = BorderStroke(2.dp, Vinho),
                            modifier = Modifier
                                .width(200.dp)
                        ) {
                            Text(
                                text = "Cadastrar",
                                color = Vinho
                            )
                        }

                        OutlinedButton (
                            onClick = { navController.navigate("telaEntrar") },
                            border = BorderStroke(2.dp, Vinho),
                            modifier = Modifier
                                .width(200.dp)
                        ) {
                            Text(
                                text = "Entrar",
                                color = Vinho
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Cadastro(navController: NavController) {
    val inter = FontFamily(
        Font(R.font.inter)
    )
    val customFontFamily = FontFamily(
        Font(R.font.cute_letters) // Nome do arquivo sem a extensão .ttf ou .otf
    )
    UnimindTheme{
        //É o cabeçário da página
        Surface (
            modifier = Modifier.fillMaxSize(),
            color = Nude
        ){
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top
            ) {
                Box(
                    //mexer no alinhamento do texto e da imagem
                    Modifier
                        .height(95.dp)
                        .background(Vinho)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center


                ) {
                   Row(
                       verticalAlignment = Alignment.CenterVertically, // Alinha os itens no centro verticalmente
                       horizontalArrangement = Arrangement.Center, // Centraliza na horizontal
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(0.dp, 15.dp, 0.dp, 0.dp)
                   ) {
                       Text(
                           text = "UNIMIND",
                           fontFamily = customFontFamily,
                           color = Nude,
                           fontSize = 50.sp,
                           /*modifier = Modifier
                               .padding(start = 10.dp)*/
                       )
                       Image(
                           painterResource(id = R.drawable.bichinho),
                           contentDescription = null,
                           modifier = Modifier
                               .padding(start = 5.dp)
                               .width(80.dp)
                               .height(80.dp)
                       )
                   }
                   }

            }
            //é o corpo da página
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    Modifier
                        .height(650.dp)
                        .clip(RoundedCornerShape(topStart = 45.dp, topEnd = 50.dp))
                        .background(Nude)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column {
                        Text(
                            text = "Cadastro",
                            fontFamily = inter,
                            fontWeight = FontWeight.Bold,
                            fontSize = 50.sp,
                            color = Color(0xFF741C28),
                            modifier = Modifier.padding(0.dp, 40.dp, 0.dp, 0.dp)
                        )

                        var nome by remember { mutableStateOf("") }
                        var email by remember { mutableStateOf("") }
                        var senha by remember { mutableStateOf("") }
                        var senhaDnv by remember { mutableStateOf("") }

                        Spacer(modifier = Modifier.height(50.dp))
                        Text(text = "Digite seu nome:", fontSize = 20.sp,
                                /*modifier = Modifier. padding(0.dp, 40.dp, 0.dp, 0.dp)*/)
                        OutlinedTextField(
                            value = nome,
                            onValueChange = { nome = it }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Digite seu email:", fontSize = 20.sp)
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Digite sua senha:", fontSize = 20.sp)
                        OutlinedTextField(
                            value = senha,
                            onValueChange = { senha = it }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Digite sua senha:", fontSize = 20.sp)
                        OutlinedTextField(
                            value = senhaDnv,
                            onValueChange = { senhaDnv = it }
                            //fazer um if p se senha!=senhaDnv n deixar cadastrar
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        OutlinedButton(
                            onClick = {null}, //fazer a verificação aq
                            border = BorderStroke(2.dp, Vinho),
                            modifier = Modifier.width(280.dp)
                        ) {
                            Text(text = "Cadastrar", color = Vinho, fontSize = 20.sp)
                        }
                    }

                }
                Box(contentAlignment = Alignment.TopCenter,
                    modifier = Modifier.height(140.dp)) {
                    Column {
                        Row{
                            Text(
                                text = "Já tem uma conta?",
                                fontSize = 20.sp
                            )
                        }
                        Row{
                            OutlinedButton(
                                onClick = {navController.navigate("telaEntrar")},
                                border = null
                            ) {
                                Text(text = "   Faça login", color = Vinho, fontSize = 20.sp)
                                //esse text nao ta ficando no meio, por isso os espaços
                            }
                        }

                    }
                }
            }
        }

    }
}


@Composable
fun Entrar(navController: NavController){
    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val cuteFont = FontFamily(
        Font(R.font.cute_letters) // Nome do arquivo sem a extensão .ttf ou .otf
    )

    val inter = FontFamily(
        Font(R.font.inter)
    )

    UnimindTheme{
        //É o cabeçário da página
        Surface (
            modifier = Modifier.fillMaxSize(),
            color = Nude
        ){
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top
            ) {
                Box(
                    //mexer no alinhamento do texto e da imagem
                    Modifier
                        .height(95.dp)
                        .background(Vinho)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center


                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically, // Alinha os itens no centro verticalmente
                        horizontalArrangement = Arrangement.Center, // Centraliza na horizontal
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 15.dp, 0.dp, 0.dp)
                    ) {
                        Text(
                            text = "UNIMIND",
                            fontFamily = cuteFont,
                            color = Nude,
                            fontSize = 50.sp,
                            /*modifier = Modifier
                                .padding(start = 10.dp)*/
                        )
                        Image(
                            painterResource(id = R.drawable.bichinho),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 5.dp)
                                .width(80.dp)
                                .height(80.dp)
                        )
                    }
                }

            }
            //é o corpo da página
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    Modifier
                        .height(650.dp)
                        .clip(RoundedCornerShape(topStart = 45.dp, topEnd = 50.dp))
                        .background(Nude)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter
                    
                ) {
                    Column {
                        Text(
                        text = "Login",
                        fontFamily = inter,
                        fontWeight = FontWeight.Bold,
                        fontSize = 50.sp,
                        color = Color(0xFF741C28),
                        modifier = Modifier.padding(0.dp, 40.dp, 0.dp, 0.dp)
                        //color = R.color.ic_launcher_background
                        )
                        Spacer(
                            modifier = Modifier.height(50.dp)
                        )

                         //permite que a caixa de texto mantenha e atualize o valor
                        //digitado pelo usuário dinamicamente.

                        Text(text = "Digite seu usuário:", fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(3.dp))
                        OutlinedTextField(
                            value = user,      //o valor q o usuário digitar será armazenado na var texto
                            onValueChange = { user = it },  //atualiza o valor da variável texto sempre que o usuário digitar algo.

                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(text = "Digite sua senha:", fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(3.dp))
                        OutlinedTextField(
                            value = password,
                            onValueChange = {password = it}
                        )

                        Spacer(modifier = Modifier.height(50.dp))

                        OutlinedButton (
                            onClick = { navController.navigate("telaBloqueio") },
                            border = BorderStroke(2.dp, Vinho),

                            modifier = Modifier
                                .width(280.dp)
                            )
                            {
                                Text(text = "Entrar",
                                color = Vinho,
                                fontSize = 20.sp)
                            }
                    }

                }
                Box(contentAlignment = Alignment.TopCenter,
                    modifier = Modifier.height(140.dp)) {
                    Column {
                        Row{
                            Text(
                                text = "Não tem uma conta?",
                                fontSize = 20.sp
                            )
                        }
                        Row{
                            OutlinedButton(
                                onClick = {navController.navigate("telaCadastro")},
                                border = null
                            ) {
                                Text(text = "   Cadastre-se", color = Vinho, fontSize = 20.sp)
                                //esse text nao ta ficando no meio, por isso os espaços
                            }
                        }

                    }
                }


            }
        }
    }
}


@Composable
fun FlashcardsArea(navController: NavController) {
    UnimindTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Nude
        ) {
            Header("Flashcards")

            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    Modifier
                        .padding(start = 50.dp),
                ) {
                    Box(
                        Modifier
                            .height(40.dp)
                            .width(200.dp)
                            .clip(RoundedCornerShape(60.dp))
                            .background(Vinho)
                            .fillMaxWidth(),
                    ) {
                        Text(
                            text = "Pesquisar...",
                            color = Nude,
                            fontSize = 15.sp,
                        )
                    }

                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Vinho),
                        onClick = { navController.navigate("telaFlashcardsPergunta") },
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .height(40.dp)
                            .width(50.dp)
                            .clip(RoundedCornerShape(60.dp))
                            .fillMaxWidth(),
                    ) {
                        Text(
                            text = "+",
                            color = White,
                            fontSize = 20.sp,
                        )
                    }
                }

                Spacer(
                    Modifier
                        .padding(20.dp)
                )

                Row (
                    Modifier
                        .padding(start = 50.dp)
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Rosinha),
                        onClick = { navController.navigate("telaFlashcardsPergunta") },
                        modifier = Modifier
                            .height(80.dp)
                            .width(140.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .fillMaxWidth(),
                    ) {
                        Text(
                            text = "Titulo\nCategoria",
                            color = White,
                            fontSize = 17.sp,
                        )
                    }

                    Spacer(
                        Modifier
                            .padding(10.dp)
                    )

                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Rosinha),
                        onClick = { navController.navigate("telaFlashcardsPergunta") },
                        modifier = Modifier
                            .height(80.dp)
                            .width(140.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .fillMaxWidth(),
                    ) {
                        Text(
                            text = "Titulo\nCategoria",
                            color = White,
                            fontSize = 17.sp,
                        )
                    }
                }
            }

            Footer()
        }
    }
}


@Composable
fun FlashcardsPergunta(navController: NavController) {
    UnimindTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Vinho
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center
            ){
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Pergunta:",
                        color = Nude,
                        fontSize = 35.sp
                    )
                }

                Spacer(
                    Modifier
                        .padding(20.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        Modifier
                            .height(300.dp)
                            .width(300.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Nude)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {}
                }

                Spacer(
                    Modifier
                        .padding(20.dp)
                )

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(end = 40.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Nude),
                        onClick = { navController.navigate("telaFlashcardsResposta") },
                        modifier = Modifier
                            .width(200.dp)
                            .height(60.dp)
                    ) {
                        Text(
                            text = "Resposta",
                            color = Vinho,
                            fontSize = 20.sp
                        )
                        Image(
                            painterResource(id = R.drawable.baseline_arrow_forward_ios_vinho),
                            contentDescription = null,
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun FlashcardsResposta(navController: NavController) {
    UnimindTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Nude
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center
            ){
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Resposta:",
                        color = Vinho,
                        fontSize = 35.sp
                    )
                }

                Spacer(
                    Modifier
                        .padding(20.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        Modifier
                            .height(300.dp)
                            .width(300.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Vinho)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {}
                }

                Spacer(
                    Modifier
                        .padding(20.dp)
                )

                Row(
                    Modifier
                        .padding(start = 40.dp, end = 40.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Vinho),
                        onClick = { navController.navigate("telaFlashcardsPergunta") },
                        modifier = Modifier
                            .width(150.dp)
                            .height(60.dp)
                    ) {
                        Image(
                            painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                            contentDescription = null,
                        )
                        Text(
                            text = "Voltar",
                            color = Nude,
                            fontSize = 20.sp
                        )
                    }

                    Spacer(
                        Modifier
                            .padding(10.dp)
                    )

                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Vinho),
                        onClick = { navController.navigate("telaFlashcardsResposta") },
                        modifier = Modifier
                            .width(150.dp)
                            .height(60.dp)
                    ) {
                        Text(
                            text = "Próximo",
                            color = Nude,
                            fontSize = 20.sp
                        )
                        Image(
                            painterResource(id = R.drawable.baseline_navigate_next_24),
                            contentDescription = null,
                        )
                    }
                }

                Spacer(
                    Modifier
                        .padding(20.dp)
                )

                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Vinho),
                        onClick = { navController.navigate("telaFlashcards") },
                        modifier = Modifier
                            .width(150.dp)
                            .height(60.dp)
                    ) {
                        Text(
                            text = "Terminar",
                            color = Nude,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun AppPreview(){
    TelaBloqueio(rememberNavController())
}