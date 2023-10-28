package state.shorts

import com.github.kotlinartisans.lumberkodee.logError
import core.Reactor
import data.repositories.SimpsonsShortsRepository

class ShortsReactor(
    private val shortsRepository: SimpsonsShortsRepository,
) : Reactor<ShortsEvent, ShortsState>(
    arrayListOf()
) {
    init {
        on<QueryAllShortsEvent> {
            shortsRepository.all().onSuccess {
                emit(it)
            }.onFailure {
                logError("failed to fetch shorts urls", it)
            }
        }
    }
}

