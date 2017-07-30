package org.codetome.zircon.terminal.config

import org.codetome.zircon.TextColor
import org.codetome.zircon.TextColor.ANSI.*
import java.awt.Color

/**
 * This class specifies the palette of colors the terminal will use for the normally available 8 + 1 ANSI colors but
 * also their bright versions with are normally enabled through bold mode. There are several palettes available, all
 * based on popular terminal emulators. All colors are defined in the AWT format.
 */
class TerminalDisplayPalette(
        private val defaultColor: Color,
        private val defaultBrightColor: Color,
        private val defaultBackgroundColor: Color,
        private val normalBlack: Color,
        private val brightBlack: Color,
        private val normalRed: Color,
        private val brightRed: Color,
        private val normalGreen: Color,
        private val brightGreen: Color,
        private val normalYellow: Color,
        private val brightYellow: Color,
        private val normalBlue: Color,
        private val brightBlue: Color,
        private val normalMagenta: Color,
        private val brightMagenta: Color,
        private val normalCyan: Color,
        private val brightCyan: Color,
        private val normalWhite: Color,
        private val brightWhite: Color) {

    /**
     * Returns the color from this palette given an ANSI color and two hints for if we are looking for a background
     * color and if we want to use the bright version.
     */
    fun get(color: TextColor.ANSI, isForeground: Boolean, useBrightTones: Boolean): Color {
        return if (useBrightTones) {
            when (color) {
                BLACK -> brightBlack
                BLUE -> brightBlue
                CYAN -> brightCyan
                DEFAULT -> if (isForeground) defaultBrightColor else defaultBackgroundColor
                GREEN -> brightGreen
                MAGENTA -> brightMagenta
                RED -> brightRed
                WHITE -> brightWhite
                YELLOW -> brightYellow
            }
        } else {
            when (color) {
                BLACK -> normalBlack
                BLUE -> normalBlue
                CYAN -> normalCyan
                DEFAULT -> if (isForeground) defaultColor else defaultBackgroundColor
                GREEN -> normalGreen
                MAGENTA -> normalMagenta
                RED -> normalRed
                WHITE -> normalWhite
                YELLOW -> normalYellow
            }
        }
    }

    companion object {
        /**
         * Values taken from gnome-terminal on Ubuntu
         */
        val GNOME_TERMINAL = TerminalDisplayPalette(
                Color(211, 215, 207),
                Color(238, 238, 236),
                Color(46, 52, 54),
                Color(46, 52, 54),
                Color(85, 87, 83),
                Color(204, 0, 0),
                Color(239, 41, 41),
                Color(78, 154, 6),
                Color(138, 226, 52),
                Color(196, 160, 0),
                Color(252, 233, 79),
                Color(52, 101, 164),
                Color(114, 159, 207),
                Color(117, 80, 123),
                Color(173, 127, 168),
                Color(6, 152, 154),
                Color(52, 226, 226),
                Color(211, 215, 207),
                Color(238, 238, 236))

        /**
         * Values taken from [Wikipedia](http://en.wikipedia.org/wiki/ANSI_escape_code), these are supposed to be the standard VGA palette.
         */
        val STANDARD_VGA = TerminalDisplayPalette(
                Color(170, 170, 170),
                Color(255, 255, 255),
                Color(0, 0, 0),
                Color(0, 0, 0),
                Color(85, 85, 85),
                Color(170, 0, 0),
                Color(255, 85, 85),
                Color(0, 170, 0),
                Color(85, 255, 85),
                Color(170, 85, 0),
                Color(255, 255, 85),
                Color(0, 0, 170),
                Color(85, 85, 255),
                Color(170, 0, 170),
                Color(255, 85, 255),
                Color(0, 170, 170),
                Color(85, 255, 255),
                Color(170, 170, 170),
                Color(255, 255, 255))

        /**
         * Values taken from [Wikipedia](http://en.wikipedia.org/wiki/ANSI_escape_code), these are supposed to be what Windows XP cmd is using.
         */
        val WINDOWS_XP_COMMAND_PROMPT = TerminalDisplayPalette(
                Color(192, 192, 192),
                Color(255, 255, 255),
                Color(0, 0, 0),
                Color(0, 0, 0),
                Color(128, 128, 128),
                Color(128, 0, 0),
                Color(255, 0, 0),
                Color(0, 128, 0),
                Color(0, 255, 0),
                Color(128, 128, 0),
                Color(255, 255, 0),
                Color(0, 0, 128),
                Color(0, 0, 255),
                Color(128, 0, 128),
                Color(255, 0, 255),
                Color(0, 128, 128),
                Color(0, 255, 255),
                Color(192, 192, 192),
                Color(255, 255, 255))

        /**
         * Values taken from [Wikipedia](http://en.wikipedia.org/wiki/ANSI_escape_code), these are supposed to be what terminal.app on MacOSX is using.
         */
        val MAC_OS_X_TERMINAL_APP = TerminalDisplayPalette(
                Color(203, 204, 205),
                Color(233, 235, 235),
                Color(0, 0, 0),
                Color(0, 0, 0),
                Color(129, 131, 131),
                Color(194, 54, 33),
                Color(252, 57, 31),
                Color(37, 188, 36),
                Color(49, 231, 34),
                Color(173, 173, 39),
                Color(234, 236, 35),
                Color(73, 46, 225),
                Color(88, 51, 255),
                Color(211, 56, 211),
                Color(249, 53, 248),
                Color(51, 187, 200),
                Color(20, 240, 240),
                Color(203, 204, 205),
                Color(233, 235, 235))

        /**
         * Values taken from [Wikipedia](http://en.wikipedia.org/wiki/ANSI_escape_code), these are supposed to be what putty is using.
         */
        val PUTTY = TerminalDisplayPalette(
                Color(187, 187, 187),
                Color(255, 255, 255),
                Color(0, 0, 0),
                Color(0, 0, 0),
                Color(85, 85, 85),
                Color(187, 0, 0),
                Color(255, 85, 85),
                Color(0, 187, 0),
                Color(85, 255, 85),
                Color(187, 187, 0),
                Color(255, 255, 85),
                Color(0, 0, 187),
                Color(85, 85, 255),
                Color(187, 0, 187),
                Color(255, 85, 255),
                Color(0, 187, 187),
                Color(85, 255, 255),
                Color(187, 187, 187),
                Color(255, 255, 255))

        /**
         * Values taken from [Wikipedia](http://en.wikipedia.org/wiki/ANSI_escape_code), these are supposed to be what xterm is using.
         */
        val XTERM = TerminalDisplayPalette(
                Color(229, 229, 229),
                Color(255, 255, 255),
                Color(0, 0, 0),
                Color(0, 0, 0),
                Color(127, 127, 127),
                Color(205, 0, 0),
                Color(255, 0, 0),
                Color(0, 205, 0),
                Color(0, 255, 0),
                Color(205, 205, 0),
                Color(255, 255, 0),
                Color(0, 0, 238),
                Color(92, 92, 255),
                Color(205, 0, 205),
                Color(255, 0, 255),
                Color(0, 205, 205),
                Color(0, 255, 255),
                Color(229, 229, 229),
                Color(255, 255, 255))

        /**
         * Default colors the SwingTerminalComponent is using if you dont specify anything
         */
        val DEFAULT_PALETTE = GNOME_TERMINAL
    }
}
