package org.hexworks.zircon.internal.behavior

import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.internal.data.LayerState

interface InternalLayerable : Layerable {

    val layerStates: Iterable<LayerState>
}