package org.hexworks.zircon.internal.util

class DefaultPersistentSet<E>(private val backend: kotlinx.collections.immutable.PersistentSet<E>) : PersistentSet<E> {

    override fun add(element: E): PersistentSet<E> = DefaultPersistentSet(backend.add(element))

    override fun addAll(elements: Collection<E>): PersistentSet<E> = DefaultPersistentSet(backend.addAll(elements))

    override fun remove(element: E): PersistentSet<E> = DefaultPersistentSet(backend.remove(element))

    override fun removeAll(elements: Collection<E>): PersistentSet<E> = DefaultPersistentSet(backend.removeAll(elements))

    override fun removeAll(predicate: (E) -> Boolean): PersistentSet<E> = DefaultPersistentSet(backend.removeAll(predicate))

    override fun clear(): PersistentSet<E> = DefaultPersistentSet(backend.clear())

    override val size: Int = backend.size

    override fun contains(element: E): Boolean = backend.contains(element)

    override fun containsAll(elements: Collection<E>): Boolean = backend.containsAll(elements)

    override fun isEmpty(): Boolean = backend.isEmpty()

    override fun iterator(): Iterator<E> = backend.iterator()
}
