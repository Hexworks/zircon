@file:Suppress("UNUSED_VARIABLE", "EXPERIMENTAL_API_USAGE")

package org.hexworks.zircon.examples.playground

import kotlinx.coroutines.runBlocking
import java.util.concurrent.CyclicBarrier
import java.util.concurrent.Executors

object TestPlayground {


    @JvmStatic
    fun main(args: Array<String>): Unit = runBlocking {

        val running = true
        val parallelism = 8
        var shared = IntArray(parallelism) { 0 }
        val sp = Executors.newSingleThreadExecutor()
        val fp = Executors.newFixedThreadPool(parallelism)
        val cb = CyclicBarrier(parallelism + 1) {
            println("Result is: ${shared.reduce(Int::plus)}")
            shared = IntArray(parallelism) { 0 }
        }

        for (i in 0..8) {
            fp.submit {
                while (running) {
                    cb.await()
                    println("Doing work...$i")
                }
            }
        }
        sp.submit {
            while (running) {
                println("Awaiting tasks...")
                cb.await()
                println("Synchronization complete, performing next action...")
            }
        }
    }

}



