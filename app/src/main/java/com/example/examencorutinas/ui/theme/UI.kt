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

var frases: MutableList<Frase> = mutableListOf()
var fraseActual: MutableState<Frase> = mutableStateOf(Frase("-", true))
var countdownValue: MutableState<Int> = mutableStateOf(20)
var gameStarted: MutableState<Boolean> = mutableStateOf(false)
var score: MutableState<Int> = mutableStateOf(0)

@Composable
fun FraseGame() {
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
                    score.value = 0 // Reiniciar la puntuación al iniciar el juego
                    startCountdown()
                }
            }
        ) {
            Text(text = "START")
        }

        Text(text = "Countdown: ${countdownValue.value}")
        val customH5Style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp // Tamaño de fuente equivalente a h5
        )

        BasicTextField(
            value = fraseActual.value.texto,
            onValueChange = { /* No op, read-only field */ },
            modifier = Modifier.padding(8.dp),
            textStyle = customH5Style
        )

        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { checkAnswer(true) },
                enabled = gameStarted.value
            ) {
                Text(text = "V")
            }

            Button(
                onClick = { checkAnswer(false) },
                enabled = gameStarted.value
            ) {
                Text(text = "F")
            }
        }

        Text(text = "Score: ${score.value}")
    }
}

fun startCountdown() {
    CoroutineScope(Dispatchers.Default).launch {
        while (countdownValue.value > 0) {
            countdownValue.value--
            fraseActual.value = frases.random()
            delay(1000)
        }
        gameStarted.value = false
    }
}

fun checkAnswer(answer: Boolean) {
    if (answer == fraseActual.value.verdadero) {
        score.value++
    }
    fraseActual.value = frases.random() // Mostrar nueva frase aleatoria
}

@Preview
@Composable
fun PreviewFraseGame() {
    FraseGame()
}



