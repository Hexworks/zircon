package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset

interface ComponentBuilder<T : Component, U : ComponentBuilder<T, U>> : Builder<T> {

    val position: Position
    val size: Size
    val componentStyleSet: ComponentStyleSet
    val title: String
    val tileset: TilesetResource
    val boxType: BoxType
    /**
     * Whether the resulting [Component] will be
     * wrapped in a box.
     */
    val wrappedWithBox: Boolean
    /**
     * Whether the resulting [Component] will be
     * wrapped with a shadow.
     */
    val wrappedWithShadow: Boolean

    /**
     * The renderers for the component decorations (if any).
     */
    val decorationRenderers: List<ComponentDecorationRenderer>

    /**
     * The renderer of the [Component].
     */
    val componentRenderer: ComponentRenderer<T>

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
     * Sets the [ComponentStyleSet] the resulting [Component] will use
     */
    fun withComponentStyleSet(componentStyleSet: ComponentStyleSet): U

    /**
     * Sets the title the resulting [Component] will use
     */
    fun withTitle(title: String): U

    /**
     * Sets the [Tileset] to use with the resulting [Component].
     */
    fun withTileset(tileset: TilesetResource): U

    /**
     * Sets the box type which can be used for wrapping.
     */
    fun withBoxType(boxType: BoxType): U

    /**
     * Sets the [ComponentDecorationRenderer]s for the resulting [Component].
     */
    fun withDecorationRenderers(vararg renderers: ComponentDecorationRenderer): U

    /**
     * Sets the [ComponentRenderer] for the resulting [Component].
     */
    fun withComponentRenderer(componentRenderer: ComponentRenderer<T>): U

    /**
     * Sets whether the resulting [Component] will be
     * wrapped in a box.
     */
    fun wrapWithBox(wrapWithBox: Boolean = true): U

    /**
     * Sets whether the resulting [Component] will be
     * wrapped with a shadow.
     */
    fun wrapWithShadow(wrapWithShadow: Boolean = true): U
}
