package org.codetome.zircon.playground

import org.codetome.zircon.playground.Test.ObservableList.ModificationType.ADDITION
import org.codetome.zircon.playground.Test.ObservableList.ModificationType.REMOVAL
import java.util.function.Consumer
import java.util.function.Predicate
import java.util.function.UnaryOperator

class Test {

    fun test() {
        val list = mutableListOf(
                Pojo("name0", "foo0"),
                Pojo("name1", "foo1"))

        val observedList = wrap(list)

        observedList.add(0, Pojo("x", "y"))
    }

    fun <T> wrap(list: MutableList<T>): MutableList<T> {
        return ObservableList(list)
    }

    class ObservableList<T>(private val backend: MutableList<T>) : AbstractMutableList<T>() {

        private enum class ModificationType {
            ADDITION,
            REMOVAL
        }

        private val observers = ModificationType.values().map {
            Pair(it, mutableListOf<Consumer<List<T>>>())
        }.toMap().toMutableMap()

        fun onAddition(callback: Consumer<List<T>>) {
            observers[ADDITION]?.add(callback)
        }

        fun onRemoval(callback: Consumer<List<T>>) {
            observers[REMOVAL]?.add(callback)
        }

        override fun add(index: Int, element: T) {
            backend.add(index, element)

        }

        override fun removeAt(index: Int): T {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun set(index: Int, element: T): T {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override val size: Int
            get() = backend.size

        override fun get(index: Int) = backend[index]

        override fun addAll(elements: Collection<T>) = backend.addAll(elements).also {

        }

        override fun add(element: T): Boolean {
            return super.add(element)
        }

        override fun addAll(index: Int, elements: Collection<T>): Boolean {
            return super.addAll(index, elements)
        }

        override fun clear() {
            super.clear()
        }

        override fun remove(element: T): Boolean {
            return super.remove(element)
        }

        override fun removeAll(elements: Collection<T>): Boolean {
            return super.removeAll(elements)
        }

        override fun removeIf(filter: Predicate<in T>): Boolean {
            return super.removeIf(filter)
        }

        override fun removeRange(fromIndex: Int, toIndex: Int) {
            super.removeRange(fromIndex, toIndex)
        }

        override fun replaceAll(operator: UnaryOperator<T>) {
            super.replaceAll(operator)
        }

        override fun retainAll(elements: Collection<T>): Boolean {
            return super.retainAll(elements)
        }
    }

    data class Pojo(val name: String, val foo: String)
}