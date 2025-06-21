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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.unimind.ui.theme.Bege
import com.example.unimind.ui.theme.Nude
import com.example.unimind.ui.theme.UnimindTheme
import com.example.unimind.ui.theme.Vinho
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.unimind.ui.theme.Azul
import com.example.unimind.ui.theme.Rosinha
import com.example.usuarioapp.viewmodel.UsuarioViewModel
import java.text.DateFormatSymbols
import java.util.Calendar
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.platform.LocalContext
import com.example.unimind.data.Usuario
import com.example.unimind.data.UsuarioConfig
import com.example.unimind.network.RetrofitUsuario

@Composable
fun Calendario(navController: NavController) {
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    for (dayOfWeek in 0..6) {
                        val day = week * 7 + dayOfWeek - firstDayOfWeek + 1
                        if (day in 1..daysInMonth) {
                            val isToday = day == Calendar.getInstance()
                                .get(Calendar.DAY_OF_MONTH) &&
                                    currentMonth.value == Calendar.getInstance()
                                .get(Calendar.MONTH) &&
                                    currentYear.value == Calendar.getInstance()
                                .get(Calendar.YEAR)
                            Text(
                                text = day.toString(),
                                color = if (isToday) Color(0xFFE91E63) else Black,
                                fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
                                modifier = Modifier
                                    .size(32.dp)
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                                    .wrapContentHeight(Alignment.CenterVertically)
                                    .clip(CircleShape)
                                    .background(
                                        if (isToday) Color(0xFFE91E63).copy(
                                            alpha = 0.2f
                                        ) else Color.Transparent
                                    )
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
                            .border(
                                1.dp,
                                if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
                                RoundedCornerShape(4.dp)
                            )
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

    UnimindTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Nude
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Vinho)
                        .padding(horizontal = 16.dp, vertical = 16.dp)
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
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "Calendário",
                            color = White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        //Spacer(modifier = Modifier.weight(1f))
                        Image(
                            painter = painterResource(id = R.drawable.calendario),
                            contentDescription = "Calendário Icon",
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
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(50.dp))
                    CalendarView()
                }
            }
        }
        Footer(rememberNavController())
    }
}