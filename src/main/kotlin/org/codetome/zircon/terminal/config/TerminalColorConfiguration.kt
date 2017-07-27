package org.codetome.zircon.terminal.config

import org.codetome.zircon.TextColor

/**
 * Color configuration settings to be using with [org.codetome.zircon.terminal.swing.SwingTerminalComponent].
 * This class contains color-related settings that is used
 * by [org.codetome.zircon.terminal.swing.SwingTerminalComponent] when it renders the component.
 */
class TerminalColorConfiguration private constructor(
        private val colorPalette: TerminalEmulatorPalette,
        private val useBrightColorsOnBold: Boolean = true) {

    /**
     * Given a TextColor and a hint as to if the color is to be used as foreground or not and if we
     * currently have bold text enabled or not, it returns the closest color that matches this.
     */
    fun toAWTColor(color: TextColor, isForeground: Boolean, inBoldContext: Boolean): java.awt.Color {
        if (color is TextColor.ANSI) {
            return colorPalette.get(color, isForeground, inBoldContext && useBrightColorsOnBold)
        }
        return color.toColor()
    }

    companion object {

        /**
         * This is the default settings that is used when you create a new
         * [org.codetome.zircon.terminal.swing.SwingTerminalComponent] without specifying any color
         * configuration. It will use classic VGA colors for the ANSI palette and bright colors on bold text.
         */
        fun getDefault() = newInstance(TerminalEmulatorPalette.STANDARD_VGA)

        /**
         * Creates a new color configuration based on a particular palette and with using brighter
         * colors on bold text.
         */
        fun newInstance(colorPalette: TerminalEmulatorPalette): TerminalColorConfiguration {
            return TerminalColorConfiguration(colorPalette)
        }
    }
}
