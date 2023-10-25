package data.clients

import kotlinx.coroutines.coroutineScope
import java.io.InputStream
import java.net.URL

/**
 * A brief implementation of an abstract HTTP Client
 */
abstract class NetworkingClient(
    private val baseUrl: URL,
) {
    suspend fun get(endpoint: String): Result<Response> {
        return coroutineScope {
            return@coroutineScope runCatching {
                val request = URL("$baseUrl/$endpoint").openConnection()
                request.connect()

                Response(
                    request.getInputStream()
                )
            }
        }
    }
}

/**
 * Models the response of a request
 */
data class Response(
    val body: InputStream
)