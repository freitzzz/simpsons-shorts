import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.github.kotlinartisans.lumberkodee.putLumberkodeeToWork
import core.ReactorComposer
import core.Vault
import data.clients.CinderelaNetworkingClient
import data.repositories.CinderelaSimpsonsShortsRepository
import data.repositories.SimpsonsShortsRepository
import logging.SimpsonsLumberkodeeClient
import presentation.blocks.SimpsonsTV
import presentation.blocks.VideoPlayer
import presentation.fixed.Dimensions
import presentation.fixed.half
import state.shorts.NextShort
import state.shorts.ShortsReactor

@Composable
@Preview
fun App(
    vault: Vault,
) {
    val shortsReactor = ShortsReactor(vault.read())

    MaterialTheme {
        Box {
            SimpsonsTV()
            ReactorComposer(
                shortsReactor.apply {
                    add(NextShort())
                },
            ) {
                if (it.url.isNotEmpty()) {
                    VideoPlayer(
                        modifier = Modifier
                            .size(Dimensions.videoPlayerSize)
                            .align(Alignment.CenterStart.half()),
                        url = it.url,
                        onFinish = {
                            shortsReactor.add(NextShort())
                        }
                    )
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
            size = Dimensions.windowSize,
        ),
        undecorated = true,
        transparent = false,
        onCloseRequest = ::exitApplication
    ) {
        WindowDraggableArea(
        ) {
            App(vault)
        }
    }
}

private fun createVault() = Vault.empty().apply {
    store<SimpsonsShortsRepository>(
        CinderelaSimpsonsShortsRepository(
            CinderelaNetworkingClient()
        )
    )
}
