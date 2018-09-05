package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.config.RuntimeConfig

@Suppress("UNCHECKED_CAST")
abstract class BaseComponentBuilder<T : Component, U : ComponentBuilder<T, U>> : ComponentBuilder<T, U> {

    private var position: Position = Position.defaultPosition()
    private var tileset: TilesetResource = RuntimeConfig.config.defaultTileset
    private var componentStyleSet: ComponentStyleSet = ComponentStyleSet.defaultStyleSet()

    override fun position() = position

    override fun position(position: Position): U {
        this.position = position
        return this as U
    }

    override fun tileset() = tileset

    override fun tileset(tileset: TilesetResource): U {
        this.tileset = tileset
        return this as U
    }

    override fun componentStyleSet() = componentStyleSet

    override fun componentStyleSet(componentStyleSet: ComponentStyleSet): U {
        this.componentStyleSet = componentStyleSet
        return this as U
    }
}
