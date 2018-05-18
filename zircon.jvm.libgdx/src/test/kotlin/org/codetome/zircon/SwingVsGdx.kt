package org.codetome.zircon

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.font.CharacterMetadata
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.font.FontTextureRegion
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.internal.font.impl.LibgdxFontTextureRegion
import org.codetome.zircon.internal.util.Identifier

object Config {
    val TILESET = CP437TilesetResource.WANDERLUST_16X16
    val WIDTH = 60
    val HEIGHT = 30
    val PIXEL_WIDTH = WIDTH * TILESET.width
    val PIXEL_HEIGHT = HEIGHT * TILESET.height
}

// GDX

class GdxFont(private val source: Texture,
              private val width: Int,
              private val height: Int,
              private val id: Identifier = Identifier.randomIdentifier()) : Font {

    override fun getWidth() = width

    override fun getHeight() = height

    override fun hasDataForChar(char: Char): Boolean {
        return true
    }

    override fun fetchRegionForChar(textCharacter: TextCharacter): FontTextureRegion<TextureRegion> {
        val cp437Idx = CP437TilesetResource.fetchCP437IndexForChar(textCharacter.getCharacter())
        val x = cp437Idx.rem(16) * width
        val y = cp437Idx.div(16) * height
        return LibgdxFontTextureRegion(TextureRegion(source, x, y, width, height))
    }

    override fun fetchMetadataForChar(char: Char): List<CharacterMetadata> {
        TODO("not implemented")
    }

    override fun getId() = id

    init {
        if (!source.textureData.isPrepared) {
            source.textureData.prepare()
        }
    }

}

class GdxExample : ApplicationAdapter() {
    lateinit var batch: SpriteBatch

    val chars = listOf('a', 'b')
    var currIdx = 0
    var loopCount = 0
    var running = true

    override fun create() {
        batch = SpriteBatch()
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()
        val width = Config.TILESET.width.toFloat()
        val height = Config.TILESET.height.toFloat()
        val font = GdxFont(
                source = Texture(Config.TILESET.path.substring(1)),
                width = Config.TILESET.width,
                height = Config.TILESET.height)
            (0..Config.HEIGHT).forEach { y ->
                (0..Config.WIDTH).forEach { x ->

                    val region = font.fetchRegionForChar(
                            TextCharacterBuilder.newBuilder().character(chars[0]).build())
                    val drawable = TextureRegionDrawable(region.getBackend())
                    val tinted = drawable.tint(com.badlogic.gdx.graphics.Color(0.5f, 0.5f, 0f, 1f)) as SpriteDrawable
                    tinted.draw(batch,
                            x * width,
                            y * height + height,
                            width,
                            height)
                }
            }
            currIdx = if (currIdx == 0) 1 else 0

        batch.end()
    }

    override fun dispose() {
        batch.dispose()
    }
}

object GdxLauncher {
    @JvmStatic fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        LwjglApplication(GdxExample(), config)
    }
}

