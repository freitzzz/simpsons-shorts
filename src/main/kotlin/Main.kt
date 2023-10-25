import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.github.kotlinartisans.lumberkodee.logDebug
import com.github.kotlinartisans.lumberkodee.putLumberkodeeToWork
import logging.SimpsonsLumberkodeeClient

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop!"
            logDebug("button was clicked")
        }) {
            Text(text)
        }
    }
}

fun main() = application {
    putLumberkodeeToWork(
        arrayListOf(
            SimpsonsLumberkodeeClient()
        )
    )

    Window(
        resizable = false,
        state = WindowState(
            size = DpSize(600.dp, 400.dp)
        ),
        onCloseRequest = ::exitApplication
    ) {
        App()
    }
}
