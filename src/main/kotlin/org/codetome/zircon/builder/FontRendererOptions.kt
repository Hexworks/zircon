package org.codetome.zircon.builder

class FontRendererOptions {

    /**
     * Will use Swing as graphical display.
     * Currently this is the only option.
     */
    fun useSwing() = SwingFontRendererBuilder()
}