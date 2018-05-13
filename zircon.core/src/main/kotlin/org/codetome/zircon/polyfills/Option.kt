package org.codetome.zircon.polyfills


class Option<T> {

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

    fun ifPresent(consumer: Consumer<in T>) {
        if (value != null)
            consumer.accept(value)
    }

    fun filter(predicate: Predicate<in T?>): Option<T> {
        return if (!isPresent)
            this
        else
            if (predicate.test(value)) this else empty()
    }

    fun <U> map(mapper: Function<in T, out U>): Option<U> {
        return if (!isPresent)
            empty()
        else {
            Option.ofNullable(mapper.apply(value!!))
        }
    }

    fun <U> flatMap(mapper: Function<in T?, Option<U>>): Option<U> {
        return if (!isPresent)
            empty()
        else {
            mapper.apply(value)
        }
    }

    fun orElse(other: T): T {
        return value ?: other
    }

    fun orElseGet(other: () -> T): T {
        return value ?: other()
    }

    fun <X : Throwable> orElseThrow(exceptionSupplier: Supplier<out X>): T {
        return value ?: throw exceptionSupplier.get()
    }

    override fun equals(obj: Any?): Boolean {
        if (this === obj) {
            return true
        }

        if (obj !is Option<*>) {
            return false
        }

        val other = obj as Option<*>?
        return value == other!!.value
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }

    override fun toString(): String {
        return if (value != null)
            "Option[$value]"
        else
            "Option.empty"
    }

    companion object {
        private val EMPTY = Option<Any>()

        fun <T> empty(): Option<T> {
            return EMPTY as Option<T>
        }

        fun <T> of(value: T): Option<T> {
            return Option(value)
        }

        fun <T> ofNullable(value: T?): Option<T> {
            return if (value == null) empty() else of(value)
        }
    }
}
