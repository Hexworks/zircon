package org.hexworks.zircon.api.component.builder

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.alignment.AlignmentStrategy
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset

interface ComponentBuilder<T : Component, U : ComponentBuilder<T, U>> {

    /**
     * Sets the [ComponentStyleSet] the [Component] will use.
     */
    fun withComponentStyleSet(componentStyleSet: ComponentStyleSet): U

    /**
     * Sets the [Tileset] to use for the [Component].
     */
    fun withTileset(tileset: TilesetResource): U

    /**
     * Sets the [AlignmentStrategy] to use for the [Component].
     */
    fun withAlignment(alignmentStrategy: AlignmentStrategy): U

    /**
     * Sets the [ComponentDecorationRenderer]s for the resulting [Component].
     * The component will be decorated with the given decorators in the given
     * order.
     */
    fun withDecorations(vararg renderers: ComponentDecorationRenderer): U

    /**
     * Sets the [ComponentRenderer] for the resulting [Component].
     */
    fun withComponentRenderer(componentRenderer: ComponentRenderer<T>): U

    /**
     * Sets the [Size] of the resulting [Component].
     */
    fun withSize(width: Int, height: Int): U

    /**
     * Sets the [Size] of the resulting [Component].
     */
    fun withSize(size: Size): U

    /**
     * Sets the [width] of the resulting [Component].
     */
    fun withWidth(width: Int): U

    /**
     * Sets the [height] of the resulting [Component].
     */
    fun withHeight(height: Int): U

}
