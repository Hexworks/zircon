package org.hexworks.zircon.internal.data

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.builder.data.block
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.data.Tile
import kotlin.test.Test

class DefaultBlockTest {

    @Test
    fun shouldProperlyFetchSide() {
        val top = characterTile { character = 'x' }

        val target = block<Tile> {
            emptyTile = Tile.empty()
            this.top = top
        }

        target.top shouldBe top
    }

    @Test
    fun nonEmptyBlockShouldNotBeEmpty() {
        val top = characterTile { character = 'x' }

        val target = block<Tile> {
            emptyTile = Tile.empty()
            this.top = top
        }

        target.isEmpty() shouldBe false
    }

    @Test
    fun emptyBlockShouldNotBeEmpty() {
        val target = block<Tile> {
            emptyTile = Tile.empty()
        }

        target.isEmpty() shouldBe true
    }
}
