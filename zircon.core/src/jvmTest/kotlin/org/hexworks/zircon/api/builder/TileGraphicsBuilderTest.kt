package org.hexworks.zircon.api.builder

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.data.GraphicalTileBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.junit.Test

class TileGraphicsBuilderTest {

    @Test
    fun shouldBuildProperTileGraphic() {
        val result = TileGraphicsBuilder.newBuilder()
            .withSize(SIZE)
            .build()
            .apply {
                fill(FILLER)
            }

        assertThat(result.size).isEqualTo(SIZE)

        assertThat(result.getTileAtOrNull(Position.create(SIZE.width - 1, SIZE.height - 1)))
            .isEqualTo(FILLER)
    }

    companion object {
        val FILLER = GraphicalTileBuilder.newBuilder().withCharacter('a').build()
        val SIZE = Size.create(5, 5)
    }
}
