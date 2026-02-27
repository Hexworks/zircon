package org.hexworks.zircon.internal.data

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size

data class DefaultBoundable(
    override val position: Position,
    override val size: Size
) : Boundable