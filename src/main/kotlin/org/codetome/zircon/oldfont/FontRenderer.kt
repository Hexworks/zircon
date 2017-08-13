package org.codetome.zircon.oldfont

import org.codetome.zircon.TextCharacter


/**
 * Renders a monospace oldfont type. There can be all kinds of fonts from the built-in
 * [java.awt.Font] to custom tileset fonts. For our purposes the most important
 * things to know about a monospace oldfont to render it is its width and height, otherwise we can't
 * use it in a terminal display environment. The other mandatory requirement for a
 * oldfont is to be monospaced (for the same reason).
 */
abstract class FontRenderer<in S>(val width: Int,
                                  val height: Int) {

    /**
     * Renders a `textCharacter` on the given `surface` at `x`:`y` coordinates.
     */
    abstract fun renderCharacter(textCharacter: TextCharacter, surface: S, x: Int, y: Int)

    open fun  isAntiAliased(): Boolean = false
}