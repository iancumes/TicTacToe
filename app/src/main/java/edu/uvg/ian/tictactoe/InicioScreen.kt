package edu.uvg.ian.tictactoe

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.uvg.ian.tictactoe.GameScreen
import edu.uvg.ian.tictactoe.R // Asegúrate de tener tu imagen en res/drawable

class InicioScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PantallaInicio()
        }
    }
}

@Composable
fun PantallaInicio() {
    var jugador1 by remember { mutableStateOf("") }
    var jugador2 by remember { mutableStateOf("") }
    var tamañoTablero by remember { mutableStateOf(3) }

    // Obtener el contexto de la aplicación
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagen en la parte superior
        Image(
            painter = painterResource(id = R.drawable.totito), // Cambia esto a tu imagen
            contentDescription = "Logo Tic Tac Toe",
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = 16.dp), // Añade espacio debajo de la imagen
            contentScale = ContentScale.Fit
        )

        // Selección del tamaño del tablero al principio
        Text("Selecciona el tamaño del tablero")
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            listOf(3, 4, 5).forEach { size ->
                Button(
                    onClick = { tamañoTablero = size },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("$size x $size")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de entrada para el Jugador 1 (color rojo)
        BasicTextField(
            value = jugador1,
            onValueChange = { jugador1 = it },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(8.dp)
                ) {
                    if (jugador1.isEmpty()) {
                        Text("Jugador 1", color = Color.Red) // Color rojo para Jugador 1
                    }
                    innerTextField()
                }
            }
        )

        // Campo de entrada para el Jugador 2 (color azul)
        BasicTextField(
            value = jugador2,
            onValueChange = { jugador2 = it },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(8.dp)
                ) {
                    if (jugador2.isEmpty()) {
                        Text("Jugador 2", color = Color.Blue) // Color azul para Jugador 2
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para iniciar el juego
        Button(onClick = {
            val intent = Intent(context, GameScreen::class.java).apply {
                putExtra("jugador1", jugador1)
                putExtra("jugador2", jugador2)
                putExtra("tamañoTablero", tamañoTablero)
            }
            context.startActivity(intent)
        }) {
            Text("Iniciar Juego")
        }
    }
}

@Preview
@Composable
fun PreviewPantallaInicio() {
    PantallaInicio()
}