package logging

import com.github.kotlinartisans.lumberkodee.LogLevel
import com.github.kotlinartisans.lumberkodee.LumberkodeeClient

/**
 * A Simpsons catchphrases inspired lumberkodee client
 */
class SimpsonsLumberkodeeClient : LumberkodeeClient {
    override fun supports(level: LogLevel) = true

    override fun info(message: String, extras: Map<String, String>) =
        println("Hey! Hey! (ℹ\uFE0F): $message (extras: $extras)")

    override fun warning(message: String, extras: Map<String, String>) =
        println("Ay, Caramba! (⚠\uFE0F): $message (extras: $extras)")

    override fun error(message: String, throwable: Throwable, extras: Map<String, String>) =
        println("D'oh! (\uD83D\uDC80): $message (throwable: $throwable | extras: $extras)")

    override fun debug(message: String, extras: Map<String, String>) =
        println("Hmm-mmmm (\uD83E\uDD16): $message (extras: $extras)")

    override fun verbose(message: String, extras: Map<String, String>) =
        println("Ha! Ha! (\uD83D\uDC1B): $message (extras: $extras)")
}