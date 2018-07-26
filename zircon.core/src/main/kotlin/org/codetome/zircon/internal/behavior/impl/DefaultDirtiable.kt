package org.codetome.zircon.internal.behavior.impl

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.internal.behavior.Dirtiable
import org.codetome.zircon.platform.factory.ThreadSafeQueueFactory

class DefaultDirtiable : Dirtiable {

    private val dirtyPositions = ThreadSafeQueueFactory.create<Position>()

    override fun isDirty() = dirtyPositions.isEmpty().not()

    override fun setPositionDirty(position: Position) {
        dirtyPositions.offer(position)
    }

    override fun drainDirtyPositions() = mutableSetOf<Position>().also { dirtyPositions.drainTo(it) }
}
