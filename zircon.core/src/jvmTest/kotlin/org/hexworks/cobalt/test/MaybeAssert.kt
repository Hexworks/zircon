package org.hexworks.cobalt.test

import org.assertj.core.api.AbstractObjectAssert
import org.hexworks.cobalt.datatypes.Maybe

fun <T> assertThat(maybe: Maybe<T>) = MaybeAssert(maybe)

class MaybeAssert<T>(actual: Maybe<T>) :
    AbstractObjectAssert<MaybeAssert<T>, Maybe<T>>(actual, MaybeAssert::class.java) {
    fun isPresent() = also {
        isNotNull

        if (!actual.isPresent) {
            failWithMessage("Expected a value to be present but was empty")
        }
    }

    fun isEmpty() = also {
        isNotNull

        if (!actual.isEmpty()) {
            failWithMessage("Expected no value to be present but contained %s", actual.get())
        }
    }

    fun hasValue(expected: T) = also {
        isNotNull

        if (actual.isEmpty()) {
            failWithMessage("Expected a value to be present but was empty")
        }
        if (actual.get() != expected) {
            failWithMessage("Expected actual <%s> to be equal to <%s> but was not", actual.get(), expected)
        }
    }
}
