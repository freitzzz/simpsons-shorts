package data.clients

/**
 * A [NetworkingClient] to interact with [Cinderela CDN](https://github.com/freitzzz/cinderela)
 */
class CinderelaNetworkingClient: RawGitHubApiNetworkingClient(
    GitHubRepository(
        owner = "freitzzz",
        repo = "cinderela",
        ref = "master"
    )
)