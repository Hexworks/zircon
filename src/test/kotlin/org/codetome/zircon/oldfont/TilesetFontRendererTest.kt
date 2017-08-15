package org.codetome.zircon.oldfont

import org.codetome.zircon.TextCharacter
import org.codetome.zircon.font.resource.DFTilesetResource
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TilesetFontRendererTest {

    lateinit var target: TilesetFontRenderer<Sprite<String>, String, String>

    @Mock
    lateinit var charImgRendererMock: CharacterImageRenderer<String, String>

    @Mock
    lateinit var spriteMock: Sprite<String>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        target = TilesetFontRenderer(
                width = WIDTH,
                height = HEIGHT,
                renderer = charImgRendererMock,
                sprite = spriteMock)
    }

    @Test
    fun testRendering() {
        val cp437Idx = DFTilesetResource.fetchCP437IndexForChar(CHAR.getCharacter())
        val spriteX = cp437Idx.rem(16) * WIDTH
        val spriteY = cp437Idx.div(16) * HEIGHT

        Mockito.`when`(spriteMock.getSubImage(spriteX, spriteY, WIDTH, HEIGHT))
                .thenReturn(SUB_IMAGE)

        target.renderCharacter(CHAR, SURFACE, X, Y)

        Mockito.verify(charImgRendererMock).renderFromImage(
                foregroundColor = CHAR.getForegroundColor(),
                backgroundColor = CHAR.getBackgroundColor(),
                image = SUB_IMAGE,
                surface = SURFACE,
                x = X,
                y = Y)
    }

    companion object {
        val WIDTH = 20
        val HEIGHT = 20
        val CHAR = TextCharacter.builder()
                .character('x')
                .build()
        val X = 4
        val Y = 5
        val SUB_IMAGE = "subImage"
        val SURFACE = "surface"
    }


}