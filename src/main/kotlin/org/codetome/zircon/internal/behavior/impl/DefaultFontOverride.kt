package org.codetome.zircon.internal.behavior.impl

import org.codetome.zircon.api.behavior.FontOverride
import org.codetome.zircon.api.font.Font
import java.util.concurrent.atomic.AtomicReference

class DefaultFontOverride<R>(initialFont: Font<R>) : FontOverride<R> {

    private val font = AtomicReference<Font<R>>(initialFont)

    override fun getCurrentFont() = font.get()

    override fun useFont(font: Font<R>) {
        this.font.set(font)
    }
}