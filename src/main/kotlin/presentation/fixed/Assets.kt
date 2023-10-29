package presentation.fixed

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource

/**
 * Global container for referencing static asset elements
 */
object Assets {

    /**
     * Provides a [androidx.compose.ui.graphics.painter.Painter] for rendering The Simpsons TV.
     */
    @Composable
    fun simpsonsTV() = painterResource("assets/tv-cover.png")

    /**
     * Provides a [androidx.compose.ui.graphics.painter.Painter] for rendering the app icon.
     */
    @Composable
    fun appIcon() = painterResource("assets/icons/simpsons-shorts-icon.png")
}