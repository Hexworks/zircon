package org.hexworks.zircon.internal.util

// TODO: placeholder while kotlinx.collections.immutable is not MPP
/**
 * A generic immutable collection of elements. Methods in this interface support only read-only access to the collection.
 *
 * Modification operations are supported through the [PersistentCollection] interface.
 *
 * Implementors of this interface take responsibility to be immutable.
 * Once constructed they must contain the same elements in the same order.
 *
 * @param E the type of elements contained in the collection. The immutable collection is covariant on its element type.
 */
interface ImmutableCollection<out E>: Collection<E>

/**
 * A generic persistent collection of elements that supports adding and removing elements.
 *
 * Modification operations return new instances of the persistent collection with the modification applied.
 *
 * @param E the type of elements contained in the collection. The persistent collection is covariant on its element type.
 */
interface PersistentCollection<out E> : ImmutableCollection<E> {
    /**
     * Returns the result of adding the specified [element] to this collection.
     *
     * @returns a new persistent collection with the specified [element] added;
     * or this instance if this collection does not support duplicates and it already contains the element.
     */
    fun add(element: @UnsafeVariance E): PersistentCollection<E>

    /**
     * Returns the result of adding all elements of the specified [elements] collection to this collection.
     *
     * @return a new persistent collection with elements of the specified [elements] collection added;
     * or this instance if no modifications were made in the result of this operation.
     */
    fun addAll(elements: Collection<@UnsafeVariance E>): PersistentCollection<E>

    /**
     * Returns the result of removing a single appearance of the specified [element] from this collection.
     *
     * @return a new persistent collection with a single appearance of the specified [element] removed;
     * or this instance if there is no such element in this collection.
     */
    fun remove(element: @UnsafeVariance E): PersistentCollection<E>

    /**
     * Returns the result of removing all elements in this collection that are also
     * contained in the specified [elements] collection.
     *
     * @return a new persistent collection with elements in this collection that are also
     * contained in the specified [elements] collection removed;
     * or this instance if no modifications were made in the result of this operation.
     */
    fun removeAll(elements: Collection<@UnsafeVariance E>): PersistentCollection<E>

    /**
     * Returns the result of removing all elements in this collection that match the specified [predicate].
     *
     * @return a new persistent collection with elements matching the specified [predicate] removed;
     * or this instance if no elements match the predicate.
     */
    fun removeAll(predicate: (E) -> Boolean): PersistentCollection<E>

    /**
     * Returns an empty persistent collection.
     */
    fun clear(): PersistentCollection<E>
}
