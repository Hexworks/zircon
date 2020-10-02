package org.hexworks.zircon.examples.playground

import kotlinx.collections.immutable.persistentHashMapOf
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.benchmark.measureRuntimeOf
import org.hexworks.zircon.benchmark.printRuntimeOf
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.MICROSECONDS
import kotlin.random.Random


object KotlinPlayground {

    private val width = 1920 / 16
    private val depth = 1080 / 16
    private val height = 20
    private val random = Random(23423)

    @JvmStatic
    fun main(args: Array<String>) {

        var pm = persistentHashMapOf<Position, Tile>()
        val mm = mutableMapOf<Position, Tile>()
        val a = arrayOfNulls<Pair<Position, Tile>>(width * depth)

        var counter = 0

        for (x in 0 until width) {
            for (y in 0 until depth) {
                val pos = Position.create(x, y)
                val tile = Tile.randomTile()
                pm = pm.put(pos, tile)
                mm[pos] = tile
                a[counter] = pos to tile
                counter++
            }
        }

        printRuntimeOf(MICROSECONDS, "PersistentMap: ") {
            for (e in pm.entries) {

            }
        }

        printRuntimeOf(MICROSECONDS, "MutableMap: ") {
            val r = mutableMapOf<Position, Tile>()
            for (e in mm.iterator()) {
                r[e.key] = e.value
            }
        }

        printRuntimeOf(MICROSECONDS) {
            a.copyOf()
        }

//        val arrSize = calculateSizeOfRec(a)
//        val posSize = calculateSizeOfRec(Position.create(1, 2)) * width * height * depth
//        val tileSize = calculateSizeOfRec(Tile.randomTile()) * width * height * depth
//
//        println("Total size: ${(arrSize + posSize + tileSize) / 1024}KB")

    }

    fun Tile.Companion.randomTile() = Tile.createCharacterTile(
            random.nextInt(Char.MAX_VALUE.toInt()).toChar(),
            StyleSet.defaultStyle()
    )

}

