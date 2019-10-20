package org.hexworks.zircon.internal.data

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.data.BlockBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.Tile
import org.junit.Test

class DefaultBlockTest {

    @Test
    fun shouldProperlyFetchSide() {
        val top = Tile.createCharacterTile('x', StyleSetBuilder.newBuilder().build())

        val target = BlockBuilder.newBuilder<Tile>()
                .withEmptyTile(Tile.empty())
                .withTop(top)
                .build()

        assertThat(target.top).isEqualTo(top)
    }

    @Test
    fun nonEmptyBlockShouldNotBeEmpty() {
        val top = Tile.createCharacterTile('x', StyleSetBuilder.newBuilder().build())

        val target = BlockBuilder.newBuilder<Tile>()
                .withEmptyTile(Tile.empty())
                .withTop(top)
                .build()

        assertThat(target.isEmpty()).isFalse()
    }

    @Test
    fun emptyBlockShouldNotBeEmpty() {
        val target = BlockBuilder.newBuilder<Tile>()
                .withEmptyTile(Tile.empty())
                .build()

        assertThat(target.isEmpty()).isTrue()
    }
}
