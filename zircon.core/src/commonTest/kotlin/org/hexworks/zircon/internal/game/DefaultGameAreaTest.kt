package org.hexworks.zircon.internal.game

import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.game.impl.DefaultGameArea
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class DefaultGameAreaTest {

    lateinit var target: DefaultGameArea<Tile, Block<Tile>>

    @BeforeTest
    fun setUp() {

    }

    @AfterTest
    fun tearDown() {
        // target.dispose()
    }

    // TODO: re-implement these
}
