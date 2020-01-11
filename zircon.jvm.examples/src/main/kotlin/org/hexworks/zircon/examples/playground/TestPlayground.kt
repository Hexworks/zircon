@file:Suppress("UNUSED_VARIABLE", "EXPERIMENTAL_API_USAGE")

package org.hexworks.zircon.examples.playground

import kotlinx.collections.immutable.persistentMapOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.runBlocking
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random
import kotlin.system.measureNanoTime


object TestPlayground {


    interface Layer {

        val contents: Map<Position, Tile>

        fun setTileAt(position: Position, tile: Tile)
    }

    class ThreadSafeLayer : Layer {

        override var contents = persistentMapOf<Position, Tile>()
            @Synchronized
            get

        @Synchronized
        override fun setTileAt(position: Position, tile: Tile) {
            contents = contents.put(position, tile)
        }

    }

    sealed class LayerMsg {

        data class SetTileAt(val position: Position,
                             val tile: Tile) : LayerMsg()

    }

    class ActorLayer : Layer, CoroutineScope {

        override val coroutineContext = Dispatchers.Default

        override var contents = persistentMapOf<Position, Tile>()
            private set

        private val actor = actor<LayerMsg> {
            var counter = 0 // actor state
            for (msg in channel) { // iterate over incoming messages
                when (msg) {
                    is LayerMsg.SetTileAt -> {
                        contents = contents.put(msg.position, msg.tile)
                    }
                }
            }
        }

        override fun setTileAt(position: Position, tile: Tile) {
            runBlocking {
                actor.send(LayerMsg.SetTileAt(position, tile))
            }
        }

    }


    @JvmStatic
    fun main(args: Array<String>) {

        val testsToRun = 10

        val tiles = listOf(
                Tile.defaultTile().withCharacter('a'),
                Tile.defaultTile().withCharacter('b'),
                Tile.defaultTile().withCharacter('c'))
        val tileCount = tiles.size

        val random = Random(65486)
        val writesToDo = 1000000

        val reader = Executors.newFixedThreadPool(1)
        val writer = Executors.newFixedThreadPool(1)

        val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
        val symbols = formatter.decimalFormatSymbols
        symbols.groupingSeparator = ' '
        formatter.decimalFormatSymbols = symbols

        repeat(testsToRun) {

            val layer = ActorLayer()
            val latch = CountDownLatch(writesToDo)
            var reads = 0

            val timeNs = measureNanoTime {
                var running = true
                reader.submit {
                    while (running) {
                        layer.contents
                        reads++
                    }
                }
                writer.submit {
                    while (running) {
                        layer.setTileAt(
                                position = Position.create(random.nextInt(1000), random.nextInt(1000)),
                                tile = tiles[random.nextInt(tileCount)])
                        latch.countDown()
                    }
                }
                latch.await(1, TimeUnit.MINUTES)
                running = false
            }

            val totalMs = timeNs.toDouble().div(1000).div(1000)
            val wps = writesToDo.div(totalMs.div(1000))

            println("Benchmark ran in ${formatter.format(totalMs.toLong())}ms." +
                    " Reads: ${formatter.format(reads)}." +
                    " Writes: ${formatter.format(writesToDo)}." +
                    " WPS: ${formatter.format(wps.toLong())}/s.")
        }

    }

}
