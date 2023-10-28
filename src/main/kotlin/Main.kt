import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.github.kotlinartisans.lumberkodee.putLumberkodeeToWork
import core.ReactorComposer
import core.Vault
import data.clients.CinderelaNetworkingClient
import data.repositories.CinderelaSimpsonsShortsRepository
import data.repositories.SimpsonsShortsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import logging.SimpsonsLumberkodeeClient
import presentation.Progress
import presentation.VideoPlayer
import presentation.VideoPlayerState
import state.shorts.NextShort
import state.shorts.ShortsReactor

@Composable
@Preview
fun App(
    vault: Vault,
) {
    val shortsReactor = ShortsReactor(vault.read())

    MaterialTheme {
        ReactorComposer(
            shortsReactor.apply {
                add(NextShort())
            },
        ) {
            if (it.url.isNotEmpty()) {
                VideoPlayer(
                    modifier = Modifier.fillMaxSize(),
                    url = it.url,
                    state = VideoPlayerState(
                        progress = Progress(0f, 0),
                    ),
                    onFinish = {
                        shortsReactor.add(NextShort())
                    }
                )
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
