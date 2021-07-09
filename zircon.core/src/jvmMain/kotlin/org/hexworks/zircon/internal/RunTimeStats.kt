package org.hexworks.zircon.internal

import java.math.BigDecimal
import java.util.concurrent.ConcurrentHashMap
import kotlin.system.measureNanoTime

object RunTimeStats {

    private val stats = ConcurrentHashMap<String, Stat>()

    fun getAllStats() = stats.values.toList()

    @Synchronized
    fun printStatFor(key: String) {
        println("T: ${System.currentTimeMillis()}, " + stats[key])
    }

    @Synchronized
    fun <T> addTimedStatFor(key: String, fn: () -> T): T {
        var result: T
        val time = measureNanoTime {
            result = fn()
        }
        addStatFor(key, time)
        return result
    }

    private fun addStatFor(key: String, timeNano: Long) {
        stats.getOrPut(key) { Stat(key) }.let { stat ->
            stat.addMeasurement(timeNano)
            if (stat.measurementCount.rem(100) == 0L) {
                printStatFor(key)
            }
        }
    }

    class Stat(
        val name: String,
        private val measurementLimit: Long = 500
    ) {

        var measurementCount = 0L
            private set

        private val measurements: ArrayDeque<BigDecimal> = ArrayDeque()

        fun addMeasurement(measurementNs: Long) {
            if (measurements.size >= measurementLimit) {
                measurements.removeFirst()
            }
            measurements.addLast(BigDecimal("$measurementNs"))
            measurementCount++
        }

        override fun toString(): String {
            val avgTimeNs = measurements.fold(BigDecimal("0"), BigDecimal::plus)
                .div(BigDecimal("${measurements.size}"))
            val ms = avgTimeNs.toDouble() / 1000 / 1000
            return "Stats: name='$name', " +
                    "avgTimeMs=$ms, " +
                    "fps=${1000 / ms}, " +
                    "measurements=$measurementCount"
        }
    }
}
