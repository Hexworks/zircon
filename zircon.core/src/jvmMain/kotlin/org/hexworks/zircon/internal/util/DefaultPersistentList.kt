package org.hexworks.zircon.internal.util

class DefaultPersistentList<E>(
        private val backend: kotlinx.collections.immutable.PersistentList<E>) : PersistentList<E> {

    override fun add(element: E): PersistentList<E> = DefaultPersistentList(backend.add(element))

    override fun addAll(elements: Collection<E>): PersistentList<E> = DefaultPersistentList(backend.addAll(elements))

    override fun remove(element: E): PersistentList<E> = DefaultPersistentList(backend.remove(element))

    override fun removeAll(elements: Collection<E>): PersistentList<E> = DefaultPersistentList(backend.removeAll(elements))

    override fun removeAll(predicate: (E) -> Boolean): PersistentList<E> = DefaultPersistentList(backend.removeAll(predicate))

    override fun clear(): PersistentList<E> = DefaultPersistentList(backend.clear())

    override fun addAll(index: Int, c: Collection<E>): PersistentList<E> = DefaultPersistentList(backend.addAll(index, c))

    override fun set(index: Int, element: E): PersistentList<E> = DefaultPersistentList(backend.set(index, element))

    override fun add(index: Int, element: E): PersistentList<E> = DefaultPersistentList(backend.add(index, element))

    override fun removeAt(index: Int): PersistentList<E> = DefaultPersistentList(backend.removeAt(index))

    override val size: Int = backend.size

    override fun contains(element: E): Boolean = backend.contains(element)

    override fun containsAll(elements: Collection<E>): Boolean = backend.containsAll(elements)

    override fun get(index: Int): E = backend[index]

    override fun indexOf(element: E): Int = backend.indexOf(element)

    override fun isEmpty(): Boolean = backend.isEmpty()

    override fun iterator(): Iterator<E> = backend.iterator()

    override fun lastIndexOf(element: E): Int = backend.lastIndexOf(element)

    override fun listIterator(): ListIterator<E> = backend.listIterator()

    override fun listIterator(index: Int): ListIterator<E> = backend.listIterator(index)

    override fun subList(fromIndex: Int, toIndex: Int): List<E> = backend.subList(fromIndex, toIndex)
}
