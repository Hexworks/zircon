package org.codetome.zircon.api.builder

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.builder.data.TileBuilder
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.builder.graphics.TileImageBuilder
import org.junit.Test

class TileImageBuilderTest {

    @Test
    fun shouldBuildProperTextImage() {
        val result = TileImageBuilder.newBuilder()
                .size(SIZE)
                .build()
                .fill(FILLER)

        assertThat(result.getBoundableSize()).isEqualTo(SIZE)

        assertThat(result.getTileAt(Position.create(SIZE.xLength - 1, SIZE.yLength - 1)).get())
                .isEqualTo(FILLER)
    }

    companion object {
        val FILLER = TileBuilder.newBuilder().character('a').build()
        val SIZE = Size.create(5, 5)
    }
}
