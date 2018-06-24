package org.codetome.zircon.internal.behavior.impl

import org.codetome.zircon.api.behavior.FontOverride
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.internal.font.impl.FontSettings

class DefaultFontOverride(initialFont: Font) : FontOverride {

    private var font = initialFont

    override fun resetFont() {
        font = FontSettings.NO_FONT
    }

    override fun getCurrentFont(): Font = font

    override fun useFont(font: Font): Boolean {
        val currentFont = getCurrentFont()
        if (currentFont !== FontSettings.NO_FONT) {
            require(currentFont.getSize() == font.getSize()) {
                "Can't override previous font with size: ${getCurrentFont().getSize()} with a Font with" +
                        " different size: ${font.getSize()}"
            }
        }
        this.font = font
        return true
    }
}
