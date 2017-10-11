package org.codetome.zircon.internal.behavior.impl

import org.codetome.zircon.api.behavior.FontOverride
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.internal.font.impl.FontSettings
import java.awt.image.BufferedImage
import java.util.concurrent.atomic.AtomicReference

class DefaultFontOverride(initialFont: Font<BufferedImage>) : FontOverride<BufferedImage> {

    private val font = AtomicReference<Font<BufferedImage>>(initialFont)

    override fun resetFont() {
        font.set(FontSettings.NO_FONT)
    }

    override fun getCurrentFont(): Font<BufferedImage> = font.get()

    override fun useFont(font: Font<BufferedImage>) {
        if (this.font.get() !== FontSettings.NO_FONT) {
            require(getCurrentFont().getSize() == font.getSize()) {
                "Can't override previous font with size: ${getCurrentFont().getSize()} with a Font with" +
                        " different size: ${font.getSize()}"
            }
        }
        this.font.set(font)
    }
}