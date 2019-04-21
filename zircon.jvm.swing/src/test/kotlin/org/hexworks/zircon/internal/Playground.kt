package org.hexworks.zircon.internal


object Playground {


    @JvmStatic
    fun main(args: Array<String>) {





    }

fun <T, R> Iterable<T>.flatMap(f: (T) -> Iterable<R>): Iterable<R> {
    return this.fold(listOf()) { acc, next ->
        acc + f(next)
    }
}

}










typealias Function<T, R> = (T) -> R

operator fun <T, U, R> Function<T, U>.plus(fn: Function<U, R>): Function<T, R> {
    return { t ->
        fn(this(t))
    }
}

fun <T, U, R> Function<T, U>.compose(fn: Function<U, R>): Function<T, R> {
    return { t ->
        fn(this(t))
    }
}


fun Any.print() = println(this)
