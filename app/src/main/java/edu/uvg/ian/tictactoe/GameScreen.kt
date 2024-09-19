package edu.uvg.ian.tictactoe



import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current
    var tablero = remember { Array(tamaño) { Array(tamaño) { "" } } } // Matriz de tablero
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
                            .size(80.dp)
                            .border(2.dp, Color.Black) // Añadimos borde negro para la visibilidad
                            .background(Color.Gray)
                            .padding(4.dp)
                            .clickable(enabled = tablero[i][j].isEmpty()) {
                                if (ganador == null) {
                                    tablero[i][j] = if (turno == jugador1) "X" else "O"
                                    if (verificarGanador(tablero, if (turno == jugador1) "X" else "O")) {
                                        ganador = turno
                                        // Mostrar pantalla de ganador
                                        val intent = Intent(context, GanadorScreen::class.java).apply {
                                            putExtra("ganador", ganador)
                                        }
                                        context.startActivity(intent)
                                    } else if (tableroLleno(tablero)) {
                                        // Si se llena el tablero y no hay ganador, es empate
                                        val intent = Intent(context, GanadorScreen::class.java).apply {
                                            putExtra("ganador", "Empate")
                                        }
                                        context.startActivity(intent)
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
    }
}

// Modificación de la función para verificar siempre 3 en línea
fun verificarGanador(tablero: Array<Array<String>>, simbolo: String): Boolean {
    val size = tablero.size
    // Verifica filas
    for (i in 0 until size) {
        for (j in 0 until size - 2) { // Verifica solo cuando puede haber 3 consecutivos
            if (tablero[i][j] == simbolo && tablero[i][j + 1] == simbolo && tablero[i][j + 2] == simbolo) {
                return true
            }
        }
    }
    // Verifica columnas
    for (i in 0 until size - 2) { // Verifica solo cuando puede haber 3 consecutivos
        for (j in 0 until size) {
            if (tablero[i][j] == simbolo && tablero[i + 1][j] == simbolo && tablero[i + 2][j] == simbolo) {
                return true
            }
        }
    }
    // Verifica diagonal principal
    for (i in 0 until size - 2) {
        for (j in 0 until size - 2) {
            if (tablero[i][j] == simbolo && tablero[i + 1][j + 1] == simbolo && tablero[i + 2][j + 2] == simbolo) {
                return true
            }
        }
    }
    // Verifica diagonal secundaria
    for (i in 0 until size - 2) {
        for (j in 2 until size) {
            if (tablero[i][j] == simbolo && tablero[i + 1][j - 1] == simbolo && tablero[i + 2][j - 2] == simbolo) {
                return true
            }
        }
    }
    return false
}

// Verificar si el tablero está completamente lleno
fun tableroLleno(tablero: Array<Array<String>>): Boolean {
    return tablero.all { fila -> fila.all { it.isNotEmpty() } }
}

@Preview
@Composable
fun PreviewPantallaJuego() {
    PantallaJuego("Jugador 1", "Jugador 2", 3)
}
