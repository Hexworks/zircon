package org.codetome.zircon.internal.multiplatform.api


@Suppress("UNCHECKED_CAST")
class Maybe<T> {

    private val value: T?

    val isPresent: Boolean
        get() = value != null

    private constructor() {
        this.value = null
    }

    private constructor(value: T) {
        this.value = value
    }

    fun get(): T {
        if (value == null) {
            throw NoSuchElementException("No value present")
        }
        return value
    }

    fun ifPresent(consumer: Consumer<T>) {
        if (value != null)
            consumer.accept(value)
    }

    fun ifPresent(consumer: (T) -> Unit) {
        if (value != null) {
            consumer.invoke(value)
        }
    }

    fun filter(predicate: Predicate<in T?>): Maybe<T> {
        return if (!isPresent)
            this
        else
            if (predicate.test(value)) this else empty()
    }

    fun <U> map(mapper: (T) -> U): Maybe<U> {
        return if (!isPresent)
            empty()
        else {
            ofNullable(mapper.invoke(value!!))
        }
    }

    fun <U> flatMap(mapper: (T?) -> Maybe<U>): Maybe<U> {
        return if (!isPresent)
            empty()
        else {
            mapper.invoke(value)
        }
    }

    fun orElse(other: T): T {
        return value ?: other
    }

    fun orElseGet(other: () -> T): T {
        return value ?: other()
    }

    fun <X : Throwable> orElseThrow(exceptionSupplier: Supplier<X>): T {
        return value ?: throw exceptionSupplier.get()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        if (other !is Maybe<*>) {
            return false
        }

        val otherMaybe = other as Maybe<*>?
        return value == otherMaybe!!.value
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }

    override fun toString(): String {
        return if (value != null)
            "Maybe[$value]"
        else
            "Maybe.empty"
    }

    companion object {
        private val EMPTY = Maybe<Any>()

        fun <T> empty(): Maybe<T> {
            return EMPTY as Maybe<T>
        }

        fun <T> of(value: T): Maybe<T> {
            return Maybe(value)
        }

        fun <T> ofNullable(value: T?): Maybe<T> {
            return if (value == null) empty() else of(value)
        }
    }
}
