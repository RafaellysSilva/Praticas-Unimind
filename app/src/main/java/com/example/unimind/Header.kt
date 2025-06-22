package com.example.unimind

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unimind.ui.theme.Bege
import com.example.unimind.ui.theme.Nude
import com.example.unimind.ui.theme.Rosinha
import com.example.unimind.ui.theme.Vinho

@Composable
fun Header(nomePagina: String) {
    // A Column externa com fillMaxSize e verticalScroll foi removida.
    // Este Composable agora desenha apenas o header, sem tentar controlar a tela inteira.
    Box(
        modifier = Modifier
            .fillMaxWidth() // Apenas o Box externo precisa preencher a largura
            .height(220.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(bottomStart = 1500.dp, bottomEnd = 1500.dp))
                .background(Bege)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 30.dp) // Ajustado para corresponder à altura
                .clip(RoundedCornerShape(bottomStart = 1500.dp, bottomEnd = 1500.dp))
                .background(Rosinha)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp) // Ajustado para corresponder à altura
                .clip(RoundedCornerShape(bottomStart = 1500.dp, bottomEnd = 1500.dp))
                .background(Vinho),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = nomePagina,
                color = Nude,
                fontSize = 24.sp, // Aumentei um pouco para melhor visibilidade
                modifier = Modifier.padding(bottom = 60.dp) // Centraliza o texto na parte visível
            )
        }
    }
}