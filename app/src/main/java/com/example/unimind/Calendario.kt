package com.example.unimind

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.unimind.ui.theme.Nude
import com.example.unimind.ui.theme.UnimindTheme
import com.example.unimind.ui.theme.Vinho
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calendario(navController: NavController) {
    UnimindTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Nude)
        ) {
            // O Header fica no fundo do Box
            CalendarHeader()

            // Scaffold com fundo transparente para que o Header seja visível
            Scaffold(
                containerColor = Color.Transparent,
                bottomBar = { Footer(navController) }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(top = 180.dp), // Espaço para o header
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CalendarView()
                }
            }
        }
    }
}

@Composable
private fun CalendarHeader() {
    var searchText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp) // Altura do header
    ) {
        // Fundo Vinho com a curva
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(bottomStart = 80.dp, bottomEnd = 80.dp))
                .background(Vinho)
        )

        // Conteúdo do Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título e Ícones
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bichinho),
                    contentDescription = "Mascote",
                    modifier = Modifier.size(40.dp)
                )
                Text(
                    text = "Calendário",
                    color = Nude,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    painter = painterResource(id = R.drawable.calendario),
                    contentDescription = "Ícone Calendário",
                    tint = Nude,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Barra de Pesquisa
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Search \"Prova Cotuca\"") },
                leadingIcon = { Icon(Icons.Default.Menu, contentDescription = "Menu") },
                trailingIcon = { Icon(Icons.Default.Search, contentDescription = "Pesquisar") },
                shape = CircleShape,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Nude,
                    unfocusedContainerColor = Nude,
                    disabledContainerColor = Nude,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )
        }
    }
}

@Composable
private fun CalendarView() {
    val calendar = remember { Calendar.getInstance() }
    var currentMonth by remember { mutableStateOf(calendar.get(Calendar.MONTH)) }
    var currentYear by remember { mutableStateOf(calendar.get(Calendar.YEAR)) }
    var selectedTimePeriod by remember { mutableStateOf("AM") }

    val daysInMonth = remember(currentMonth, currentYear) {
        Calendar.getInstance().apply {
            set(Calendar.YEAR, currentYear)
            set(Calendar.MONTH, currentMonth)
        }.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    val firstDayOfWeek = remember(currentMonth, currentYear) {
        Calendar.getInstance().apply {
            set(Calendar.YEAR, currentYear)
            set(Calendar.MONTH, currentMonth)
            set(Calendar.DAY_OF_MONTH, 1)
        }.get(Calendar.DAY_OF_WEEK) - 1 // Domingo = 0
    }

    val monthName = remember(currentMonth) {
        SimpleDateFormat("MMMM", Locale.getDefault()).format(
            Calendar.getInstance().apply { set(Calendar.MONTH, currentMonth) }.time
        ).replaceFirstChar { it.uppercase() }
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Navegação do Mês
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    if (currentMonth > 0) currentMonth-- else {
                        currentMonth = 11; currentYear--
                    }
                }) {
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Mês Anterior",
                        tint = Vinho
                    )
                }
                Text(
                    text = "$monthName $currentYear",
                    fontWeight = FontWeight.Bold,
                    color = Vinho,
                    fontSize = 18.sp
                )
                IconButton(onClick = {
                    if (currentMonth < 11) currentMonth++ else {
                        currentMonth = 0; currentYear++
                    }
                }) {
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Próximo Mês",
                        tint = Vinho
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Dias da Semana
            Row(modifier = Modifier.fillMaxWidth()) {
                val weekDays = listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")
                weekDays.forEach { day ->
                    Text(
                        text = day,
                        color = Color.Gray,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Grid de Dias
            val totalCells = (daysInMonth + firstDayOfWeek)
            val numWeeks = (totalCells + 6) / 7
            for (week in 0 until numWeeks) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    for (dayOfWeek in 0..6) {
                        val day = week * 7 + dayOfWeek - firstDayOfWeek + 1
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.weight(1f).aspectRatio(1f)
                        ) {
                            if (day in 1..daysInMonth) {
                                val isToday = day == 26 // Para fins de preview igual a imagem

                                Text(
                                    text = day.toString(),
                                    color = if (isToday) Color.White else Color.Black,
                                    fontWeight = FontWeight.Normal,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(if (isToday) Vinho else Color.Transparent)
                                        .wrapContentHeight(Alignment.CenterVertically)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Seletor de Horário
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Ends", color = Color.Gray, fontSize = 14.sp)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(Color.Transparent)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text("8:00", color = Color.Black, fontSize = 14.sp)
                    }

                    // Botão AM
                    TimePeriodSelector(
                        text = "AM",
                        isSelected = selectedTimePeriod == "AM",
                        onClick = { selectedTimePeriod = "AM" }
                    )

                    // Botão PM
                    TimePeriodSelector(
                        text = "PM",
                        isSelected = selectedTimePeriod == "PM",
                        onClick = { selectedTimePeriod = "PM" }
                    )
                }
            }
        }
    }
}

@Composable
private fun TimePeriodSelector(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) Color.DarkGray else Color.LightGray
    val textColor = if (isSelected) Color.White else Color.Black.copy(alpha = 0.7f)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text = text, color = textColor, fontSize = 14.sp)
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CalendarioPreview() {
    Calendario(rememberNavController())
}