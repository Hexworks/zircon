package org.hexworks.zircon.internal.behavior

import org.hexworks.zircon.api.data.Position

/**
 * Represents **any** object, component or data structure which holds
 * data about a [Position], can change over time thus can become **dirty**.
 * This interface exposes means to query
 * and manipulate the dirty parts.
 */
interface Dirtiable {

    /**
     * Tells whether this [Dirtiable] contains any dirty [Position]s.
     */
    fun isDirty(): Boolean

    /**
     * Sets a [Position] as dirty.
     */
    fun setPositionDirty(position: Position)

    /**
     * Empties the set of dirty positions in this [Dirtiable] and returns them.
     */
    fun drainDirtyPositions(): Set<Position>
}
