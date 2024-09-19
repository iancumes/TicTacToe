package edu.uvg.ian.tictactoe



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

class GanadorScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val ganador = intent.getStringExtra("ganador") ?: "Empate"
            PantallaGanador(ganador)
        }
    }
}

@Composable
fun PantallaGanador(ganador: String) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = if (ganador == "Empate") "¡Es un empate!" else "¡El ganador es: $ganador!", style = MaterialTheme.typography.displayLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            // Volver a la pantalla principal
            val intent = Intent(context, InicioScreen::class.java)
            context.startActivity(intent)
        }) {
            Text("Jugar de nuevo")
        }
    }
}

@Preview
@Composable
fun PreviewPantallaGanador() {
    PantallaGanador("Jugador 1")
}
