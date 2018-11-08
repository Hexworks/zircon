package org.hexworks.zircon.api.util


@Suppress("UNCHECKED_CAST")
class Maybe<T> {

    private val value: T?

    /**
     * Tells whether this [Maybe] has a value.
     */
    val isPresent: Boolean
        get() = value != null

    /**
     * Tells whether this [Maybe] is empty (has no value).
     */
    fun isEmpty() = isPresent.not()

    private constructor() {
        this.value = null
    }

    private constructor(value: T) {
        this.value = value
    }

    /**
     * Returns the value stored in this [Maybe].
     * @throws [NoSuchElementException] if there is no value present.
     */
    fun get(): T {
        if (value == null) {
            throw NoSuchElementException("No value present")
        }
        return value
    }

    /**
     * Calls `consumer` only if this [Maybe] has a value.
     */
    fun ifPresent(consumer: Consumer<T>) {
        if (value != null)
            consumer.accept(value)
    }

    /**
     * Returns `this` [Maybe] if `predicate` returns `true` when called
     * with the value stored in this [Maybe] otherwise it returns an
     * empty [Maybe]. If this [Maybe] doesn't have a value `this` is
     * returned.
     */
    // TODO: make this easier to use
    fun filter(predicate: Predicate<in T>): Maybe<T> {
        return if (!isPresent)
            this
        else
            if (predicate.test(get())) this else empty()
    }

    /**
     * Transforms the value in this [Maybe] using the given
     * `mapper` function and returns a new [Maybe] with the result.
     * Returns an empty [Maybe] if there was no value to transform.
     */
    fun <U> map(mapper: Function<T, U>): Maybe<U> {
        return if (isEmpty())
            empty()
        else {
            ofNullable(mapper.apply(get()))
        }
    }

    /**
     * Transforms the value in this [Maybe] using the given
     * `mapper` function and returns it.
     * Returns an empty [Maybe] if there was no value to transform.
     * This method differs from [Maybe.map] in that `mapper` must
     * return a [Maybe] instead of a flat value.
     */
    fun <U> flatMap(mapper: Function<T, Maybe<U>>): Maybe<U> {
        return if (isEmpty())
            empty()
        else {
            mapper.apply(get())
        }
    }

    /**
     * Returns the value supplied by `whenEmpty` if this [Maybe] is empty,
     * otherwise returns the result of applying `whenPresent` to the value.
     */
    fun <U> fold(whenEmpty: Supplier<U>, whenPresent: Function<T, U>): U {
        return if (isPresent) {
            whenPresent.apply(get())
        } else {
            whenEmpty.get()
        }
    }

    /**
     * Returns the value of this [Maybe] or if it is not present
     * returns the supplied `other` value.
     */
    fun orElse(other: T): T {
        return value ?: other
    }

    /**
     * Returns the value of this [Maybe] or if it is not present
     * returns the value returned by calling the `other` function.
     */
    fun orElseGet(other: Supplier<T>): T {
        return value ?: other.get()
    }

    /**
     * Returns the value of this [Maybe] or if it is not present
     * throws the exception returned by calling the `exceptionSupplier` function.
     */
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
