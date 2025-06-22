package com.example.unimind

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unimind.R

// 1. A função foi movida para fora do CompeticaoHeader.
// Isto é uma boa prática para performance e reutilização de código.
@Composable
private fun HeaderUserProfile(color: Color, name: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.width(80.dp) // Dando uma largura para evitar que o nome quebre a linha
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = 6.dp)
                .size(60.dp) // Usar um tamanho fixo é mais seguro aqui
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(), // Preenche o Box
                painter = painterResource(R.drawable.fotouser2),
                contentDescription = "Foto do usuário $name",
                tint = Color.Unspecified,
            )
        }

        Text(
            text = name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = color,
            maxLines = 1 // Garante que o nome não quebre em duas linhas
        )
    }
}


@Composable
fun CompeticaoHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp) // Aumentei um pouco a altura para acomodar melhor
            .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
            .background(Color(0xFFF3E0D1)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.em_cima_versus__1___1_),
            contentDescription = "Imagem de fundo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly // SpaceEvenly para um melhor espaçamento
        ) {

            HeaderUserProfile(color = Color(0xFF741C28), "Nome 1")

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color(0xFF741C28), fontFamily = FontFamily.Serif)) {
                        append("V")
                    }
                    withStyle(style = SpanStyle(color = Color(0xFFFFF1E7), fontFamily = FontFamily.Serif)) {
                        append("S")
                    }
                },
                fontSize = 50.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.SemiBold
            )

            HeaderUserProfile(color = Color(0xFFFFF1E7), name = "Nome 2")
        }
    }
}