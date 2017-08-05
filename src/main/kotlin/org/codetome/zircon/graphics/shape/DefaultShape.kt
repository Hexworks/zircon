package org.codetome.zircon.graphics.shape

import org.codetome.zircon.Position

class DefaultShape(private val positions: Set<Position> = setOf())
    : Shape, Collection<Position> by positions {

    override fun getPositions() = positions
}