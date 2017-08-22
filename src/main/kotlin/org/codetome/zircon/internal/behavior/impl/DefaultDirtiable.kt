package org.codetome.zircon.internal.behavior.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.internal.behavior.Dirtiable
import java.util.concurrent.LinkedBlockingQueue

class DefaultDirtiable : Dirtiable {

    val dirtyPositions = LinkedBlockingQueue<Position>()

    override fun isDirty() = dirtyPositions.isEmpty().not()

    override fun setPositionDirty(position: Position) {
        dirtyPositions.offer(position)
    }

    override fun drainDirtyPositions() = mutableSetOf<Position>().also { dirtyPositions.drainTo(it) }
}