package edu.uvg.ian.tictactoe



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class GameScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val jugador1 = intent.getStringExtra("jugador1") ?: "Jugador 1"
            val jugador2 = intent.getStringExtra("jugador2") ?: "Jugador 2"
            val tamañoTablero = intent.getIntExtra("tamañoTablero", 3)
            PantallaJuego(jugador1, jugador2, tamañoTablero)
        }
    }
}

@Composable
fun PantallaJuego(jugador1: String, jugador2: String, tamaño: Int) {
    var tablero = remember { Array(tamaño) { Array(tamaño) { "" } } }
    var turno by remember { mutableStateOf(if ((1..2).random() == 1) jugador1 else jugador2) }
    var ganador by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Turno de: $turno")
        Spacer(modifier = Modifier.height(16.dp))

        for (i in 0 until tamaño) {
            Row {
                for (j in 0 until tamaño) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(Color.Gray)
                            .padding(4.dp)
                            .clickable(enabled = tablero[i][j].isEmpty()) {
                                if (ganador == null) {
                                    tablero[i][j] = if (turno == jugador1) "X" else "O"
                                    if (verificarGanador(tablero, turno)) {
                                        ganador = turno
                                    } else {
                                        turno = if (turno == jugador1) jugador2 else jugador1
                                    }
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        BasicText(text = tablero[i][j], style = MaterialTheme.typography.displayLarge)
                    }
                }
            }
        }

        ganador?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Ganador: $it")
        }
    }
}

fun verificarGanador(tablero: Array<Array<String>>, jugador: String): Boolean {
    val size = tablero.size
    // Verifica filas, columnas y diagonales
    for (i in 0 until size) {
        if (tablero[i].all { it == jugador }) return true
        if ((0 until size).all { tablero[it][i] == jugador }) return true
    }
    // Diagonales
    if ((0 until size).all { tablero[it][it] == jugador }) return true
    if ((0 until size).all { tablero[it][size - it - 1] == jugador }) return true
    return false
}

@Preview
@Composable
fun PreviewPantallaJuego() {
    PantallaJuego("Jugador 1", "Jugador 2", 3)
}
