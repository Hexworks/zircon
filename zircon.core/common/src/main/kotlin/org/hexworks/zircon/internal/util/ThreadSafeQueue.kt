package org.hexworks.zircon.internal.util

import org.hexworks.cobalt.datatypes.Maybe

interface ThreadSafeQueue<E> : MutableCollection<E> {

    /**
     * Inserts the specified element at the tail of this queue if it is
     * possible then returns `true` upon success and `false` otherwise
     */
    fun offer(e: E): Boolean

    /**
     * Removes all of the current elements of this queue and adds
     * them to [collection].
     */
    fun drainTo(collection: MutableCollection<E>): Int

    /**
     * Removes and returns all of the current elements of this queue.
     */
    fun drainAll(): Collection<E>

    /**
     * Retrieves the **head** of this queue,
     * or returns an empty `Maybe` if the queue is empty.
     */
    fun peek(): Maybe<E>

    /**
     * Retrieves the **tail** of this queue,
     * or returns an empty `Maybe` if the queue is empty.
     */
    fun peekLast(): Maybe<E>

    /**
     * Retrieves and removes the **head** of this queue,
     * or returns an empty `Maybe` if the queue is empty.
     */
    fun poll(): Maybe<E>

    /**
     * Retrieves and removes the **tail** of this queue,
     * or returns an empty `Maybe` if the queue is empty.
     */
    fun pollLast(): Maybe<E>
}
