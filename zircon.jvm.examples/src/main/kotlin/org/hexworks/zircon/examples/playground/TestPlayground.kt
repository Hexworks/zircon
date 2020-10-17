@file:Suppress("UNUSED_VARIABLE", "EXPERIMENTAL_API_USAGE")

package org.hexworks.zircon.examples.playground

import kotlinx.coroutines.runBlocking

object TestPlayground {


    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        val interval = 100/7
        println("interval: $interval")

        println(100/interval)
    }

}



