@file:Suppress("UNUSED_VARIABLE", "MayBeConstant", "EXPERIMENTAL_API_USAGE")

package org.hexworks.zircon.examples.playground

import kotlinx.collections.immutable.persistentListOf


object KotlinPlayground {

    @JvmStatic
    fun main(args: Array<String>) {

        var list = persistentListOf("A", "B", "C")

        list = list.add(1, "X")

        println(list)
    }
}
