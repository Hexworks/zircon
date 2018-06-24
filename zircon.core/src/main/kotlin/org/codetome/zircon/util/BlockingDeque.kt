package org.codetome.zircon.util

interface BlockingDeque<E> : Collection<E> {

    /**
     * Inserts the specified element at the tail of this queue if it is
     * possible then returns `true` upon success and `false` otherwise
     */
    fun offer(e: E): Boolean

    fun drainTo(c: MutableCollection<E>): Int

    fun remove(element: E): Boolean

    /**
     * Retrieves and removes the last element of this deque,
     * or returns an empty `Maybe` if the queue is empty.
     */
    fun pollLast(): Maybe<E>
}
