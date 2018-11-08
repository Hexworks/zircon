package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.internal.behavior.Dirtiable
import org.hexworks.zircon.platform.factory.ThreadSafeQueueFactory

class DefaultDirtiable : Dirtiable {

    private val dirtyPositions = ThreadSafeQueueFactory.create<Position>()

    override fun isDirty() = dirtyPositions.isEmpty().not()

    override fun setPositionDirty(position: Position) {
        dirtyPositions.offer(position)
    }

    override fun drainDirtyPositions() = mutableSetOf<Position>().also { dirtyPositions.drainTo(it) }
}
