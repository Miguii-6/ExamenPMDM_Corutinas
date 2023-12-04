package com.example.examencorutinas.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

data class Frase(var texto: String, var verdadero: Boolean)

var frases: MutableList<Frase> = mutableListOf()
var fraseActual: MutableState<Frase> = mutableStateOf(Frase("-", true))
var countdownValue: MutableState<Int> = mutableStateOf(20)
var gameStarted: MutableState<Boolean> = mutableStateOf(false)

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
                    startCountdown()
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
            textStyle = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
        )
    }
}

fun startCountdown() {
    CoroutineScope(Dispatchers.Default).launch {
        while (countdownValue.value > 0) {
            withContext(Dispatchers.Main) {
                countdownValue.value--
                fraseActual.value = frases.random()
            }
            kotlinx.coroutines.delay(1000)
        }
        gameStarted.value = false
    }
}

@Preview
@Composable
fun PreviewFraseGame() {
    FraseGame()
}
