package org.hexworks.zircon.internal.data

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.data.block
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.data.Tile
import org.junit.Test

class DefaultBlockTest {

    @Test
    fun shouldProperlyFetchSide() {
        val top = characterTile { character = 'x' }

        val target = block<Tile> {
            emptyTile = Tile.empty()
            this.top = top
        }

        assertThat(target.top).isEqualTo(top)
    }

    @Test
    fun nonEmptyBlockShouldNotBeEmpty() {
        val top = characterTile { character = 'x' }

        val target = block<Tile> {
            emptyTile = Tile.empty()
            this.top = top
        }

        assertThat(target.isEmpty()).isFalse()
    }

    @Test
    fun emptyBlockShouldNotBeEmpty() {
        val target = block<Tile> {
            emptyTile = Tile.empty()
        }

        assertThat(target.isEmpty()).isTrue()
    }
}
