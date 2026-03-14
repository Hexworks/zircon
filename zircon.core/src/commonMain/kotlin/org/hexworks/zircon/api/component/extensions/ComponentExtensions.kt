package org.hexworks.zircon.api.component.extensions

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position

val Component.absolutePosition: Position
    get() = position