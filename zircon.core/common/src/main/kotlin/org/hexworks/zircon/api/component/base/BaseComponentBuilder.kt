package org.hexworks.zircon.api.component.base

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.ComponentBuilder
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.BoxDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.ShadowDecorationRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.TilesetResource

@Suppress("UNCHECKED_CAST")
abstract class BaseComponentBuilder<T : Component, U : ComponentBuilder<T, U>>(
        private val props: CommonComponentProperties<T>) : ComponentBuilder<T, U> {

    val position: Position
        get() = positionFn(Rect.create(size = size))

    val size: Size
        get() = props.size

    val componentStyleSet: ComponentStyleSet
        get() = props.componentStyleSet

    val title
        get() = props.title

    val tileset
        get() = props.tileset

    val boxType
        get() = props.boxType

    val isWrappedWithBox
        get() = props.wrapWithBox

    val isWrappedWithShadow
        get() = props.wrapWithShadow

    val decorationRenderers: List<ComponentDecorationRenderer>
        get() = props.decorationRenderers

    val componentRenderer: ComponentRenderer<T>
        get() = props.componentRenderer as ComponentRenderer<T>

    private val logger = LoggerFactory.getLogger(this::class)

    private var positionFn: (currentRect: Rect) -> Position = {
        props.position
    }

    override fun withTitle(title: String): U {
        props.title = title
        if (isWrappedWithBox.not()) {
            logger.warn("Trying to set the title without wrapping the component in a box. The title won't be displayed.")
        }
        return this as U
    }

    override fun withComponentStyleSet(componentStyleSet: ComponentStyleSet): U {
        props.componentStyleSet = componentStyleSet
        return this as U
    }

    override fun withTileset(tileset: TilesetResource): U {
        props.tileset = tileset
        return this as U
    }

    override fun withPosition(x: Int, y: Int): U {
        return withPosition(Positions.create(x, y))
    }

    override fun withPosition(position: Position): U {
        positionFn = {
            position
        }
        return this as U
    }

    override fun withSize(width: Int, height: Int): U {
        return withSize(Sizes.create(width, height))
    }

    override fun withSize(size: Size): U {
        props.size = size
        return this as U
    }

    override fun withBoxType(boxType: BoxType): U {
        props.boxType = boxType
        return this as U
    }

    override fun wrapWithBox(wrapWithBox: Boolean): U {
        props.wrapWithBox = wrapWithBox
        return this as U
    }

    override fun wrapWithShadow(wrapWithShadow: Boolean): U {
        props.wrapWithShadow = wrapWithShadow
        return this as U
    }

    override fun withDecorationRenderers(vararg renderers: ComponentDecorationRenderer): U {
        props.decorationRenderers = renderers.toList()
        return this as U
    }

    override fun withComponentRenderer(componentRenderer: ComponentRenderer<T>): U {
        props.componentRenderer = componentRenderer
        return this as U
    }

    override fun withAlignmentWithin(container: Container, componentAlignment: ComponentAlignment): U {
        positionFn = { currentRect ->
            componentAlignment.alignWithin(
                    target = Rect.create(size = container.contentSize),
                    subject = currentRect)
        }
        return this as U
    }

    override fun withAlignmentAround(component: Component, componentAlignment: ComponentAlignment): U {
        positionFn = { currentRect ->
            componentAlignment.alignAround(
                    target = component.rect,
                    subject = currentRect)
        }
        return this as U
    }

    override fun withAlignmentWithin(tileGrid: TileGrid, componentAlignment: ComponentAlignment): U {
        positionFn = { currentRect ->
            componentAlignment.alignWithin(
                    target = Rect.create(size = tileGrid.size),
                    subject = currentRect)
        }
        return this as U
    }

    protected fun copyProps() = props.copy()

    protected fun fixPosition(size: Size): Position {
        return positionFn(Rect.create(size = size))
    }

    protected fun fillMissingValues() {
        if (props.decorationRenderers.isEmpty()) {
            val decorationRenderers = mutableListOf<ComponentDecorationRenderer>()
            if (props.wrapWithShadow) {
                decorationRenderers.add(ShadowDecorationRenderer())
            }
            if (props.wrapWithBox) {
                decorationRenderers.add(BoxDecorationRenderer(
                        boxType = boxType,
                        titleProperty = createPropertyFrom(title)))
            }
            props.decorationRenderers = decorationRenderers.toList()
        }
    }

}
