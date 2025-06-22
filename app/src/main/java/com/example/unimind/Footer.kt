package com.example.unimind

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.unimind.ui.theme.Vinho

@Composable
fun Footer(navController: NavController) {
    BottomAppBar(
        containerColor = Vinho,
        modifier = Modifier.height(65.dp) // Altura fixa para a barra
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround, // Distribui os ícones uniformemente
            verticalAlignment = Alignment.CenterVertically
        ) {
            FooterIcon(navController, R.drawable.flashcards, "telaFlashcardsArea")
            FooterIcon(navController, R.drawable.listasprovas, "telaListasProvasArea")
            FooterIcon(navController, R.drawable.competicao, "telaCompeticaoCriar")
            FooterIcon(navController, R.drawable.estatisticas, "telaEstatisticas")
            FooterIcon(navController, R.drawable.bichinho, "telaInicial")
        }
    }
}

@Composable
private fun FooterIcon(navController: NavController, iconRes: Int, route: String) {
    IconButton(onClick = { navController.navigate(route) }) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = route, // Descrição para acessibilidade
            tint = Color.Unspecified, // Cor do ícone
            modifier = Modifier.size(30.dp)
        )
    }
}
