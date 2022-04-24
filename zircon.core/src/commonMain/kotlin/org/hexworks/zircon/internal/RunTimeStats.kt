package org.hexworks.zircon.internal

import org.hexworks.zircon.platform.util.SystemUtils
import kotlin.jvm.Synchronized

object RunTimeStats {

    private val stats = mutableMapOf<String, Stat>()

    fun getAllStats() = stats.values.toList()

    @Synchronized
    fun printStatFor(key: String) {
        println("T: ${SystemUtils.getCurrentTimeMs()}, " + stats[key])
    }

    @Synchronized
    fun <T> addTimedStatFor(key: String, fn: () -> T): T? {
        var result: T? = null
        val time = SystemUtils.measureNanoTime {
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

        private val measurements: ArrayDeque<Long> = ArrayDeque()

        fun addMeasurement(measurementNs: Long) {
            if (measurements.size >= measurementLimit) {
                measurements.removeFirst()
            }
            measurements.addLast(measurementNs)
            measurementCount++
        }

        override fun toString(): String {
            val avgTimeNs = measurements.fold(0) { acc: Long, t: Long -> acc + t }
                .div(measurements.size)
            val ms = avgTimeNs.toDouble() / 1000 / 1000
            return "Stats: name='$name', " +
                "avgTimeMs=$ms, " +
                "fps=${1000 / ms}, " +
                "measurements=$measurementCount"
        }
    }
}
