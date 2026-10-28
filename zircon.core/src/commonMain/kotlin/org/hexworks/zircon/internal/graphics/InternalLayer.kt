package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.behavior.Movable
import org.hexworks.zircon.api.graphics.Layer

/**
 * Exposes the [Movable] and [Renderable] behavior of a [Layer] to Zircon internals.
 */
public interface InternalLayer : Layer, Movable, Renderable
