package org.hexworks.zircon.api.builder

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.builder.graphics.TileGraphicBuilder
import org.junit.Test

class TileGraphicBuilderTest {

    @Test
    fun shouldBuildProperTileGraphic() {
        val result = TileGraphicBuilder.newBuilder()
                .size(SIZE)
                .build()
                .withFiller(FILLER)

        assertThat(result.size()).isEqualTo(SIZE)

        assertThat(result.getTileAt(Position.create(SIZE.xLength - 1, SIZE.yLength - 1)).get())
                .isEqualTo(FILLER)
    }

    companion object {
        val FILLER = TileBuilder.newBuilder().character('a').build()
        val SIZE = Size.create(5, 5)
    }
}
