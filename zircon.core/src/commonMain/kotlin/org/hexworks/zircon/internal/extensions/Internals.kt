package org.hexworks.zircon.internal.extensions

import org.hexworks.zircon.api.animation.AnimationFrame
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.animation.InternalAnimationFrame
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalContainer
import org.hexworks.zircon.internal.game.InternalGameArea
import org.hexworks.zircon.internal.graphics.InternalLayer
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.screen.InternalScreen

// Every public implementation of these API types is one of the internal interfaces below,
// so the casts are safe. Keeping them `internal` keeps internal types out of the public API.

internal fun TileGrid.asInternal(): InternalTileGrid = this as InternalTileGrid

internal fun Screen.asInternal(): InternalScreen = this as InternalScreen

internal fun Layer.asInternal(): InternalLayer = this as InternalLayer

internal fun AnimationFrame.asInternal(): InternalAnimationFrame = this as InternalAnimationFrame

@Suppress("UNCHECKED_CAST")
internal fun <T : Tile, B : Block<T>> GameArea<T, B>.asInternal(): InternalGameArea<T, B> = this as InternalGameArea<T, B>

internal fun Component.asInternalComponent(): InternalComponent = this as InternalComponent

internal fun Container.asInternalComponent(): InternalContainer = this as InternalContainer
