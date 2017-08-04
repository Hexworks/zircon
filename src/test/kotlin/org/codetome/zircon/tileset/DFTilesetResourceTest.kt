package org.codetome.zircon.tileset

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DFTilesetResourceTest {

    @Test
    fun test() {
        println(listOf(true, true) == listOf(true, false))
    }

    @Test
    fun testLookup() {
        DFTilesetResource.UNICODE_TO_CP437_LOOKUP.forEach { k, v ->
            assertThat(DFTilesetResource.fetchCP437IndexForChar(k.toChar()  ))
                    .isEqualTo(v)
        }
    }
}