package org.hexworks.zircon.internal.game

import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.game.impl.DefaultGameArea
import org.junit.After
import org.junit.Before

class DefaultGameAreaTest {

    lateinit var target: DefaultGameArea<Tile, Block<Tile>>

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {
        // target.dispose()
    }

    // TODO: re-implement these
}
