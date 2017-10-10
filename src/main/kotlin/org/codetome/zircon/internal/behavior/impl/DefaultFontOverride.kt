package org.codetome.zircon.internal.behavior.impl

import org.codetome.zircon.api.behavior.FontOverride
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.internal.font.impl.FontSettings
import java.util.concurrent.atomic.AtomicReference

class DefaultFontOverride<R>(initialFont: Font<R>) : FontOverride<R> {

    private val font = AtomicReference<Font<R>>(initialFont)

    override fun getCurrentFont(): Font<R> = font.get()

    override fun useFont(font: Font<R>) {
        // TODO: test this
        if (this.font.get() !== FontSettings.NO_FONT) {
            require(getCurrentFont().getSize() == font.getSize()) {
                "Can't override previous font with size: ${getCurrentFont().getSize()} with a Font with" +
                        " different size: ${font.getSize()}"
            }
        }
        this.font.set(font)
    }
}