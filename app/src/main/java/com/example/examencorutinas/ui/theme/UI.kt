package com.example.examencorutinas.ui.theme

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState


data class Frase(var texto: String, var verdadero: Boolean)

fun cargarFrases(): MutableList<Frase> {
    return mutableListOf(
        Frase("el torneo de rugby cinco naciones, ahora es seis naciones", true),
        Frase("en el cielo hay cinco estrellas", false),
        Frase("el dia cinco de diciembre del 2023 es martes", true),
        Frase("cinco más cinco son diez", true),
        Frase("dos mas dos son cinco", false),
        Frase("los elefantes tienen cinco patas", false),
        Frase("las estaciones climáticas son cinco", false),
        Frase("tenemos cinco dedos los humanos", true),
        Frase("cinco días tiene la semana sin el Domingo y el Sábado", true),
        Frase("una gallina pesa menos que cinco toneladas", true)
    )
}

@Composable
fun FraseGame() {
    var fraseActualIndex by remember { mutableStateOf(0) }
    val countdownValue: MutableState<Int> = remember { mutableStateOf(20) }
    val gameStarted: MutableState<Boolean> = remember { mutableStateOf(false) }
    val score: MutableState<Int> = remember { mutableStateOf(0) }
    val frases = cargarFrases()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                gameStarted.value = true
                countdownValue.value = 20
                score.value = 0
                fraseActualIndex = 0
                startCountdown(countdownValue, gameStarted)
            },
            modifier = Modifier
                .padding(vertical = 24.dp)
                .height(72.dp)
                .width(200.dp)
        ) {
            Text(text = "START", fontSize = 24.sp)
        }

        Text(
            text = "Countdown: ${countdownValue.value}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        if (gameStarted.value) {
            BasicTextField(
                value = frases[fraseActualIndex].texto,
                onValueChange = { /* No op, read-only field */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(16.dp),
                textStyle = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
            )

            Row(
                modifier = Modifier.padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        if (frases[fraseActualIndex].verdadero) {
                            score.value++
                        }
                        fraseActualIndex = (fraseActualIndex + 1) % frases.size
                    },
                    enabled = gameStarted.value,
                    modifier = Modifier
                        .height(72.dp)
                        .width(100.dp)
                ) {
                    Text(text = "V", fontSize = 24.sp)
                }

                Button(
                    onClick = {
                        if (!frases[fraseActualIndex].verdadero) {
                            score.value++
                        }
                        fraseActualIndex = (fraseActualIndex + 1) % frases.size
                    },
                    enabled = gameStarted.value,
                    modifier = Modifier
                        .height(72.dp)
                        .width(100.dp)
                ) {
                    Text(text = "F", fontSize = 24.sp)
                }
            }

            Text(
                text = "Score: ${score.value}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

fun startCountdown(
    countdownValue: MutableState<Int>,
    gameStarted: MutableState<Boolean>
) {
    CoroutineScope(Dispatchers.Default).launch {
        while (countdownValue.value > 0 && gameStarted.value) {
            countdownValue.value--
            delay(1000)
        }
        gameStarted.value = false
    }
}

@Preview
@Composable
fun PreviewFraseGame() {
    FraseGame()
}


