package org.hexworks.zircon.internal.util

class DefaultImmutableCollection<E>(
        private val backend: kotlinx.collections.immutable.ImmutableCollection<E>) : ImmutableCollection<E> {

    override val size: Int = backend.size

    override fun contains(element: E): Boolean = backend.contains(element)

    override fun containsAll(elements: Collection<E>): Boolean = backend.containsAll(elements)

    override fun isEmpty(): Boolean = backend.isEmpty()

    override fun iterator(): Iterator<E> = backend.iterator()
}
