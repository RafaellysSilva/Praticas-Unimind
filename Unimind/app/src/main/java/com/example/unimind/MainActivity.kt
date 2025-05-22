package com.example.unimind


import android.annotation.SuppressLint
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.unimind.ui.theme.Bege
import com.example.unimind.ui.theme.Nude
import com.example.unimind.ui.theme.UnimindTheme
import com.example.unimind.ui.theme.Vinho
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.unimind.ui.theme.Rosinha
import com.example.usuarioapp.viewmodel.UsuarioViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.reflect.Array.set
import java.text.DateFormatSymbols
import java.util.Calendar


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
        composable("telaBloqueio") { Bloqueio(navController) }
        composable("telaCadastro") { Cadastro(navController) }
        composable("telaEntrar") { Entrar(navController) }
        composable("telaInicial") { Inicial(navController) }
        composable("telaConfiguracoes") { Configuracoes(navController) }
        composable("telaFlashCardsArea") { FlashcardsArea(navController) }
        composable("telaFlashCardsPergunta") { FlashcardsPergunta(navController) }
        composable("telaFlashcardsResposta") { FlashcardsResposta(navController) }
        composable("telaListasProvasArea") { ListasProvasArea(navController) }
        composable("telaListaProvaResolucao") { ListaProvaResolucao(navController) }
        composable("telaListaPersonalizadaCriar") { ListaPersonalizadaCriar(navController) }
        composable("telaCompeticaoCriar") { CompeticaoCriar(navController) }
        composable("telaCompeticaoMomento") { CompeticaoMomento(navController) }
        composable("telaEstatisticas") { Estatisticas(navController) }
        composable("telaCalendario") { Calendario(navController) }
    }
}


//função para chamar (tem várias telas que tem a mesma parte como o footer, fica mais facil assim)
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
                    contentAlignment = Center
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
fun Footer(navController: NavController) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 50.dp),
        verticalArrangement = Arrangement.Bottom,
    ) {
        Box(
            contentAlignment = Center
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
                    modifier = Modifier
                        .clickable { navController.navigate("telaFlashcardsArea") }
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
                    modifier = Modifier
                        .clickable(onClick = {navController.navigate("telaInicial")})
                )
            }
        }
    }
}



@Composable
fun Bloqueio(navController: NavController){
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
                    contentAlignment = Center
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
                    contentAlignment = Center

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
                    Column(/*modifier = Modifier.background(color = Color.Blue)*/) {
                        Text(
                            text = "Cadastro",
                            fontFamily = inter,
                            fontWeight = FontWeight.Bold,
                            fontSize = 50.sp,
                            //background = null,
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
                            onClick = {navController.navigate("telaInicial")}, //fazer a verificação aq
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
fun Entrar(navController: NavController, viewModel: UsuarioViewModel = viewModel()){

    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var erroLogin by remember { mutableStateOf("") }

    val usuarios by viewModel.usuarios
    var novoNome by remember { mutableStateOf("") }
    var usuarioIdBusca by remember { mutableStateOf("") }
    var usuarioIdAtualizar by remember { mutableStateOf("") }
    var novoNomeAtualizar by remember { mutableStateOf("") }
    var usuarioIdDeletar by remember { mutableStateOf("") }
    val usuarioDetalhe by viewModel.usuarioDetalhe


//    fun verificarLogin(nome: String, senha: String, onSuccess: () -> Unit, onError: () -> Unit) {
//        val url = "http://10.0.2.2:5000/login/$nome/$senha" // use 10.0.2.2 no emulador Android
//        //val url = "http://localhost:5133/usuarios"
//
//        val client = OkHttpClient()
//        val request = Request.Builder().url(url).build()
//
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val response = client.newCall(request).execute()
//                val body = response.body?.string()
//
//
//                if (response.isSuccessful && body?.contains("Login válido") == true) {
//                    withContext(Dispatchers.Main) {
//                        onSuccess()
//                    }
//                } else {
//                    withContext(Dispatchers.Main) {
//                        onError()
//                    }
//                }
//            } catch (e: Exception) {
//                withContext(Dispatchers.Main) {
//                    onError()
//                }
//            }
//        }
//    }

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
                    contentAlignment = Center
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

                        //TEM QUE COLOCAR A FUNÇÃO VERIFICAR LOGIN AQUI NESSE BOTÃO
                        OutlinedButton (
                            onClick = {
                                if (user.isNotEmpty() && password.isNotEmpty()) {
                                    verificarLogin(user, password)
                                }
                            },
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

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun verificarLogin(user: String, password: String) {
    //acho q vai ter q pegar o user e o password e verificar
    val usuarioViewModel = UsuarioViewModel()

    usuarioViewModel.buscarUsuario(user.toString(), password.toString()) { encontrado ->
        if (encontrado) {
            // Usuário encontrado
        } else {
            // Usuário não encontrado
        }
    }


}

//@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Configuracoes(navController: NavController) {
    val inter = FontFamily(
        Font(R.font.inter)
    )
    UnimindTheme {
        //É o cabeçário da página
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Nude
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                //horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    //mexer no alinhamento do texto e da imagem
                    Modifier
                        .height(95.dp)
                        .background(Vinho)
                        .fillMaxWidth(),
                    contentAlignment = Center

                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically, // Alinha os itens no centro verticalmente
                        //horizontalArrangement = Arrangement.Center, // Centraliza na horizontal
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 15.dp, 0.dp, 0.dp)
                    ) {
                        Image(
                            painterResource(id = R.drawable.bichinho),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 5.dp, end = 170.dp)
                                .width(50.dp)
                                .height(50.dp)
                        )
                        Text(
                            text = "Configurações",
                            fontFamily = inter,
                            color = White,
                            fontSize = 20.sp,
                            /*modifier = Modifier
                                .padding(start = 10.dp)*/
                        )
                        Image(
                            painterResource(id = R.drawable.engranagem),
                            contentDescription = null,
                            modifier = Modifier
                                .height(30.dp)
                                .width(30.dp)
                                .padding(start = 10.dp)
                        )
                    }
                    Box(
                        Modifier
                            .padding(top = 40.dp)
                            .fillMaxWidth()
                    ) {
                        Image(
                            painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = null,
                            modifier = Modifier
                                .clip(shape = CircleShape)
                                .align(BottomCenter)
                                .background(Blue)
                        )
                    }
                }
                //corpo da tela
                Spacer(Modifier.height(70.dp))
                Box{
                    Column{
                        Text(text = "Perfil",
                            fontSize = 23.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFf3d1c2))
                                .padding(top = 5.dp, bottom = 5.dp, start = 10.dp)
                        )
                        Spacer(Modifier.height(15.dp))

                        Row{
                            var user by remember { mutableStateOf("") }
                            Text("Usuário:", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(start = 10.dp)
                            )
                            OutlinedTextField(
                                value = user,
                                onValueChange = {user = it},
                                modifier = Modifier
                                    .width(280.dp)
                                    .height(10.dp)
                                    .padding(start = 20.dp, top = 10.dp)
                            )
                        }

                        Spacer(Modifier.height(15.dp))

                        Row{
                            var nome by remember { mutableStateOf("") }
                            Text("Nome:", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 17.dp)
                            )
                            OutlinedTextField(
                                value = nome,
                                onValueChange = {nome = it},
                                modifier = Modifier
                                    .width(280.dp)
                                    .height(10.dp)
                                    .padding(start = 20.dp, top = 10.dp)
                            )
                        }

                        Spacer(Modifier.height(15.dp))

                        Row{
                            var email by remember { mutableStateOf("") }
                            Text("E-mail:", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 12.dp)
                            )
                            OutlinedTextField(
                                value = email,
                                onValueChange = {email = it},
                                modifier = Modifier
                                    .width(280.dp)
                                    .height(10.dp)
                                    .padding(start = 20.dp, top = 10.dp)
                            )
                        }

                        Spacer(Modifier.height(15.dp))

                        Row{
                            var senha by remember { mutableStateOf("") }
                            Text("Senha:", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 14.dp)
                            )
                            OutlinedTextField(
                                value = senha,
                                onValueChange = {senha = it},
                                modifier = Modifier
                                    .width(280.dp)
                                    .height(10.dp)
                                    .padding(start = 20.dp, top = 10.dp)
                            )
                        }

                        Spacer(Modifier.height(15.dp))

                        Row{
                            var nivel by remember { mutableStateOf("") }
                            Text("Nível:", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 24.dp)
                            )
                            OutlinedTextField(
                                value = nivel,
                                readOnly = true,
                                onValueChange = {nivel = it},
                                modifier = Modifier
                                    .width(280.dp)
                                    .height(10.dp)
                                    .padding(start = 20.dp, top = 10.dp)
                            )
                        }
                        Spacer(Modifier.height(20.dp))
                        Text(text = "Preferências",
                            fontSize = 23.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFf3d1c2))
                                .padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
                        )
                        Spacer(Modifier.height(15.dp))

                        Row{
                            Text("Dificuldade:", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 14.dp, top = 3.dp)
                            )
                            var expanded by remember { mutableStateOf(false) }
                            var selecionada by remember { mutableStateOf("") }
                            val opcoes = listOf("Opção 1", "Opção 2", "Opção 3")
                            ExposedDropdownMenuBox(
                                expanded = expanded,
                                onExpandedChange = { expanded = !expanded }
                            ) {
                                OutlinedTextField(
                                    value = selecionada,
                                    onValueChange = {},
                                    readOnly = true,
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                    modifier = Modifier
                                        .menuAnchor() // Necessário para alinhar o menu corretamente
                                        .height(30.dp)
                                        .width(250.dp)
                                )

                                ExposedDropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                    //Modifier.height(50.dp)
                                ) {
                                    opcoes.forEach { opcao ->
                                        DropdownMenuItem(
                                            text = { Text(opcao) },
                                            onClick = {
                                                selecionada = opcao
                                                expanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(Modifier.height(15.dp))
                        Row{
                            Text("Facilidade:", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 23.dp, top = 3.dp)
                            )
                            var expanded by remember { mutableStateOf(false) }
                            var selecionada by remember { mutableStateOf("") }
                            val opcoes = listOf("Opção 1", "Opção 2", "Opção 3")
                            ExposedDropdownMenuBox(
                                expanded = expanded,
                                onExpandedChange = { expanded = !expanded }
                            ) {
                                OutlinedTextField(
                                    value = selecionada,
                                    onValueChange = {},
                                    readOnly = true,
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                    modifier = Modifier
                                        .menuAnchor() // Necessário para alinhar o menu corretamente
                                        .height(30.dp)
                                        .width(250.dp)
                                )

                                ExposedDropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                    //Modifier.height(50.dp)
                                ) {
                                    opcoes.forEach { opcao ->
                                        DropdownMenuItem(
                                            text = { Text(opcao) },
                                            onClick = {
                                                selecionada = opcao
                                                expanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(8.dp))
                        Row{
                            var minutos by remember { mutableStateOf("") }
                            Text("Tempo em \nresolução\nde listas:", fontSize = 15.sp, fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 14.dp, top = 3.dp)
                            )
                            OutlinedTextField(
                                value = minutos,
                                onValueChange = {minutos = it},
                                modifier = Modifier
                                    .height(30.dp)
                                    .width(281.dp)
                                    .padding(top = 40.dp, start = 32.dp)
                            )
                        }

                        Spacer(Modifier.height(20.dp))
                        Text(text = "Outros",
                            fontSize = 23.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFf3d1c2))
                                .padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
                        )

                        Spacer(Modifier.height(20.dp))

                        Row{
                            Text("Excluir conta:", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 23.dp, top = 8.dp)
                            )
                            OutlinedButton (
                                onClick = { print("Clicou no excluir") },
                                //border = BorderStroke(2.dp, Vinho),
                                modifier = Modifier
                                    .width(200.dp)
                                    .height(40.dp)
                            ) {
                                Image(
                                    painterResource(id = R.drawable.lixeira),
                                    contentDescription = null
                                )
                            }
                        }

                        Spacer(Modifier.height(10.dp))

                        Row{
                            Text("Nosso contato:", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 23.dp, top = 8.dp)
                            )
                            Text(text = "email@example.com", fontSize = 20.sp, modifier = Modifier
                                .padding(top = 7.dp))
                        }

                        Spacer(Modifier.height(15.dp))

                        Row(
                            Modifier.padding(start = 120.dp)
                        ){
                            OutlinedButton(
                                onClick = {}
                            ) {
                                Text(text = "Salvar alterações", color = Black)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Inicial(navController: NavController){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Nude
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            //horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                //mexer no alinhamento do texto e da imagem
                Modifier
                    .height(95.dp)
                    .background(Vinho)
                    .fillMaxWidth(),
                contentAlignment = Center

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically, // Alinha os itens no centro verticalmente
                    //horizontalArrangement = Arrangement.Center, // Centraliza na horizontal
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 15.dp, 0.dp, 0.dp)
                ) {
                    Image(
                        painterResource(id = R.drawable.bichinho),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 5.dp, end = 150.dp)
                            .width(70.dp)
                            .height(70.dp)
                    )
                    Text(
                        text = "Olá, usuário",
                        color = White,
                        fontSize = 20.sp,
                        /*modifier = Modifier
                            .padding(start = 10.dp)*/
                    )
                    Image(
                        painterResource(id = R.drawable.user), contentDescription = null

                    )
                }
            }
            //corpo
            Box(contentAlignment = Center
            ){
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ){
                    Row(modifier = Modifier.padding(top = 55.dp)){
                        Box(modifier = Modifier
                            .clickable(onClick = { navController.navigate("telaConfiguracoes") })
                            .background(Color(0xFFBB8C94))
                            .padding(top = 24.dp, bottom = 24.dp, start = 10.dp, end = 10.dp)
                        )
                        {
                            Image(painterResource(id = R.drawable.config), contentDescription = null,
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                            )
                            Text("Configurações", color = White, modifier = Modifier.padding(start = 25.dp))
                        }
                        Spacer(Modifier.width(15.dp))
                        Box(modifier = Modifier
                            .background(Color(0xFFBB8C94))
                            .padding(top = 24.dp, bottom = 24.dp, start = 22.dp, end = 22.dp)) {
                            Image(
                                painterResource(id = R.drawable.calendario), contentDescription = null,
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                                    .padding(bottom = 4.dp)
                            )
                            Text(
                                "Calendário",
                                color = White,
                                modifier = Modifier.padding(start = 25.dp)
                            )
                        }
                    }
                    Row(modifier = Modifier.padding(top = 60.dp)){
                        Box(modifier = Modifier
                            .background(Color(0xFFBB8C94))
                            .padding(top = 20.dp, bottom = 20.dp, start = 24.dp, end = 50.dp)){
                            Image(painterResource(id = R.drawable.cards), contentDescription = null,
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(40.dp)
                            )}
                        Box(modifier = Modifier
                            .background(Color(0xFFBB8C94))
                            .padding(top = 30.dp, bottom = 33.5.dp, end = 75.dp)){
                            Text("FlashCards", color = White, modifier = Modifier.padding(start = 25.dp))
                        }
                    }
                    Row(modifier = Modifier.padding(top = 40.dp)){
                        Box(modifier = Modifier
                            .background(Color(0xFFBB8C94))
                            .padding(top = 20.dp, bottom = 20.dp, start = 23.dp, end = 50.dp)){
                            Image(painterResource(id = R.drawable.tempo), contentDescription = null,
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(40.dp)
                            )}

                        Box(modifier = Modifier
                            .background(Color(0xFFBB8C94))
                            .padding(top = 30.dp, bottom = 33.5.dp, end = 53.dp)){
                            Text("Listas e provas", color = White, modifier = Modifier.padding(start = 28.dp))
                        }
                    }
                    Row(modifier = Modifier.padding(top = 40.dp)){
                        Box(modifier = Modifier
                            .background(Color(0xFFBB8C94))
                            .padding(top = 20.dp, bottom = 20.dp, start = 22.dp, end = 50.dp)){
                            Image(painterResource(id = R.drawable.competdois), contentDescription = null,
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(40.dp)
                            )}

                        Box(modifier = Modifier
                            .background(Color(0xFFBB8C94))
                            .padding(top = 30.dp, bottom = 33.5.dp, end = 72.dp, start = 2.dp)){
                            Text("Competição", color = White, modifier = Modifier.padding(start = 25.dp))
                        }
                    }
                    Row(modifier = Modifier.padding(top = 40.dp)){
                        Box(modifier = Modifier
                            .background(Color(0xFFBB8C94))
                            .padding(top = 20.dp, bottom = 20.dp, start = 24.dp, end = 55.dp)){
                            Image(painterResource(id = R.drawable.estadois), contentDescription = null,
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(40.dp)
                            )}

                        Box(modifier = Modifier
                            .background(Color(0xFFBB8C94))
                            .padding(top = 30.dp, bottom = 33.5.dp, end = 67.dp, start = 2.dp)){
                            Text("Estatísticas", color = White, modifier = Modifier.padding(start = 27.dp))
                        }
                    }
                }
            }
        }
        Footer(rememberNavController())
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
                        .padding(start = 50.dp)
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
            Footer(rememberNavController())
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
                        contentAlignment = Center
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
                        contentAlignment = Center
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


@Composable
fun ListasProvasArea(navController: NavController) {
    UnimindTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Nude
        ) {
            Header("Provas e listas")

            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    Modifier.padding(start = 50.dp)
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
                        onClick = { navController.navigate("telaCriarLista") },
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .height(40.dp)
                            .width(50.dp)
                            .clip(RoundedCornerShape(60.dp))
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "+",
                            color = White,
                            fontSize = 20.sp
                        )
                    }
                }


                Spacer(
                    Modifier.padding(20.dp)
                )


                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3E0D1)),
                    shape = RoundedCornerShape(17.dp),
                    border = BorderStroke(2.dp, Color(0x66BB8C94)), // AQUI!
                    onClick = { navController.navigate("telaFlashcardsPergunta") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 45.dp)
                        .height(50.dp)
                ) {
                    Text(
                        text = "Provas e listas já realizadas",
                        color = Color(0xFF9A575B),
                        fontSize = 17.sp
                    )
                }


                Spacer(Modifier.padding(20.dp))


                Row(
                    Modifier.padding(start = 50.dp)
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Rosinha),
                        onClick = { navController.navigate("TelaCriarLista") },
                        modifier = Modifier
                            .height(80.dp)
                            .width(140.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .fillMaxWidth(),
                    )  {
                        Text(
                            text = "Título\nCategoria",
                            color = White,
                            fontSize = 17.sp
                        )
                    }


                    Spacer(Modifier
                        .padding(10.dp))


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
        }
        Footer(rememberNavController())
    }
}


@Composable
fun ListaProvaResolucao(navController: NavController) {
    UnimindTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Nude
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Parte superior fixa (Cabeçalho e Cronômetro)
                Column(
                    modifier = Modifier
                        .background(Nude)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        Modifier
                            .height(60.dp)
                            .fillMaxWidth(),
                        contentAlignment = Center
                    ) {
                        Text(
                            text = "Prova A - Conteúdo",
                            color = Vinho,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }


                    // Cronômetro menor abaixo do cabeçalho
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        contentAlignment = Center
                    ) {
                        Text(
                            text = "45:00",
                            color = Vinho,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }


                // Conteúdo principal scrollável (incluindo os botões)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                        .background(Vinho)
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Caixa da questão
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .background(Nude, shape = RoundedCornerShape(16.dp))
                            .padding(14.dp)
                    ) {
                        Text("dsmfkjnfkjhasbdhsvhfbaskdbas")
                    }


                    Spacer(modifier = Modifier.height(16.dp))


                    // Caixa das alternativas
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .background(Nude, shape = RoundedCornerShape(20.dp))
                            .padding(15.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            listOf("A", "B", "C", "D", "E").forEach { alternativa ->
                                Button(
                                    onClick = { /* Ação ao clicar */ },
                                    colors = ButtonDefaults.buttonColors(containerColor = White),
                                    shape = RoundedCornerShape(20.dp),
                                    border = BorderStroke(1.dp, Color(0xFFB9B9B9)),
                                    contentPadding = PaddingValues(0.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(35.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 16.dp),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        Text(
                                            text = alternativa,
                                            color = Black,
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                        }
                    }


                    Spacer(modifier = Modifier.height(32.dp))


                    // Botões de navegação (ESPAÇAMENTO E TAMANHO DAS SETAS AJUSTADOS)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Botão esquerdo (questão anterior)
                        Button(
                            onClick = { /* Anterior */ },
                            colors = ButtonDefaults.buttonColors(Nude),
                            modifier = Modifier.height(50.dp),
                            contentPadding = PaddingValues(all = 8.dp)
                        ) {
                            Box(modifier = Modifier.size(24.dp), contentAlignment = Center) {
                                Image(
                                    painterResource(id = R.drawable.baseline_arrow_forward_ios_vinho),
                                    contentDescription = null,
                                    modifier = Modifier.graphicsLayer { scaleX = -1f }
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Questão anterior",
                                color = Vinho
                            )
                        }


                        Spacer(modifier = Modifier.width(16.dp)) // Espaço entre os botões


                        // Botão direito (próxima questão)
                        Button(
                            onClick = { /* Próxima */ },
                            colors = ButtonDefaults.buttonColors(Nude),
                            modifier = Modifier.height(50.dp),
                            contentPadding = PaddingValues(all = 8.dp)
                        ) {
                            Text(
                                text = "Próxima questão",
                                color = Vinho
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(modifier = Modifier.size(24.dp), contentAlignment = Center) {
                                Image(
                                    painterResource(id = R.drawable.baseline_arrow_forward_ios_vinho),
                                    contentDescription = null
                                )
                            }
                        }
                    }


                    Spacer(modifier = Modifier.height(8.dp))


                    // Botão Terminar
                    Button(
                        onClick = { /* Terminar prova */ },
                        colors = ButtonDefaults.buttonColors(Nude),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 120.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = "Terminar",
                            color = Vinho,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }


                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaPersonalizadaCriar(navController: NavController) {
    UnimindTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Vinho
        ) {
            Header("Criar Lista Personalizada") // O Header agora é o primeiro elemento na Column principal

            Column(
                modifier = Modifier
                    .fillMaxWidth() // A Column das preferências ocupa a largura total
                    .padding(top = 250.dp)
            ) {
                Box(
                    Modifier.fillMaxWidth()
                )
                
                Spacer(Modifier.height(10.dp))

		Row {
                    Text(
                        "Nome:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 35.dp, end = 55.dp, top = 3.dp)
                    )

                    var expanded by remember { mutableStateOf(false) }
                    var selecionada by remember { mutableStateOf("") }
                    val opcoes = listOf("Opção 1", "Opção 2", "Opção 3")
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = selecionada,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .height(30.dp)
                                .width(210.dp)
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            opcoes.forEach { opcao ->
                                DropdownMenuItem(
                                    text = { Text(opcao) },
                                    onClick = {
                                        selecionada = opcao
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(10.dp))

                Row {
                    Text(
                        "Categoria:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 35.dp, end = 23.dp, top = 3.dp)
                    )
                    var expanded by remember { mutableStateOf(false) }
                    var selecionada by remember { mutableStateOf("") }
                    val opcoes = listOf("Opção 1", "Opção 2", "Opção 3")
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = selecionada,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .height(30.dp)
                                .width(210.dp)
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            opcoes.forEach { opcao ->
                                DropdownMenuItem(
                                    text = { Text(opcao) },
                                    onClick = {
                                        selecionada = opcao
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(10.dp))

                Row {
                    Text(
                        "Fonte:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 35.dp, end = 55.dp, top = 3.dp)
                    )

                    var expanded by remember { mutableStateOf(false) }
                    var selecionada by remember { mutableStateOf("") }
                    val opcoes = listOf("Opção 1", "Opção 2", "Opção 3")
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = selecionada,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .height(30.dp)
                                .width(210.dp)
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            opcoes.forEach { opcao ->
                                DropdownMenuItem(
                                    text = { Text(opcao) },
                                    onClick = {
                                        selecionada = opcao
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(10.dp))

		Row {
                    Text(
                        "Ano:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 35.dp, end = 55.dp, top = 3.dp)
                    )

                    var expanded by remember { mutableStateOf(false) }
                    var selecionada by remember { mutableStateOf("") }
                    val opcoes = listOf("Opção 1", "Opção 2", "Opção 3")
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = selecionada,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .height(30.dp)
                                .width(210.dp)
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            opcoes.forEach { opcao ->
                                DropdownMenuItem(
                                    text = { Text(opcao) },
                                    onClick = {
                                        selecionada = opcao
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(10.dp))

                Row {
                    Text(
                        "Tempo:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 35.dp, end = 46.dp, top = 3.dp)
                    )

                    var expanded by remember { mutableStateOf(false) }
                    var selecionada by remember { mutableStateOf("") }
                    val opcoes = listOf("Opção 1", "Opção 2", "Opção 3")
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = selecionada,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .height(30.dp)
                                .width(210.dp)
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            opcoes.forEach { opcao ->
                                DropdownMenuItem(
                                    text = { Text(opcao) },
                                    onClick = {
                                        selecionada = opcao
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(10.dp))

                Row {
                    Text(
                        "Questões:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 35.dp, end = 25.dp, top = 3.dp)
                    )

                    var expanded by remember { mutableStateOf(false) }
                    var selecionada by remember { mutableStateOf("") }
                    val opcoes = listOf("Opção 1", "Opção 2", "Opção 3")
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = selecionada,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .height(30.dp)
                                .width(210.dp)
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            opcoes.forEach { opcao ->
                                DropdownMenuItem(
                                    text = { Text(opcao) },
                                    onClick = {
                                        selecionada = opcao
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(30.dp))

                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3E0D1)),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, Vinho),
                    onClick = { navController.navigate("") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 100.dp)
                        .height(38.dp)
                ) {
                    Text(
                        text = "Criar",
                        color = Vinho,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.height(30.dp))

              }
            Footer(rememberNavController())
        }
    }
}


//@OptIn(ExperimentalMaterial3Api::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompeticaoCriar(navController: NavController) {
    UnimindTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Nude
        ) {
            Header("Competição") // O Header agora é o primeiro elemento na Column principal

            Column(
                modifier = Modifier
                    .fillMaxWidth() // A Column das preferências ocupa a largura total
                    .padding(top = 250.dp)
            ) {
                Box(
                    Modifier.fillMaxWidth()
                )
                Text(
                    text = "Preferências",
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFf3d1c2))
                        .padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
                )

                Spacer(Modifier.height(10.dp))

                Row {
                    Text(
                        "Categoria:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 35.dp, end = 23.dp, top = 3.dp)
                    )
                    var expanded by remember { mutableStateOf(false) }
                    var selecionada by remember { mutableStateOf("") }
                    val opcoes = listOf("Opção 1", "Opção 2", "Opção 3")
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = selecionada,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .height(30.dp)
                                .width(210.dp)
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            opcoes.forEach { opcao ->
                                DropdownMenuItem(
                                    text = { Text(opcao) },
                                    onClick = {
                                        selecionada = opcao
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(10.dp))

                Row {
                    Text(
                        "Fonte:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 35.dp, end = 55.dp, top = 3.dp)
                    )

                    var expanded by remember { mutableStateOf(false) }
                    var selecionada by remember { mutableStateOf("") }
                    val opcoes = listOf("Opção 1", "Opção 2", "Opção 3")
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = selecionada,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .height(30.dp)
                                .width(210.dp)
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            opcoes.forEach { opcao ->
                                DropdownMenuItem(
                                    text = { Text(opcao) },
                                    onClick = {
                                        selecionada = opcao
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(10.dp))

                Row {
                    Text(
                        "Tempo:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 35.dp, end = 46.dp, top = 3.dp)
                    )

                    var expanded by remember { mutableStateOf(false) }
                    var selecionada by remember { mutableStateOf("") }
                    val opcoes = listOf("Opção 1", "Opção 2", "Opção 3")
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = selecionada,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .height(30.dp)
                                .width(210.dp)
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            opcoes.forEach { opcao ->
                                DropdownMenuItem(
                                    text = { Text(opcao) },
                                    onClick = {
                                        selecionada = opcao
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(10.dp))

                Row {
                    Text(
                        "Questões:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 35.dp, end = 25.dp, top = 3.dp)
                    )

                    var expanded by remember { mutableStateOf(false) }
                    var selecionada by remember { mutableStateOf("") }
                    val opcoes = listOf("Opção 1", "Opção 2", "Opção 3")
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = selecionada,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .height(30.dp)
                                .width(210.dp)
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            opcoes.forEach { opcao ->
                                DropdownMenuItem(
                                    text = { Text(opcao) },
                                    onClick = {
                                        selecionada = opcao
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(30.dp))

                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3E0D1)),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, Vinho),
                    onClick = { navController.navigate("") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 100.dp)
                        .height(38.dp)
                ) {
                    Text(
                        text = "Salvar alterações",
                        color = Vinho,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.height(30.dp))

                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(start = 30.dp, end = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Você ainda não está competindo com ninguém. Convide alguém do mesmo nível para apostar questões.",
                        fontSize = 14.sp,
                        lineHeight = 18.sp,
                        color = Vinho,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Usuário para competir",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.widthIn(max = 80.dp)
                        )

                        var nomeUsuario by remember { mutableStateOf("") }

                        BasicTextField(
                            value = nomeUsuario,
                            onValueChange = { nomeUsuario = it },
                            modifier = Modifier
                                .width(240.dp)
                                .height(40.dp)
                                .background(Color(0xFFE0E0E0))
                                .padding(vertical = 8.dp),
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                color = Black
                            ),
                            decorationBox = { innerTextField ->
                                if (nomeUsuario.isEmpty()) {
                                    Text(
                                        text = "Nome do usuário",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            color = Color.Gray
                                        ),
                                        modifier = Modifier.align(Alignment.CenterVertically)
                                    )
                                }
                                innerTextField()
                            }
                        )
                    }

                    Spacer(Modifier.height(10.dp))

                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Rosinha),
                        onClick = { navController.navigate("") },
                        modifier = Modifier
                            .width(190.dp)
                            .height(40.dp)
                    ) {
                        Text(
                            text = "Convidar",
                            color = White,
                            fontSize = 18.sp
                        )
                    }
                }
            }
            Footer(rememberNavController())
        }
    }
}


@Composable
fun CompeticaoMomento(navController: NavController){
    UnimindTheme{
    	Surface(
            modifier = Modifier.fillMaxSize(),
	    color = Rosinha
        ){
           Column(
	   	modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.SpaceBetween,
		horizontalAlignment = Alignment.CenterHorizontally
	   ){
		ChallengeHeader()

		Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.fotouser1), //é "correndo" o nome da imagem, mas não tem essa imagem na pasta e estava dando erro
                            contentDescription = "Competindo",
                            tint = White,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Responda corretamente as\nquestões antes do seu parceiro.",
                            color = White,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

		 // Conteúdo principal scrollável (incluindo os botões)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                        .background(Vinho)
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Caixa da questão
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .background(Nude, shape = RoundedCornerShape(16.dp))
                            .padding(14.dp)
                    ) {
                        Text("dsmfkjnfkjhasbdhsvhfbaskdbas")
                    }


                    Spacer(modifier = Modifier.height(16.dp))


                    // Caixa das alternativas
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .background(Nude, shape = RoundedCornerShape(20.dp))
                            .padding(15.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            listOf("A", "B", "C", "D", "E").forEach { alternativa ->
                                Button(
                                    onClick = { /* Ação ao clicar */ },
                                    colors = ButtonDefaults.buttonColors(containerColor = White),
                                    shape = RoundedCornerShape(20.dp),
                                    border = BorderStroke(1.dp, Color(0xFFB9B9B9)),
                                    contentPadding = PaddingValues(0.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(35.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 16.dp),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        Text(
                                            text = alternativa,
                                            color = Black,
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                        }
                    }


                    Spacer(modifier = Modifier.height(32.dp))


                    // Botão Responder 
                    Button(
                        onClick = { /* Responder */ },
                        colors = ButtonDefaults.buttonColors(Nude),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 120.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = "Responder",
                            color = Vinho,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }


                    Spacer(modifier = Modifier.height(16.dp))
                }
		
		 // Temporizador
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Vinho)
                        .padding(vertical = 24.dp),
                    contentAlignment = Center
                ) {
                    Text(
                        text = "45:00",
                        color = White,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun Estatisticas(navController: NavController){
    UnimindTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Nude
        ){
            Header("Estatísticas")

            Card(
                modifier = Modifier.fillMaxWidth()
                //Colocar as barrinhas de questoes aqui
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(horizontal = 15.dp)
                ){
                    Row(
                        modifier = Modifier.padding(vertical = 15.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(85.dp)
                    ){
                        Text("Questões", fontSize = 25.sp, fontWeight = FontWeight.Bold, color = Black)
                    }

                    Text("Corretas", fontSize = 15.sp, color = Black)

                    Text("Incorretas", fontSize = 15.sp, color = Black)

                    Text("Total:50")
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth()
                //Colocar as cores das barrinhas aqui
            ){
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(horizontal = 15.dp)
                ){
                    Row(
                        modifier = Modifier.padding(vertical = 15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text("Tempo de estudo", fontSize = 25.sp, fontWeight = FontWeight.Bold, color = Black)
                    }

                    Text("Tempo", fontSize = 15.sp, color = Black)

                    Text("Total:50")
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth()
            ){
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(horizontal = 15.dp)
                ){
                    Row(
                        modifier = Modifier.padding(vertical = 15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text("Competições reralizadas", fontSize = 25.sp, fontWeight = FontWeight.Bold, color = Black)
                    }

                    Text("Competições", fontSize = 15.sp, color = Black)

                    Text("Total:50")
                }
            }
            Footer(rememberNavController())
        }
    }
}


@Composable
fun Calendario(navController: NavController){
    UnimindTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Rosinha
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bichinho),
                        contentDescription = "Unimind Logo",
                        modifier = Modifier.size(32.dp)
                    )
                    Text(
                        text = "Calendário",
                        color = Vinho,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Icon(
                        imageVector = Icons.Rounded.CheckCircle,        //NÃO É ESSA IMAGEM, É CalendarMonth, MAS ELA NÃO EXISTE E EU QUERIA TIRAR O ERRO
                        contentDescription = "Calendário Icon",
                        tint = Vinho,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(White),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = "Menu Icon",
                        tint = Color.Gray,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    BasicTextField(
                        value = "Search \"Prova Cotuca\"",
                        onValueChange = {},
                        textStyle = TextStyle(color = Color.Gray),
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp, vertical = 12.dp)
                    )
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Gray,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFE91E63).copy(alpha = 0.8f))
                        .padding(8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(White, CircleShape)
                                .border(2.dp, Color(0xFFE91E63), CircleShape)
                                .padding(4.dp),
                            contentAlignment = Center
                        ) {
                            Text(
                                text = "L",
                                color = Color(0xFFE91E63),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(White, CircleShape)
                                .border(2.dp, Color(0xFF4CAF50), CircleShape)
                                .padding(4.dp),
                            contentAlignment = Center
                        ) {
                            Text(
                                text = "M",
                                color = Color(0xFF4CAF50),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                CalendarView()
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.bichinho),
                    contentDescription = "Unimind Logo Bottom",
                    modifier = Modifier
                        .size(48.dp)
                        .alpha(0.5f)
                )
            }
        }
    }
}

@Composable
fun CalendarView() {
    val calendar = remember { Calendar.getInstance() }
    val currentMonth = remember { mutableStateOf(calendar.get(Calendar.MONTH)) }
    val currentYear = remember { mutableStateOf(calendar.get(Calendar.YEAR)) }

    val daysInMonth = remember(currentMonth.value, currentYear.value) {
        Calendar.getInstance().apply {
            set(Calendar.YEAR, currentYear.value)
            set(Calendar.MONTH, currentMonth.value)
        }.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    val firstDayOfWeek = remember(currentMonth.value, currentYear.value) {
        Calendar.getInstance().apply {
            set(Calendar.YEAR, currentYear.value)
            set(Calendar.MONTH, currentMonth.value)
            set(Calendar.DAY_OF_MONTH, 1)
        }.get(Calendar.DAY_OF_WEEK) - 1 // Adjust to start from Sunday (0)
    }

    val monthName = remember(currentMonth.value) {
        DateFormatSymbols().months[currentMonth.value]
    }

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(White)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "Previous Month",
                tint = Vinho,
                modifier = Modifier.clickable {
                    if (currentMonth.value > 0) {
                        currentMonth.value--
                    } else {
                        currentMonth.value = 11
                        currentYear.value--
                    }
                }
            )
            Text(
                text = "$monthName ${currentYear.value}",
                fontWeight = FontWeight.Bold,
                color = Vinho,
                fontSize = 18.sp
            )
            Icon(
                imageVector = Icons.Rounded.ArrowForward,
                contentDescription = "Next Month",
                tint = Vinho,
                modifier = Modifier.clickable {
                    if (currentMonth.value < 11) {
                        currentMonth.value++
                    } else {
                        currentMonth.value = 0
                        currentYear.value++
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Text("Sun", color = Color.Gray, fontSize = 12.sp)
            Text("Mon", color = Color.Gray, fontSize = 12.sp)
            Text("Tue", color = Color.Gray, fontSize = 12.sp)
            Text("Wed", color = Color.Gray, fontSize = 12.sp)
            Text("Thu", color = Color.Gray, fontSize = 12.sp)
            Text("Fri", color = Color.Gray, fontSize = 12.sp)
            Text("Sat", color = Color.Gray, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(4.dp))
        for (week in 0..((daysInMonth + firstDayOfWeek - 1) / 7)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                for (dayOfWeek in 0..6) {
                    val day = week * 7 + dayOfWeek - firstDayOfWeek + 1
                    if (day in 1..daysInMonth) {
                        val isToday = day == Calendar.getInstance().get(Calendar.DAY_OF_MONTH) &&
                                currentMonth.value == Calendar.getInstance().get(Calendar.MONTH) &&
                                currentYear.value == Calendar.getInstance().get(Calendar.YEAR)
                        Text(
                            text = day.toString(),
                            color = if (isToday) Color(0xFFE91E63) else Black,
                            fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
                            modifier = Modifier
                                .size(32.dp)
                                .wrapContentWidth(Alignment.CenterHorizontally)
                                .wrapContentHeight(Alignment.CenterVertically)
                                .clip(CircleShape)
                                .background(if (isToday) Color(0xFFE91E63).copy(alpha = 0.2f) else Color.Transparent)
                                .padding(4.dp)
                        )
                    } else {
                        Text("", modifier = Modifier.size(32.dp))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Ends", color = Color.Gray, fontSize = 14.sp)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("8:00", color = Black, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .border(1.dp, if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray, RoundedCornerShape(4.dp))
                        .background(if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .clickable { /* Handle AM/PM selection */ }
                ) {
                    Text("AM", color = White, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.width(4.dp))
                Box(
                    modifier = Modifier
                        .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .clickable { /* Handle AM/PM selection */ }
                ) {
                    Text("PM", color = Black, fontSize = 14.sp)
                }
            }
            }
        }
    }



@Composable
fun MultiColorProgressBar(progress1: Float, progress2: Float) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(White)
    ) {
        Row(modifier = Modifier.fillMaxSize().border(width = 5.dp, color = White)) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress1)
                    .background(Color(0xFF4A90E2))
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress2)
                    .background(Color(0xFFD32F2F))
            )
        }
    }
}


@Composable
fun PlayerCard(questoesText : String){
    Card(
        border = BorderStroke(width = 2.dp, color = Color(0xFFBFC8CD)),
        modifier = Modifier
            .width(170.dp)
            .height(120.dp)
            .padding(vertical = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .background(color = Color(0xFFAFE5FF))
                .padding(vertical = 6.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(CircleShape),
                contentAlignment = Center
            ) {
                Icon(
                    modifier = Modifier.size(80.dp),
                    painter = painterResource(R.drawable.fotouser1),
                    contentDescription = null,
                    tint = Color.Unspecified,
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = questoesText, fontSize = 16.sp, color = Color(0xFF3E6B88), textAlign = TextAlign.Center)
        }
    }
}


@Composable
fun ProgressionCard(){
    Card(
        modifier = Modifier
            .fillMaxWidth(0.95f).shadow(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E0D1)),
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(horizontal = 15.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(85.dp)
            ) {
                Text("Desafio", fontSize = 38.sp, fontWeight = FontWeight.Bold, color = Black)

                Text("7 dias restantes", fontSize = 16.sp, color = Color.Gray)
            }

            Text("Resolver 100 questões", fontSize = 18.sp, color = Black)

            Spacer(modifier = Modifier.height(25.dp))

            MultiColorProgressBar(0.5f, 0.5f)

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                PlayerCard("34 questões")

                Spacer(modifier = Modifier.width(6.dp))

                PlayerCard("25 questões")
            }
        }
    }
}


@Composable
fun CorrectQuestionsCard(){
    Card(modifier = Modifier
        .fillMaxWidth(0.95f).shadow(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E0D1)),){
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.padding(16.dp)
        ){
            Text(text = "Questões corretas", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = "Nome 1", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .fillMaxWidth(0.5f)
                        .background(Color(0xFF4A90E2))
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = "Nome 2", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .fillMaxWidth(0.8f)
                        .background(Color(0xFFD32F2F))
                )
            }
        }
    }
}


@Composable
fun BottomBar(){
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
        containerColor = Color(0xFFF3E0D1)
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ){
            Icon(
                painter = painterResource(R.drawable.bichinho),
                contentDescription = "Porco Espinho",
                modifier = Modifier.size(48.dp),
                tint = Color.Unspecified,
            )
        }
    }
}


@Composable
fun HeaderUserProfile(color : Color, name : String){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ){
        Box(
            modifier = Modifier
                .padding(vertical = 6.dp)
                .weight(1f)
                .clip(CircleShape),
            contentAlignment = Center
        ) {
            Icon(
                modifier = Modifier.size(80.dp),
                painter = painterResource(R.drawable.fotouser2),
                contentDescription = null,
                tint = Color.Unspecified,
            )
        }

        Text(text = name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = color,
            modifier = Modifier
                .padding(vertical = 6.dp))
    }
}


@Composable
fun ChallengeHeader(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
            .background(Color(0xFFF3E0D1)),
        contentAlignment = Center
    ) {
        Image(
            painter = painterResource(R.drawable.em_cima_versus__1___1_),
            contentDescription = "Imagem de fundo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(65.dp),
        ){

            HeaderUserProfile(color = Color(0xFF741C28), "Nome 1")

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color(0xFF741C28),
                        fontFamily = FontFamily.Serif)
                    ) {
                        append("V")
                    }
                    withStyle(style = SpanStyle(color = Color(0xFFFFF1E7),
                        fontFamily = FontFamily.Serif)
                    ) {
                        append("S")
                    }
                },
                fontSize = 50.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.offset(x = (-7).dp)
            )

            HeaderUserProfile(color = Color(0xFFFFF1E7), name = "Nome 2")
        }
    }

}


@Composable
fun ChallengeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFBB8C8C))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ChallengeHeader()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProgressionCard()

                Spacer(modifier = Modifier.height(21.dp))

                CorrectQuestionsCard()
            }

            BottomBar()
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun AppPreview() {
    Entrar(rememberNavController())
}
