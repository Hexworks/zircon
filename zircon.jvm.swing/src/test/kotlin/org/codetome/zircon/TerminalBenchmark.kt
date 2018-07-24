package org.codetome.zircon

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.SwingTerminalBuilder
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.builder.graphics.LayerBuilder
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.interop.Sizes
import org.codetome.zircon.api.interop.TextColors
import org.codetome.zircon.api.resource.CP437TilesetResource
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.system.measureNanoTime

fun main(args: Array<String>) {
    val screen = SwingTerminalBuilder.newBuilder()
            .initialTerminalSize(SIZE)
            .font(CP437TilesetResource.WANDERLUST_16X16.toFont())
            .buildScreen()
    screen.setCursorVisibility(false)

    val random = Random()
    val terminalWidth = 100
    val terminalHeight = 40
    val charCount = terminalWidth * terminalHeight
    val layerCount = 20
    val layerWidth = 20
    val layerHeight = 10
    var layers = (0..layerCount).map {
        LayerBuilder.newBuilder().filler(TextCharacterBuilder.newBuilder()
                .backgroundColor(TextColor.create(random.nextInt(255), random.nextInt(255), random.nextInt(255)))
                .foregroundColor(TextColor.create(random.nextInt(255), random.nextInt(255), random.nextInt(255)))
                .character('x')
                .build())
                .size(Size.create(layerWidth, layerHeight))
                .offset(Position.create(random.nextInt(terminalWidth - layerWidth), random.nextInt(terminalHeight - layerHeight)))
                .build().also {
                    screen.pushLayer(it)
                }
    }

    val chars = listOf('a', 'b')
    val bgColors = listOf(TextColors.fromString("#223344"), TextColors.fromString("#112233"))
    val fgColors = listOf(TextColors.fromString("#ffaaff"), TextColors.fromString("#aaffaa"))

    var currIdx = 0
    var loopCount = 0

    while (true) {
        Stats.addTimedStatFor("terminalBenchmark") {
            screen.setBackgroundColor(bgColors[currIdx])
            screen.setForegroundColor(fgColors[currIdx])
            (0..charCount).forEach {
                screen.putCharacter(chars[currIdx])
            }
            layers.forEach {
                screen.removeLayer(it)
            }
            layers = (0..layerCount).map {
                LayerBuilder.newBuilder().filler(TextCharacterBuilder.newBuilder()
                        .backgroundColor(TextColor.create(random.nextInt(255), random.nextInt(255), random.nextInt(255)))
                        .foregroundColor(TextColor.create(random.nextInt(255), random.nextInt(255), random.nextInt(255)))
                        .character('x')
                        .build())
                        .size(Size.create(layerWidth, layerHeight))
                        .offset(Position.create(random.nextInt(terminalWidth - layerWidth), random.nextInt(terminalHeight - layerHeight)))
                        .build().also {
                            screen.pushLayer(it)
                        }
            }
            screen.refresh()
            screen.putCursorAt(Position.defaultPosition())
            currIdx = if (currIdx == 0) 1 else 0
            loopCount++
        }
        if (loopCount.rem(100) == 0) {
            Stats.printStats()
        }
    }
}

val SIZE = Sizes.create(60, 30)


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
            val ms = avgTimeNs * weight / 1000 / 1000
            return "Stat(name='$name', " +
                    "avgTimeMs=$ms, " +
                    "fps=${1000 / ms}" +
                    "measurements=$measurements, " +
                    "weight = $weight)"
        }
    }
}
