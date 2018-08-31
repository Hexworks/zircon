package org.hexworks.zircon.api.resource

import org.hexworks.zircon.internal.util.AwtFontUtils
import org.junit.Test
import java.awt.Font
import java.awt.GraphicsEnvironment

class BuiltInMonospaceFontResourceTest {

    @Test
    fun test() {

        val fr = BuiltInMonospaceFontResource.TEST

        val f = Font.createFont(
                Font.TRUETYPE_FONT,
                this::class.java.getResourceAsStream(fr.path))
        val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
        ge.registerFont(f)

        val font = f.deriveFont(fr.height.toFloat())

        require(AwtFontUtils.isFontMonospaced(font))
        println("Width: ${AwtFontUtils.getFontWidth(font)}, height: ${AwtFontUtils.getFontHeight(font)}")

    }
}
