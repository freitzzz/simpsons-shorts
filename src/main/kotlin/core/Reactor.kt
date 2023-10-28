package core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * A functional interface for emitting values.
 */
@FunctionalInterface
interface Emitter<V> {
    suspend fun emit(value: V)
}

/**
 * A [Emitter] that emits values to a [MutableStateFlow].
 */
class FlowStateEmitter<V>(
    private val stateFlow: MutableStateFlow<V>
) : Emitter<V> {
    override suspend fun emit(value: V) = stateFlow.emit(value)
}

/**
 * An event stream like reaction engine that propagates events into 0 or more states (1 Event > * State).
 * The API is pretty straight forward:
 * 1. clients register event-state mappers using the [on] method inside the `init` block
 * 2. clients publish an event using [add] method
 * 3. engine transforms these events using the available mappers for the specific event
 * 4. subscribes receive the created states using the [subscribe] method
 *
 * ```kotlin
 * class AuthReactor: Reactor<AuthEvent, AuthState>(NotAuthenticated()) {
 *
 *  init {
 *      on<AuthenticationStarted> {
 *          val credentials = Credentials(it.username, it.password)
 *          val authenticated = AuthenticationService.login(credentials)
 *
 *          if(authenticated) {
 *              emit(AuthenticationSuccessful())
 *          } else {
 *              emit(AuthenticationFailure())
 *          }
 *      }
 *  }
 * }
 *
 * val reactor = AuthReactor()
 * reactor.add(AuthenticationStarted())
 *
 * val state by reactor.subscribe()
 * ```
 *
 * Very inspired from [Flutter Bloc library](https://bloclibrary.dev) and it's implementation in
 * [Kotlin](https://github.com/ptrbrynt/KotlinBloc).
 */
abstract class Reactor<E, S>(
    initialState: S,
    val scope: CoroutineScope = CoroutineScope(Dispatchers.IO),
) {
    private val states = MutableStateFlow(initialState)
    val events = MutableSharedFlow<E>()
    val emitter = FlowStateEmitter(states)

    /**
     * Publishes an event to be processed by the reactor.
     */
    fun add(event: E) {
        scope.launch {
            events.emit(event)
        }
    }

    /**
     * Allows Composer subscribers to react to emitted states.
     */
    @Composable
    fun subscribe() = states.collectAsState()

    /**
     * Allows clients to implement their own event reaction logic and emit states
     * in accordance to the event context.
     */
    inline fun <reified E> on(
        noinline mapEventToState: suspend Emitter<S>.(E) -> Unit,
    ) {
        events
            .filterIsInstance<E>()
            .onEach { emitter.mapEventToState(it) }
            .launchIn(scope)
    }
}

/**
 * A composer block that builds everytime a new state is emitted in a reactor.
 *
 * This is the same as `BlocBuilder` in Flutter.
 */
@Composable
fun <E, S> ReactorComposer(
    reactor: Reactor<E, S>,
    content: @Composable (S) -> Unit,
) {
    val state by reactor.subscribe()

    content(state)
}