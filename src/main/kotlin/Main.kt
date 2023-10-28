import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import core.ReactorComposer
import core.Vault
import data.clients.CinderelaNetworkingClient
import data.repositories.CinderelaSimpsonsShortsRepository
import data.repositories.SimpsonsShortsRepository
import logging.SimpsonsLumberkodeeClient
import state.shorts.QueryAllShortsEvent
import state.shorts.ShortsReactor

@Composable
@Preview
fun App(
    vault: Vault,
) {
    var text by remember { mutableStateOf("Hello, World!") }
    val shortsReactor = ShortsReactor(vault.read())

    MaterialTheme {
        Column {
            Button(
                onClick = {
                    text = "Hello, Desktop!"
                    logDebug("button was clicked")

                    shortsReactor.add(QueryAllShortsEvent())
                }) {
                Text(text)
            }

            ReactorComposer(
                shortsReactor,
            ) {
                LazyColumn {
                    items(it) {
                        println("!!!")
                        Text(it)
                    }
                }
            }
        }
    }
}

fun main() = application {
    putLumberkodeeToWork(
        arrayListOf(
            SimpsonsLumberkodeeClient()
        )
    )

    val vault = createVault()

    Window(
        resizable = false,
        state = WindowState(
            size = DpSize(600.dp, 400.dp),
        ),
        onCloseRequest = ::exitApplication
    ) {
        App(vault)
    }
}

private fun createVault() = Vault.empty().apply {
    store<SimpsonsShortsRepository>(
        CinderelaSimpsonsShortsRepository(
            CinderelaNetworkingClient()
        )
    )
}
