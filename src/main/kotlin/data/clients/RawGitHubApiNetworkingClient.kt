package data.clients

import java.net.URL

/**
 * A [NetworkingClient] for interacting with Raw GitHub API
 */
open class RawGitHubApiNetworkingClient(repository: GitHubRepository): NetworkingClient(
    baseUrl = URL("https://raw.githubusercontent.com/${repository.owner}/${repository.repo}/${repository.ref}")
)

/**
 * Models the required data to identify a GitHub repository
 */
data class GitHubRepository(
    val owner: String,
    val repo: String,
    val ref: String,
)