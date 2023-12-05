package com.example.examencorutinas
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.examencorutinas.ui.theme.Datos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MiViewModel : ViewModel() {
    var countdownValue: MutableState<Int> = mutableStateOf(20)
    var gameStarted: MutableState<Boolean> = mutableStateOf(false)
    var fraseActualIndex = 0
    var score: MutableState<Int> = mutableStateOf(0)

    fun iniciarJuego() {
        gameStarted.value = true
        countdownValue.value = 20
        score.value = 0
        fraseActualIndex = 0
        startCountdown()
    }

    fun responderVF(esVerdadero: Boolean) {
        if (gameStarted.value) {
            if ((esVerdadero && Datos.frases[fraseActualIndex].verdadero) ||
                (!esVerdadero && !Datos.frases[fraseActualIndex].verdadero)
            ) {
                score.value++
            }
            fraseActualIndex = (fraseActualIndex + 1) % Datos.frases.size
        }
    }

    private fun startCountdown() {
        CoroutineScope(Dispatchers.Default).launch {
            while (countdownValue.value > 0 && gameStarted.value) {
                countdownValue.value--
                delay(1000)
            }
            gameStarted.value = false
        }
    }
}

