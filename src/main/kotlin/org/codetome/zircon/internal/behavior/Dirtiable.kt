package org.codetome.zircon.internal.behavior

import org.codetome.zircon.api.Position

/**
 * Represents **any** object, component or data structure which can change
 * over time thus become **dirty**. This interface exposes means to query
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