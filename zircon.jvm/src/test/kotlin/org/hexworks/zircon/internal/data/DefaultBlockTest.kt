package org.hexworks.zircon.internal.data

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.data.BlockBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.data.BlockSide
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.junit.Test

class DefaultBlockTest {

    @Test
    fun shouldProperlyFetchSide() {
        val top = Tile.createCharacterTile('x', StyleSetBuilder.newBuilder().build())

        val target = BlockBuilder.create()
                .withPosition(Position.defaultPosition())
                .withTop(top)
                .build()

        assertThat(target.fetchSide(BlockSide.TOP))
                .isEqualTo(top)
    }

    @Test
    fun nonEmptyBlockShouldNotBeEmpty() {
        val top = Tile.createCharacterTile('x', StyleSetBuilder.newBuilder().build())

        val target = BlockBuilder.create()
                .withPosition(Position.defaultPosition())
                .withTop(top)
                .build()

        assertThat(target.isEmpty()).isFalse()
    }

    @Test
    fun emptyBlockShouldNotBeEmpty() {
        val target = BlockBuilder.create()
                .withPosition(Position.defaultPosition())
                .build()

        assertThat(target.isEmpty()).isTrue()
    }
}
