@file:Suppress("UNUSED_VARIABLE", "EXPERIMENTAL_API_USAGE")

package org.hexworks.zircon.examples.playground

import kotlinx.coroutines.*

object TestPlayground {


    @JvmStatic
    fun main(args: Array<String>): Unit = runBlocking {
    }

    class ReusableCoroutines(
            private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    ) : CoroutineScope by scope {

        private lateinit var job: Job
        private var stopped: Boolean = false
        private var started: Boolean = false

        @Synchronized
        fun start() {
            require(stopped.not()) {
                "Already stopped!"
            }
            require(started.not()) {
                "Already started!"
            }
            job = launch {
                println("Started and running!")
                while (true) {

                }
            }
        }

        @Synchronized
        fun stop() {
            require(stopped.not()) {
                "Already stopped!"
            }
            stopped = true
            cancel()
            println("Stopped")
        }

    }

}



