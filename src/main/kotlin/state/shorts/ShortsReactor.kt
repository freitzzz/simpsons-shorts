package state.shorts

import com.github.kotlinartisans.lumberkodee.logError
import com.github.kotlinartisans.lumberkodee.logInfo
import core.Reactor
import data.repositories.SimpsonsShortsRepository

class ShortsReactor(
    private val shortsRepository: SimpsonsShortsRepository,
) : Reactor<ShortsEvent, ShortsState>(
    ShortsState("")
) {
    private var shortsCache = arrayListOf<String>()

    init {
        on<NextShort> {
            if (shortsCache.isEmpty()) {
                shortsCache.addAll(allShorts())
                shortsCache.shuffle()
            }

            val nextShort = shortsCache.removeFirst()

            logInfo("next short: $nextShort")

            emit(
                ShortsState(nextShort)
            )
        }
    }

    private suspend fun allShorts(): List<String> {
        return shortsRepository.all().getOrElse {
            logError("failed to fetch shorts urls", it)

            return arrayListOf()
        }
    }
}

