package org.codetome.zircon.beta

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.TerminalBuilder
import org.codetome.zircon.api.builder.VirtualTerminalBuilder
import org.codetome.zircon.api.color.TextColorFactory
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.junit.Test
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.system.measureNanoTime

fun main(args:Array<String>) {
    val terminal = VirtualTerminalBuilder.newBuilder()
            .initialTerminalSize(SIZE)
            .font(CP437TilesetResource.WANDERLUST_16X16.toFont())
            .build()
    terminal.setCursorVisibility(false)

    val charCount = 60 * 30
    val chars = listOf('a', 'b')
    val bgColors = listOf(TextColorFactory.fromString("#223344"), TextColorFactory.fromString("#112233"))
    val fgColors = listOf(TextColorFactory.fromString("#ffaaff"), TextColorFactory.fromString("#aaffaa"))

    var currIdx = 0
    var loopCount = 0
    while(true) {
        Stats.addTimedStatFor("terminalBenchmark") {
            terminal.setBackgroundColor(bgColors[currIdx])
            terminal.setForegroundColor(fgColors[currIdx])
            (0..charCount).forEach {
                terminal.putCharacter(chars[currIdx])
            }
            terminal.flush()
            terminal.putCursorAt(Position.DEFAULT_POSITION)
            currIdx = if(currIdx == 0) 1 else 0
            loopCount++
        }
        if(loopCount.rem(100) == 0) {
            Stats.printStats()
        }
    }
}

val SIZE = Size.of(60, 30)


object Stats {

    private val stats = ConcurrentHashMap<String, Stat>()

    @JvmStatic
    fun getAllStats() = stats.values.toList()

    @JvmStatic
    fun printStats() {
        println("==================== S T A T S ====================")
        println(getAllStats().joinToString("\n"))
        println("===================================================")
    }

    @JvmStatic
    fun addWeightFor(key: String, weight: Long) {
        addEmptyStatForKeyIfNotPresent(key)
        stats[key] = stats[key]!!.copy(weight = weight)
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
    fun addStatFor(key: String, timeMs: Long) {
        addEmptyStatForKeyIfNotPresent(key)
        stats[key] = stats[key]!!.let { stat ->
            stat.copy(name = key,
                    avgTimeNs = stat.avgTimeNs
                            .times(stat.measurements)
                            .plus(timeMs)
                            .div(stat.measurements + 1),
                    measurements = stat.measurements + 1)
        }
    }

    private fun addEmptyStatForKeyIfNotPresent(key: String) {
        if (stats.containsKey(key).not()) {
            stats[key] = Stat(key)
        }
    }

    data class Stat(val name: String,
                    val avgTimeNs: Double = 0.toDouble(),
                    val measurements: Long = 0,
                    val weight: Long = 1) {

        override fun toString(): String {
            return "Stat(name='$name', " +
                    "avgTimeMs=${avgTimeNs * weight / 1000 / 1000}, " +
                    "measurements=$measurements, " +
                    "weight = $weight)"
        }
    }
}
