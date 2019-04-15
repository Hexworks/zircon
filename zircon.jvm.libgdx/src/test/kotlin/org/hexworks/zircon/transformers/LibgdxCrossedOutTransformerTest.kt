package org.hexworks.zircon.transformers

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.hexworks.zircon.api.Modifiers
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.internal.tileset.impl.DefaultTileTexture
import org.hexworks.zircon.internal.tileset.transformer.LibgdxCrossedOutTransformer
import org.junit.Before
import org.junit.Test
import java.awt.image.BufferedImage

class Java2DCrossedOutTransformerTest {

    lateinit var target: LibgdxCrossedOutTransformer

    @Before
    fun setUp() {
        target = LibgdxCrossedOutTransformer()
    }

    @Test
    fun shouldProperlyRun() {
        val image = TextureRegion()
        target.transform(DefaultTileTexture(WIDTH, HEIGHT, image), CHAR)

        // TODO: check cross?
    }

    companion object {
        val WIDTH = 10
        val HEIGHT = 10
        val CHAR = Tiles.newBuilder()
                .withModifiers(Modifiers.crossedOut())
                .build()
    }
}