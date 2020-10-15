package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.behavior.Movable
import org.hexworks.zircon.api.graphics.Layer

interface InternalLayer : Layer, Movable, Renderable
