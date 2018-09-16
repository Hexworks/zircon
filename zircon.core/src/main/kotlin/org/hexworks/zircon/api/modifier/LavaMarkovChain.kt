package org.hexworks.zircon.api.modifier

import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.util.Random

enum class LavaMarkovChain(private val tile: Tile,
                           private val probability: Int) : MarkovData {

    EMPTY(Tile.empty()
            .withBackgroundColor(TileColor.fromString("#750000")), 10) {
        override fun next(): MarkovData {
            var next = fetchNext(SMALL_BUBBLE)
//            if (next == EMPTY) next = fetchNext(HEATED, 50)
            return next
        }
    },
    HEATED(Tile.empty()
            .withBackgroundColor(TileColor.fromString("#930000")), 100) {
        override fun next(): MarkovData {
            return fetchNext(EMPTY)
        }
    },
    SMALL_BUBBLE(Tile.empty()
            .withCharacter(Symbols.INTERPUNCT)
            .withForegroundColor(TileColor.fromString("#ffc98a"))
            .withBackgroundColor(TileColor.fromString("#750000")), 100) {
        override fun next(): MarkovData {
            return fetchNext(MEDIUM_BUBBLE)
        }
    },
    MEDIUM_BUBBLE(Tile.empty()
            .withCharacter(Symbols.BULLET_SMALL)
            .withForegroundColor(TileColor.fromString("#ffd65a"))
            .withBackgroundColor(TileColor.fromString("#930000")), 200) {
        override fun next(): MarkovData {
            return fetchNext(LARGE_BUBBLE)
        }
    },
    LARGE_BUBBLE(Tile.empty()
            .withCharacter(Symbols.BULLET)
            .withForegroundColor(TileColor.fromString("#ffe32a"))
            .withBackgroundColor(TileColor.fromString("#b10000")), 300) {
        override fun next(): MarkovData {
            return fetchNext(EXPLODING_BUBBLE)
        }
    },
    EXPLODING_BUBBLE(Tile.empty()
            .withCharacter(Symbols.SOLAR_SYMBOL)
            .withForegroundColor(TileColor.fromString("#fff00a"))
            .withBackgroundColor(TileColor.fromString("#c90000")), 300) {
        override fun next(): MarkovData {
            return fetchNext(SUBSIDING_BUBBLE_0)
        }
    },
    SUBSIDING_BUBBLE_0(Tile.empty()
            .withCharacter(Symbols.WHITE_CIRCLE)
            .withForegroundColor(TileColor.fromString("#ffe32a"))
            .withBackgroundColor(TileColor.fromString("#b10000")), 200) {
        override fun next(): MarkovData {
            return fetchNext(SUBSIDING_BUBBLE_1)
        }
    },
    SUBSIDING_BUBBLE_1(Tile.empty()
            .withCharacter(Symbols.DEGREE)
            .withForegroundColor(TileColor.fromString("#ffd65a"))
            .withBackgroundColor(TileColor.fromString("#930000")), 200) {
        override fun next(): MarkovData {
            return fetchNext(EMPTY)
        }
    };

    override fun first() = EMPTY

    override fun generateCacheKey() = name

    override fun tile() = tile

    internal fun fetchNext(next: LavaMarkovChain, probability: Int = this.probability): MarkovData {
        return if (probability > random.nextInt(10000)) {
            next
        } else {
            this
        }
    }

    companion object {
        private val random = Random.create()
    }
}
