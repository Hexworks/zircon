package org.hexworks.zircon.internal.util

// TODO: placeholder while kotlinx.collections.immutable is not MPP
/**
 * A generic immutable unordered collection of elements that does not support duplicate elements.
 * Methods in this interface support only read-only access to the immutable set.
 *
 * Modification operations are supported through the [PersistentSet] interface.
 *
 * Implementors of this interface take responsibility to be immutable.
 * Once constructed they must contain the same elements in the same order.
 *
 * @param E the type of elements contained in the set. The set is covariant on its element type.
 */
interface ImmutableSet<out E>: Set<E>, ImmutableCollection<E>

/**
 * A generic persistent unordered collection of elements that does not support duplicate elements, and supports
 * adding and removing elements.
 *
 * Modification operations return new instances of the persistent set with the modification applied.
 *
 * @param E the type of elements contained in the set. The persistent set is covariant on its element type.
 */
interface PersistentSet<out E> : ImmutableSet<E>, PersistentCollection<E> {
    /**
     * Returns the result of adding the specified [element] to this set.
     *
     * @return a new persistent set with the specified [element] added;
     * or this instance if it already contains the element.
     */
    override fun add(element: @UnsafeVariance E): PersistentSet<E>

    /**
     * Returns the result of adding all elements of the specified [elements] collection to this set.
     *
     * @return a new persistent set with elements of the specified [elements] collection added;
     * or this instance if it already contains every element of the specified collection.
     */
    override fun addAll(elements: Collection<@UnsafeVariance E>): PersistentSet<E>

    /**
     * Returns the result of removing the specified [element] from this set.
     *
     * @return a new persistent set with the specified [element] removed;
     * or this instance if there is no such element in this set.
     */
    override fun remove(element: @UnsafeVariance E): PersistentSet<E>

    /**
     * Returns the result of removing all elements in this set that are also
     * contained in the specified [elements] collection.
     *
     * @return a new persistent set with elements in this set that are also
     * contained in the specified [elements] collection removed;
     * or this instance if no modifications were made in the result of this operation.
     */
    override fun removeAll(elements: Collection<@UnsafeVariance E>): PersistentSet<E>

    /**
     * Returns the result of removing all elements in this set that match the specified [predicate].
     *
     * @return a new persistent set with elements matching the specified [predicate] removed;
     * or this instance if no elements match the predicate.
     */
    override fun removeAll(predicate: (E) -> Boolean): PersistentSet<E>

    /**
     * Returns an empty persistent set.
     */
    override fun clear(): PersistentSet<E>
}
