package org.hexworks.zircon.internal.graphics

import kotlin.test.BeforeTest
import kotlin.test.Test

class PersistentTileGraphicsTest : TileGraphicsTest() {

    override lateinit var target: InternalTileGraphics

    @BeforeTest
    override fun setUp() {
        target = PersistentTileGraphics(
                initialSize = SIZE_OF_3X3,
                initialTileset = TILESET
        )
    }

    @Test
    fun test() {

    }

}
