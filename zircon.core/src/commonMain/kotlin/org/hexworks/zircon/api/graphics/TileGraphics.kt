package org.hexworks.zircon.api.graphics

import org.hexworks.zircon.api.behavior.Copiable

/**
 * A [TileGraphics] enhances the [DrawSurface] interface with factory functions
 * for creating derived objects such as:
 * - [toDrawWindow]
 * - [toLayer]
 * - [toResized]
 * - [toTileImage]
 * and to be able to [createCopy] from this object.
 */
//! TODO: Delete this as we no longer need it
interface TileGraphics : Copiable<TileGraphics>, DrawSurface