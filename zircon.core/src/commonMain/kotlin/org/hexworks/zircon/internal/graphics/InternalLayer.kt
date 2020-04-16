package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.behavior.Movable
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.internal.data.LayerState

interface InternalLayer : Layer, InternalTileGraphics, Movable {

    override val state: LayerState
}
