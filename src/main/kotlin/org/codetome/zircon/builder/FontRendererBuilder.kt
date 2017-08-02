package org.codetome.zircon.builder


/**
 * FontRenderer configuration class for [org.codetome.zircon.terminal.swing.SwingTerminalComponent].
 */
object FontRendererBuilder {

    /**
     * Returns the default font config which will use [java.awt.Font]s
     * which are available on your system.
     */
    @JvmStatic
    fun getDefault() = newBuilder()
            .useSwing()
            .usePhysicalFonts()
            .build()

    @JvmStatic
    fun newBuilder() = FontRendererOptions()
}
