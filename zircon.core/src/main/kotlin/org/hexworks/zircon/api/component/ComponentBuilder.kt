package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset

interface ComponentBuilder<T : Component, U: ComponentBuilder<T, U>> : Builder<T> {

    /**
     * Returns the [TilesetResource] the resulting [Component] will use.
     */
    fun tileset(): TilesetResource

    /**
     * Sets the [Tileset] to use with the resulting [Component].
     */
    fun tileset(tileset: TilesetResource): U

    /**
     * Returns the [Position] where the resulting [Component] will
     * be placed.
     */
    fun position(): Position

    /**
     * Sets the [Position] where the resulting [Component] will
     * be placed.
     */
    fun position(position: Position): U

    /**
     * Returns the [ComponentStyleSet] the resulting [Component] will use
     */
    fun componentStyleSet(): ComponentStyleSet

    /**
     * Sets the [ComponentStyleSet] the resulting [Component] will use
     */
    fun componentStyleSet(componentStyleSet: ComponentStyleSet): U
}
