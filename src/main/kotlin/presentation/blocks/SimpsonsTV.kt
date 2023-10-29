package presentation.blocks

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import presentation.fixed.Assets

/**
 * Builds an image that renders The Simpsons TV.
 */
@Composable
fun SimpsonsTV(
    modifier: Modifier = Modifier,
) = Image(
    Assets.simpsonsTV(),
    "the simpsons tv cover",
    alignment = Alignment.Center,
    contentScale = ContentScale.FillBounds,
    modifier = modifier.fillMaxSize(),
)