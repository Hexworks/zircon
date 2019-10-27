package org.hexworks.zircon.api.behavior

/**
 * Represents an object which can be copied. Note that [createCopy]
 * is creating a *new* object with the same type ([T]) which is completely
 * decoupled from the original. *Note* that the copy is a **deep copy**.
 */
interface Copiable<out T> {

    /**
     * Creates a **deep copy** of `this` object.
     */
    fun createCopy(): T
}