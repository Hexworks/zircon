package org.hexworks.zircon.api.behavior

/**
 * Represents an object that can be copied. [createCopy] will create a
 * *new* object with the same type ([T]) which is completely
 * decoupled from the original one. **Ensure** that the copy is a **deep copy**.
 */
interface Copiable<out T> {

    /**
     * Creates a **deep copy** of `this` object.
     */
    fun createCopy(): T
}