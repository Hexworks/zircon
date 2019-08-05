@file:Suppress("UNUSED_VARIABLE")

package org.hexworks.zircon.examples.playground

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.coroutines.runBlocking
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import java.text.DecimalFormat
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random
import kotlin.system.exitProcess
import kotlin.system.measureNanoTime

const val SIZE = 100
const val TEST_TIME_NS = 60_000_000_000L
val RANDOM = Random(234235)
val TILESET = CP437TilesetResources.aesomatica16x16()
val TILES = listOf(
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

object TestPlayground {

    @JvmStatic
    fun main(args: Array<String>): Unit = runBlocking {
        Benchmark(PersistentTileGrid()).run()
    }
}

class Benchmark(private val grid: TileGrid) {

    private val producer = Executors.newSingleThreadExecutor()
    private val consumer = Executors.newSingleThreadExecutor()
    private val decimalFormat = DecimalFormat().apply {
        groupingSize = 3
    }

    @Volatile
    private var readCount = 0
    @Volatile
    private var writeCount = 0
    @Volatile
    private var totalTime = 0L

    fun run() {
        for (z in 1..SIZE) {
            grid.pushLayer(LayerProxy(SimpleLayer()))
        }
        val startLatch = CountDownLatch(1)
        val endLatch = CountDownLatch(1)

        totalTime += measureNanoTime {

            producer.submit {
                val startTime = System.nanoTime()
                var currTime = startTime
                try {
                    startLatch.await()
                    while (currTime - startTime < TEST_TIME_NS) {
                        currTime = System.nanoTime()
                        val layer = grid.getLayerAt(RANDOM.nextInt(SIZE)).get()
                        if (writeCount % 2 == 0) {
                            grid
                        } else {
                            layer.moveTo(createRandomPosition())
                            layer
                        }.setTileAt(
                                position = createRandomPosition(),
                                tile = fetchRandomTile())
                        writeCount++
                    }
                    endLatch.countDown()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            consumer.submit {
                try {
                    startLatch.countDown()
                    while (endLatch.count > 0) {
                        grid.createSnapshot()
                        readCount++
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            endLatch.await()
        }
        val timeSeconds = TimeUnit.NANOSECONDS.toSeconds(totalTime)

        println("Benchmark Results for '${grid::class.simpleName}':")
        println("Time elapsed: ${decimalFormat.format(timeSeconds)}s")
        println("Reads: ${decimalFormat.format(readCount)}")
        println("Writes: ${decimalFormat.format(writeCount)}")
        println("Read speed: ${decimalFormat.format(readCount / timeSeconds)} reads / s")
        println("Write speed: ${decimalFormat.format(writeCount / timeSeconds)} writes / s")

        producer.shutdownNow()
        consumer.shutdownNow()

        exitProcess(1)
    }

    private fun fetchRandomTile() = TILES[RANDOM.nextInt(TILES.size)]

    private fun createRandomPosition() = Positions.create(RANDOM.nextInt(SIZE), RANDOM.nextInt(SIZE))
}


interface TileComposite {

    fun setTileAt(position: Position, tile: Tile)

    fun createSnapshot(): Snapshot
}

interface TileGrid : TileComposite {

    fun pushLayer(layer: Layer): Int

    fun popLayer(): Maybe<Layer>

    fun getLayerAt(index: Int): Maybe<Layer>

    fun setLayerAt(index: Int, layer: Layer)

    fun mutate(fn: (TileGrid) -> Unit)
}

interface Layer : TileComposite {

    val position: Position
    val content: PersistentMap<Position, Tile>

    fun moveTo(position: Position)
}

class LayerProxy(var backend: Layer) : Layer {

    override val position: Position
        get() = backend.position

    override val content: PersistentMap<Position, Tile>
        get() = backend.content

    override fun createSnapshot(): Snapshot {
        return backend.createSnapshot()
    }

    override fun setTileAt(position: Position, tile: Tile) {
        backend.setTileAt(position, tile)
    }

    override fun moveTo(position: Position) {
        backend.moveTo(position)
    }
}


class SimpleLayer(override var position: Position = Positions.zero(),
                  override var content: PersistentMap<Position, Tile> = persistentMapOf())
    : Layer {

    override fun createSnapshot() = Snapshot(contents = listOf(this))

    override fun moveTo(position: Position) {
        this.position = position
    }

    override fun setTileAt(position: Position, tile: Tile) {
        content = content.put(position, tile)
    }
}

data class AttachedLayer(private val backend: Layer,
                         private val index: Int,
                         private val tileGrid: TileGrid) : Layer {

    override val position: Position
        get() = backend.position

    override val content: PersistentMap<Position, Tile>
        get() = backend.content

    override fun createSnapshot() = backend.createSnapshot()

    override fun setTileAt(position: Position, tile: Tile) {
        backend.setTileAt(position, tile)
    }

    override fun moveTo(position: Position) {
        tileGrid.mutate {
            tileGrid.setLayerAt(index, copy(backend = backend.apply { moveTo(position) }))
        }
    }
}

data class Snapshot(
        val contents: List<Layer>)

class PersistentTileGrid(
        private var contents: PersistentList<Layer> = persistentListOf(LayerProxy(SimpleLayer()))) : TileGrid {

    private val executor = Executors.newSingleThreadExecutor()

    override fun createSnapshot() = Snapshot(
            contents = contents)

    override fun setTileAt(position: Position, tile: Tile) {
        executor.submit {
            contents = contents.set(0, contents[0].apply {
                setTileAt(position, tile)
            })
        }.get()
    }

    override fun getLayerAt(index: Int): Maybe<Layer> {
        return Maybe.ofNullable(contents.getOrNull(index))
    }

    override fun setLayerAt(index: Int, layer: Layer) {
        executor.submit {
            if (layer is LayerProxy) {
                layer.backend = AttachedLayer(
                        backend = layer.backend,
                        index = index,
                        tileGrid = this)
            }
            contents = contents.set(index, layer)
        }.get()
    }

    override fun pushLayer(layer: Layer): Int {
        return executor.submit<Int> {
            val idx = contents.lastIndex + 1
            if (layer is LayerProxy) {
                layer.backend = AttachedLayer(
                        backend = layer.backend,
                        index = idx,
                        tileGrid = this)
            }
            contents = contents.add(layer)
            idx
        }.get()
    }

    override fun popLayer(): Maybe<Layer> {
        return executor.submit<Maybe<Layer>> {
            val result = if (contents.isEmpty()) {
                Maybe.empty()
            } else Maybe.of(contents[contents.lastIndex])
            contents = contents.removeAt(contents.lastIndex)
            result
        }.get()
    }

    override fun mutate(fn: (TileGrid) -> Unit) {
        executor.submit {
            fn(this)
        }
    }
}
