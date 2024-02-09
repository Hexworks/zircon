package org.hexworks.zircon.internal.util

import org.hexworks.zircon.internal.behavior.impl.DefaultObservable

/**
 * Queue that has a fixed maximum size. After this
 * size is reached the oldest added element is removed
 * whenever a new element is added
 */
class BoundedFifoQueue<T : Any> private constructor(
    private val currentElements: MutableList<T>,
    private val maxElements: Int
) {

    private val observable = DefaultObservable<T>()

    val elements: List<T>
        get() = currentElements.toList()
    val size: Int
        get() = currentElements.size
    val lastIndex: Int
        get() = currentElements.lastIndex


    operator fun get(index: Int) = currentElements[index]

    fun add(element: T) {
        currentElements.add(element)
        if (currentElements.size > maxElements) {
            observable.notifyObservers(currentElements.removeAt(0))
        }
    }

    fun subList(fromIndex: Int, toIndex: Int): List<T> {
        return currentElements.subList(fromIndex, toIndex)
    }

    fun onKick(fn: (element: T) -> Unit) = observable.addObserver(fn)

    companion object {
        fun <T : Any> create(
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