package presentation.fixed

import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

// "Believe me, I know what I'm doing" ratio between window and video player sizes.
private val windowToVideoPlayerRatio = Size(
    (1100.dp.times(100)).div(1400.dp) * 0.01f,
    (700.dp.times(100)).div(900.dp) * 0.01f,
)

/**
 * Global container for referencing static dimension values
 */
object Dimensions {
    val windowSize = DpSize(1400.dp, 900.dp)
    val videoPlayerSize = windowSize.times(windowToVideoPlayerRatio)
}

/**
 * Uses [DpSize] and [Size] `width` and `height` properties to compute their product.
 */
fun DpSize.times(size: Size) = DpSize(this.width.times(size.width), this.height.times(size.height))

/**
 * Computes the half value of Alignment. For now, Supports only [BiasAlignment] instances.
 */
fun Alignment.half() = when (this) {
    is BiasAlignment -> BiasAlignment(this.horizontalBias / 2f, this.verticalBias / 2f)
    else -> throw UnsupportedOperationException("only bias alignment operation is supported")
}