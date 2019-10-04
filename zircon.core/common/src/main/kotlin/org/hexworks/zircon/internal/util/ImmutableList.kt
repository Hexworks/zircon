package org.hexworks.zircon.internal.util

// TODO: placeholder while kotlinx.collections.immutable is not MPP
/**
 * A generic immutable ordered collection of elements. Methods in this interface support only read-only access to the immutable list.
 *
 * Modification operations are supported through the [PersistentList] interface.
 *
 * Implementors of this interface take responsibility to be immutable.
 * Once constructed they must contain the same elements in the same order.
 *
 * @param E the type of elements contained in the list. The immutable list is covariant on its element type.
 */
interface ImmutableList<out E> : List<E>, ImmutableCollection<E>

/**
 * A generic persistent ordered collection of elements that supports adding and removing elements.
 *
 * Modification operations return new instances of the persistent list with the modification applied.
 *
 * @param E the type of elements contained in the list. The persistent list is covariant on its element type.
 */
interface PersistentList<out E> : ImmutableList<E>, PersistentCollection<E> {
    /**
     * Returns a new persistent list with the specified [element] appended.
     */
    override fun add(element: @UnsafeVariance E): PersistentList<E>

    /**
     * Returns the result of appending all elements of the specified [elements] collection to this list.
     *
     * The elements are appended in the order they appear in the specified collection.
     *
     * @return a new persistent list with elements of the specified [elements] collection appended;
     * or this instance if the specified collection is empty.
     */
    override fun addAll(elements: Collection<@UnsafeVariance E>): PersistentList<E> // = super<ImmutableCollection>.addAll(elements) as ImmutableList

    /**
     * Returns the result of removing the first appearance of the specified [element] from this list.
     *
     * @return a new persistent list with the first appearance of the specified [element] removed;
     * or this instance if there is no such element in this list.
     */
    override fun remove(element: @UnsafeVariance E): PersistentList<E>

    /**
     * Returns the result of removing all elements in this list that are also
     * contained in the specified [elements] collection.
     *
     * @return a new persistent list with elements in this list that are also
     * contained in the specified [elements] collection removed;
     * or this instance if no modifications were made in the result of this operation.
     */
    override fun removeAll(elements: Collection<@UnsafeVariance E>): PersistentList<E>

    /**
     * Returns the result of removing all elements in this list that match the specified [predicate].
     *
     * @return a new persistent list with elements matching the specified [predicate] removed;
     * or this instance if no elements match the predicate.
     */
    override fun removeAll(predicate: (E) -> Boolean): PersistentList<E>

    /**
     * Returns an empty persistent list.
     */
    override fun clear(): PersistentList<E>


    /**
     * Returns the result of inserting the specified [c] collection at the specified [index].
     *
     * @return a new persistent list with the specified [c] collection inserted at the specified [index];
     * or this instance if the specified collection is empty.
     *
     * @throws IndexOutOfBoundsException if [index] is out of bounds of this list.
     */
    fun addAll(index: Int, c: Collection<@UnsafeVariance E>): PersistentList<E> // = builder().apply { addAll(index, c.toList()) }.build()

    /**
     * Returns a new persistent list with the element at the specified [index] replaced with the specified [element].
     *
     * @throws IndexOutOfBoundsException if [index] is out of bounds of this list.
     */
    fun set(index: Int, element: @UnsafeVariance E): PersistentList<E>

    /**
     * Returns a new persistent list with the specified [element] inserted at the specified [index].
     *
     * @throws IndexOutOfBoundsException if [index] is out of bounds of this list.
     */
    fun add(index: Int, element: @UnsafeVariance E): PersistentList<E>

    /**
     * Returns a new persistent list with the element at the specified [index] removed.
     *
     * @throws IndexOutOfBoundsException if [index] is out of bounds of this list.
     */
    fun removeAt(index: Int): PersistentList<E>
}
