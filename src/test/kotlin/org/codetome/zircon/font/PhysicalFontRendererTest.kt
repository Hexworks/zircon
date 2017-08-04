package org.codetome.zircon.font

import org.codetome.zircon.TextCharacter
import org.codetome.zircon.util.FontUtils
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PhysicalFontRendererTest {

    lateinit var target: PhysicalFontRenderer<String, String>

    @Mock
    lateinit var charImgRendererMock: CharacterImageRenderer<String, String>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        target = PhysicalFontRenderer(
                fontsInOrderOfPriority = FontUtils.selectDefaultFont(),
                characterImageRenderer = charImgRendererMock,
                antiAliased = ANTI_ALIASED,
                width = WIDTH,
                height = HEIGHT)
    }

    // headless exception needs to be fixed
    @Ignore
    @Test
    fun testRendering() {
        target.renderCharacter(
                textCharacter = CHAR,
                surface = SURFACE,
                x = X,
                y = Y)

        Mockito.verify(charImgRendererMock).renderFromFont(
                textCharacter = CHAR,
                font = FontUtils.selectDefaultFont().first { it.canDisplay(CHAR.getCharacter()) },
                surface = SURFACE,
                x = X,
                y = Y)
    }


    companion object {
        val ANTI_ALIASED = true
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