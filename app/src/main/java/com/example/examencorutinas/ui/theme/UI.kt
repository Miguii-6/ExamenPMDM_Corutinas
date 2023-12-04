package com.example.examencorutinas.ui.theme

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
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState


data class Frase(var texto: String, var verdadero: Boolean)

fun cargarFrases(): MutableState<Frase> {
    val frases: MutableList<Frase> = mutableListOf()
    frases.add(Frase("el torneo de rugby cinco naciones, ahora es seis naciones", true))
    frases.add(Frase("en el cielo hay cinco estrellas", false))
    frases.add(Frase("el dia cinco de diciembre del 2023 es martes", true))
    frases.add(Frase("cinco más cinco son diez", true))
    frases.add(Frase("dos mas dos son cinco", false))
    frases.add(Frase("los elefantes tienen cinco patas", false))
    frases.add(Frase("las estaciones climáticas son cinco", false))
    frases.add(Frase("tenemos cinco dedos los humanos", true))
    frases.add(Frase("cinco días tiene la semana sin el Domingo y el Sábado", true))
    frases.add(Frase("una gallina pesa menos que cinco toneladas", true))

    val fraseActual: MutableState<Frase> = mutableStateOf(frases.random())
    return fraseActual
}

@Composable
fun FraseGame() {
    val fraseActual = cargarFrases()
    val countdownValue: MutableState<Int> = remember { mutableStateOf(20) }
    val gameStarted: MutableState<Boolean> = remember { mutableStateOf(false) }
    val score: MutableState<Int> = remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                if (!gameStarted.value) {
                    gameStarted.value = true
                    countdownValue.value = 20
                    score.value = 0
                    startCountdown(countdownValue, gameStarted, fraseActual)
                }
            }
        ) {
            Text(text = "START")
        }

        Text(text = "Countdown: ${countdownValue.value}")

        BasicTextField(
            value = fraseActual.value.texto,
            onValueChange = { /* No op, read-only field */ },
            modifier = Modifier.padding(8.dp),
            textStyle = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp // Tamaño de fuente equivalente a h5
            )
        )

        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { checkAnswer(true, score, fraseActual) },
                enabled = gameStarted.value
            ) {
                Text(text = "V")
            }

            Button(
                onClick = { checkAnswer(false, score, fraseActual) },
                enabled = gameStarted.value
            ) {
                Text(text = "F")
            }
        }

        Text(text = "Score: ${score.value}")
    }
}

fun startCountdown(
    countdownValue: MutableState<Int>,
    gameStarted: MutableState<Boolean>,
    fraseActual: MutableState<Frase>
) {
    CoroutineScope(Dispatchers.Default).launch {
        while (countdownValue.value > 0) {
            countdownValue.value--
            fraseActual.value = cargarFrases().value
            delay(1000)
        }
        gameStarted.value = false
    }
}

fun checkAnswer(
    answer: Boolean,
    score: MutableState<Int>,
    fraseActual: MutableState<Frase>
) {
    if (answer == fraseActual.value.verdadero) {
        score.value++
    }
    fraseActual.value = cargarFrases().value
}

@Preview
@Composable
fun PreviewFraseGame() {
    FraseGame()
}




