package data.repositories

import data.clients.CinderelaNetworkingClient
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

/**
 * Specifies the contract API to interact with Simpsons shorts data source
 */
interface SimpsonsShortsRepository {
    suspend fun all(): Result<List<String>>
}

/**
 * A [SimpsonsShortsRepository] that connects to Cinderela CDN data source
 */
class CinderelaSimpsonsShortsRepository(
    private val client: CinderelaNetworkingClient
) : SimpsonsShortsRepository {
    override suspend fun all(): Result<List<String>> {
        val response = client.get("small-databases/simpson-shorts.json")

        return response.map {
            Json.decodeFromStream<List<String>>(it.body)
        }
    }
}