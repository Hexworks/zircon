package org.codetome.zircon.poc.drawableupgrade.position

interface Position {

    val x: Int
    val y: Int

    fun toAbsolutePosition(tileWidth: Int, tileHeight: Int): AbsolutePosition
}
