@file:Suppress("UNUSED_VARIABLE")

package org.hexworks.zircon.examples.playground

import kotlinx.coroutines.runBlocking
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TilesetResource
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random
import kotlin.system.exitProcess

object TestPlayground {

    private val tileset = CP437TilesetResources.aesomatica16x16()
    val tiles = listOf(
            Tiles.newBuilder()
                    .withCharacter('x')
                    .withBackgroundColor(ANSITileColor.BRIGHT_WHITE)
                    .withForegroundColor(ANSITileColor.GRAY)
                    .buildCharacterTile(),
            Tiles.newBuilder()
                    .withCharacter('y')
                    .withBackgroundColor(ANSITileColor.RED)
                    .withForegroundColor(ANSITileColor.GREEN)
                    .buildCharacterTile(),
            Tiles.newBuilder()
                    .withCharacter('w')
                    .withBackgroundColor(ANSITileColor.YELLOW)
                    .withForegroundColor(ANSITileColor.BLUE)
                    .buildCharacterTile(),
            Tiles.newBuilder()
                    .withCharacter('z')
                    .withBackgroundColor(ANSITileColor.BRIGHT_CYAN)
                    .withForegroundColor(ANSITileColor.BRIGHT_MAGENTA)
                    .buildCharacterTile())
    val size = 100
    val random = Random(234235)
    val elementsToProduce = 1_000_000

    interface TileComposite {

        fun setTileAt(position: Position, tile: Tile)

        fun createSnapshot(): Snapshot
    }

    data class Snapshot(
            val tilesetResource: TilesetResource,
            val contents: Map<Position, Tile>)

    class ConcurrentTileComposite(
            private val tilesetResource: TilesetResource,
            private val map: MutableMap<Position, Tile> = ConcurrentHashMap()) : TileComposite {

        override fun setTileAt(position: Position, tile: Tile) {
            map[position] = tile
        }

        override fun createSnapshot(): Snapshot {
            return Snapshot(
                    tilesetResource = tilesetResource,
                    contents = map.toMap())
        }
    }

    class ThreadConfinedTileComposite(
            private val tilesetResource: TilesetResource,
            private val map: MutableMap<Position, Tile> = mutableMapOf()) : TileComposite {

        private val executor = Executors.newSingleThreadExecutor()

        override fun setTileAt(position: Position, tile: Tile) {
            executor.submit {
                map[position] = tile
            }
        }

        override fun createSnapshot(): Snapshot {
            return executor.submit<Snapshot> {
                Snapshot(
                        tilesetResource = tilesetResource,
                        contents = map.toMap())
            }.get()
        }
    }


    @JvmStatic
    fun main(args: Array<String>): Unit = runBlocking {

        val composites = mapOf<String, () -> TileComposite>(
                "ConcurrentTileComposite" to { ConcurrentTileComposite(tileset) },
                "ThreadConfinedTileComposite" to { ThreadConfinedTileComposite(tileset) })

        val producer = Executors.newSingleThreadExecutor()
        val consumer = Executors.newSingleThreadExecutor()

        composites.forEach { (name, compositeFn) ->

            val timesToRun = 100

            var totalSnapshots = 0.0
            var totalTime = 0.0

            repeat(timesToRun) {

                val composite = compositeFn()
                for (x in 0..size) {
                    for (y in 0..size) {
                        composite.setTileAt(Positions.create(x, y), tiles[random.nextInt(tiles.size)])
                    }
                }
                val latch = CountDownLatch(1)
                val start = System.nanoTime()

                producer.submit {
                    var count = 0
                    while (count < elementsToProduce) {
//                        composite.setTileAt(
//                                position = Positions.create(random.nextInt(size), random.nextInt(size)),
//                                tile = tiles[random.nextInt(tiles.size)])
                        count++
                    }
                    latch.countDown()
                }
                val result = consumer.submit<Int> {
                    var snapshots = 0
                    while (latch.count > 0) {
                        composite.createSnapshot()
                        snapshots++
                    }
                    snapshots
                }.get(10, TimeUnit.SECONDS)
                val end = System.nanoTime()
                val time = (end - start) / 1000 / 1000

                totalSnapshots += result.toDouble()
                totalTime += time.toDouble()
            }

            println("Average: ${(totalSnapshots * 1000 / totalTime).toInt()} / sec for $name.")
        }

        producer.shutdownNow()
        consumer.shutdownNow()

        exitProcess(0)
    }
}
