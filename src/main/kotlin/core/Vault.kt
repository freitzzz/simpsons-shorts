package core

import java.lang.reflect.Type

/**
 * Alias for a general use [TypedVault]
 */
typealias Vault = TypedVault<Any>

/**
 * A lightweight container for dependency injection with lazy loading support. In a nutshell, you
 * can see this as a hash map of dependencies, which are identified by their type. The type is
 * important because that's what will allow to retrieve a dependency later on, using type inference.
 */
class TypedVault<T> private constructor(
    val container: HashMap<Type, Lazy<T>>,
) {
    /**
     * Stores a dependency in eager mode
     */
    inline fun <reified S : T> store(value: S) = container.put(S::class.java, lazyOf(value))

    /**
     * Stores a dependency in lazy mode
     */
    inline fun <reified S : T> store(noinline lazyExpr: () -> S) =
        container.put(S::class.java, lazy(lazyExpr))

    /**
     * Attempts to retrieve a dependency, but fallbacks to null if it hasn't been registered yet
     */
    inline fun <reified S : T> lookup(): S? = container[S::class.java]?.value as S?

    /**
     * Retrieves a dependency, regardless if it has been registered yet.
     */
    inline fun <reified S : T> read(): S = lookup<S>()!!

    companion object {
        fun empty() = Vault(HashMap())
    }
}