package com.example.examencorutinas.ui.theme


import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.examencorutinas.MiViewModel


@Composable
fun FraseGame(viewModel: MiViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Botón de inicio
        Button(
            onClick = {
                viewModel.iniciarJuego()
            },
            modifier = Modifier
                .padding(vertical = 24.dp)
                .height(72.dp)
                .width(200.dp)
        ) {
            Text(text = "START", fontSize = 24.sp)
        }

        // Contador
        Text(
            text = "Countdown: ${viewModel.countdownValue.value}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        if (viewModel.gameStarted.value) {
            // Campo de texto para mostrar la frase
            BasicTextField(
                value = Datos.frases[viewModel.fraseActualIndex].texto,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .padding(16.dp),
                textStyle = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
            )

            // Botones de Verdadero/Falso y puntuación
            Row(
                modifier = Modifier.padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        viewModel.responderVF(true)
                    },
                    enabled = viewModel.gameStarted.value,
                    modifier = Modifier
                        .height(72.dp)
                        .width(100.dp)
                ) {
                    Text(text = "V", fontSize = 24.sp)
                }

                Button(
                    onClick = {
                        viewModel.responderVF(false)
                    },
                    enabled = viewModel.gameStarted.value,
                    modifier = Modifier
                        .height(72.dp)
                        .width(100.dp)
                ) {
                    Text(text = "F", fontSize = 24.sp)
                }
            }

            // Mostrar puntuación
            Text(
                text = "Score: ${viewModel.score.value}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Preview
@Composable
fun PreviewFraseGame() {
    FraseGame()
}


