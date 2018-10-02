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

    /**
     * Returns the [Position] where the resulting [Component] will
     * be placed.
     */
    val position: Position

    /**
     * Returns the [Size] of the resulting [Component].
     */
    val size: Size

    fun title(): Maybe<String>

    fun withTitle(title: String): U

    /**
     * Returns the [ComponentStyleSet] the resulting [Component] will use
     */
    fun componentStyleSet(): ComponentStyleSet

    /**
     * Sets the [ComponentStyleSet] the resulting [Component] will use
     */
    fun withComponentStyleSet(componentStyleSet: ComponentStyleSet): U

    /**
     * Returns the [TilesetResource] the resulting [Component] will use.
     */
    fun tileset(): TilesetResource

    /**
     * Sets the [Tileset] to use with the resulting [Component].
     */
    fun withTileset(tileset: TilesetResource): U

    /**
     * Sets the [Position] where the resulting [Component] will
     * be placed.
     */
    fun withPosition(position: Position): U

    /**
     * Sets the [Size] of the resulting [Component].
     */
    fun withSize(size: Size): U

    /**
     * Returns the box type which can be used for wrapping.
     */
    fun boxType(): BoxType

    /**
     * Sets the box type which can be used for wrapping.
     */
    fun withBoxType(boxType: BoxType): U

    /**
     * Tells whether the resulting [Component] will be
     * wrapped in a box.
     */
    fun isWrappedWithBox(): Boolean

    /**
     * Sets whether the resulting [Component] will be
     * wrapped in a box.
     */
    fun wrapWithBox(wrapWithBox: Boolean = true): U

    /**
     * Tells whether the resulting [Component] will be
     * wrapped with a shadow.
     */
    fun isWrappedWithShadow(): Boolean

    /**
     * Sets whether the resulting [Component] will be
     * wrapped with a shadow.
     */
    fun wrapWithShadow(wrapWithShadow: Boolean = true): U

    /**
     * Returns the [ComponentDecorationRenderer]s (if any).
     */
    fun decorationRenderers(): List<ComponentDecorationRenderer>

    /**
     * Sets the [ComponentDecorationRenderer]s for the resulting [Component].
     */
    fun withDecorationRenderers(vararg renderers: ComponentDecorationRenderer): U
}
