package org.codetome.zircon

import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.system.measureNanoTime

object RunTimeStats {

    private val stats = ConcurrentHashMap<String, Stat>()

    @JvmStatic
    fun getAllStats() = stats.values.toList()

    @JvmStatic
    fun printStatFor(key: String) {
        println("T: ${System.currentTimeMillis()}, " + stats[key])
    }

    @JvmStatic
    fun <T> addTimedStatFor(key: String, fn: () -> T): T {
        var optResult = Optional.empty<T>()
        val time = measureNanoTime {
            optResult = Optional.of(fn())
        }
        addStatFor(key, time)
        return optResult.get()
    }

    @JvmStatic
    fun addStatFor(key: String, timeNano: Long) {
        stats.getOrPut(key) { Stat(key) }.let { stat ->
            val newStat = stat.copy(name = key,
                    avgTimeNs = stat.avgTimeNs
                            .times(stat.measurements)
                            .plus(timeNano)
                            .div(stat.measurements + 1),
                    measurements = stat.measurements + 1)
            stats[key] = newStat
            if(newStat.measurements.rem(100) == 0L) {
                printStatFor(key)
            }
        }
    }

    private fun addEmptyStatForKeyIfNotPresent(key: String) {
        if (stats.containsKey(key).not()) {
            stats[key] = Stat(key)
        }
    }

    data class Stat(val name: String,
                    val avgTimeNs: Double = 0.toDouble(),
                    val measurements: Long = 0) {

        override fun toString(): String {
            val ms = avgTimeNs / 1000 / 1000
            return "Stats: name='$name', " +
                    "avgTimeMs=$ms, " +
                    "fps=${1000 / ms}, " +
                    "measurements=$measurements"
        }
    }
}
