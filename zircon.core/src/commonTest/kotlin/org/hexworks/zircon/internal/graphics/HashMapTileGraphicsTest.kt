package org.hexworks.zircon.internal.graphics

import kotlin.test.BeforeTest
import kotlin.test.Test

class HashMapTileGraphicsTest : TileGraphicsTest() {

    override lateinit var target: InternalTileGraphics

    @BeforeTest
    override fun setUp() {
        target = HashMapTileGraphics(
            initialSize = SIZE_OF_3X3,
            initialTileset = TILESET
        )
    }

    @Test
    fun test() {

    }

}
