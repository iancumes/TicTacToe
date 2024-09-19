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
            val tamanoTablero = intent.getIntExtra("tamañoTablero", 3)
            PantallaJuego(jugador1, jugador2, tamanoTablero)
        }
    }
}

@Composable
fun PantallaJuego(jugador1: String, jugador2: String, tamano: Int) {
    val context = LocalContext.current
    var tablero = remember { Array(tamano) { Array(tamano) { "" } } } // Matriz de tablero
    var turno by remember { mutableStateOf(if ((1..2).random() == 1) jugador1 else jugador2) }
    var ganador by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Turno de: $turno")
        Spacer(modifier = Modifier.height(16.dp))

        for (i in 0 until tamano) {
            Row {
                for (j in 0 until tamano) {
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

// Función para verificar si el jugador ha ganado
fun verificarGanador(tablero: Array<Array<String>>, simbolo: String): Boolean {
    val size = tablero.size
    // Verifica filas, columnas y diagonales
    for (i in 0 until size) {
        if (tablero[i].all { it == simbolo }) return true // Filas
        if ((0 until size).all { tablero[it][i] == simbolo }) return true // Columnas
    }
    // Diagonales
    if ((0 until size).all { tablero[it][it] == simbolo }) return true // Diagonal principal
    if ((0 until size).all { tablero[it][size - it - 1] == simbolo }) return true // Diagonal secundaria
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