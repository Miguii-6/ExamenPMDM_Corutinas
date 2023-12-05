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

    // Propiedades para controlar el juego y la puntuación
    var countdownValue: MutableState<Int> = mutableStateOf(20) // Valor inicial del contador
    var gameStarted: MutableState<Boolean> = mutableStateOf(false) // Estado del juego (iniciado o no)
    var fraseActualIndex = 0 // Estado del juego (iniciado o no)
    var score: MutableState<Int> = mutableStateOf(0) // Puntuación del jugador

    //Método para Iniciar el juego
    fun iniciarJuego() {
        // Reinicia las variables para un nuevo juego
        gameStarted.value = true
        countdownValue.value = 20
        score.value = 0
        fraseActualIndex = 0
        // Inicia la cuenta regresiva
        startCountdown()
    }

    // Función para responder Verdadero/Falso
    fun responderVF(esVerdadero: Boolean) {
        if (gameStarted.value) { // Verifica si el juego está en curso
            // Comprueba si la respuesta es correcta y actualiza la puntuación
            if ((esVerdadero && Datos.frases[fraseActualIndex].verdadero) ||
                (!esVerdadero && !Datos.frases[fraseActualIndex].verdadero)
            ) {
                score.value++
            }
            fraseActualIndex = (fraseActualIndex + 1) % Datos.frases.size // Avanza a la siguiente pregunta
        }
    }

    // Función para iniciar la cuenta regresiva
    private fun startCountdown() {
        CoroutineScope(Dispatchers.Default).launch {
            while (countdownValue.value > 0 && gameStarted.value) { // Realiza la cuenta regresiva mientras el juego esté activo
                countdownValue.value-- // Reduce el contador
                delay(1000) // Espera 1 segundo
            }
            gameStarted.value = false // Cuando el tiempo se agota, detiene el juego
        }
    }
}

