package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.util.Maybe

interface ComponentBuilder<T : Component, U : ComponentBuilder<T, U>> : Builder<T> {

    fun title(): Maybe<String>

    fun title(title: String): U

    /**
     * Returns the [ComponentStyleSet] the resulting [Component] will use
     */
    fun componentStyleSet(): ComponentStyleSet

    /**
     * Sets the [ComponentStyleSet] the resulting [Component] will use
     */
    fun componentStyleSet(componentStyleSet: ComponentStyleSet): U

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
     * Returns the [Size] of the resulting [Component].
     */
    fun size(): Size

    /**
     * Sets the [Size] of the resulting [Component].
     */
    fun size(size: Size): U

    /**
     * Returns the box type which can be used for wrapping.
     */
    fun boxType(): BoxType

    /**
     * Sets the box type which can be used for wrapping.
     */
    fun boxType(boxType: BoxType): U

    /**
     * Tells whether the resulting [Component] will be
     * wrapped in a box.
     */
    fun wrapWithBox(): Boolean

    /**
     * Sets whether the resulting [Component] will be
     * wrapped in a box.
     */
    fun wrapWithBox(wrapWithBox: Boolean): U

    /**
     * Tells whether the resulting [Component] will be
     * wrapped with a shadow.
     */
    fun wrapWithShadow(): Boolean

    /**
     * Sets whether the resulting [Component] will be
     * wrapped with a shadow.
     */
    fun wrapWithShadow(wrapWithShadow: Boolean): U

    /**
     * Returns the [ComponentDecorationRenderer]s (if any).
     */
    fun decorationRenderers(): List<ComponentDecorationRenderer>

    /**
     * Sets the [ComponentDecorationRenderer]s for the resulting [Component].
     */
    fun decorationRenderers(vararg renderers: ComponentDecorationRenderer): U
}
