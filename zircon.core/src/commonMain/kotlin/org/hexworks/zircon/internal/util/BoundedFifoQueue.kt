package org.hexworks.zircon.internal.util

import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.internal.behavior.impl.DefaultObservable

/**
 * Queue that has a fixed maximum size. After this size is reached, the
 * oldest added element is removed whenever a new element is added
 */
public class BoundedFifoQueue<T : Any> private constructor(
    private val currentElements: MutableList<T>,
    private val maxElements: Int
) {

    private val observable = DefaultObservable<T>()

    public val elements: List<T>
        get() = currentElements.toList()
    public val size: Int
        get() = currentElements.size
    public val lastIndex: Int
        get() = currentElements.lastIndex


    public operator fun get(index: Int): T = currentElements[index]

    public fun add(element: T) {
        currentElements.add(element)
        if (currentElements.size > maxElements) {
            observable.notifyObservers(currentElements.removeAt(0))
        }
    }

    public fun subList(fromIndex: Int, toIndex: Int): List<T> {
        return currentElements.subList(fromIndex, toIndex)
    }

    public fun onKick(fn: (element: T) -> Unit): Subscription = observable.addObserver(fn)

    public companion object {
        public fun <T : Any> create(
            elements: List<T> = listOf(),
            maxElements: Int = elements.size
        ): BoundedFifoQueue<T> {
            require(maxElements > 0) {
                "Having a queue with max elements of 0 is meaningless"
            }
            require(maxElements >= elements.size) {
                "Can't have more elements in the queue than the max elements."
            }
            return BoundedFifoQueue(
                currentElements = elements.toMutableList(),
                maxElements = maxElements
            )
        }
    }
}