package org.hexworks.zircon.api.component.base

import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.ComponentAlignments
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.alignment.AlignmentStrategy
import org.hexworks.zircon.api.component.builder.ComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.BoxDecorationRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.positionalAlignment
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.alignment.PositionalAlignmentStrategy
import kotlin.math.max

@Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
abstract class BaseComponentBuilder<T : Component, U : ComponentBuilder<T, U>>()
    : ComponentBuilder<T, U>, Builder<T> {

    val componentStyleSet: ComponentStyleSet
        get() = props.componentStyleSet

    val tileset: TilesetResource
        get() = props.tileset

    val position: Position
        get() = props.alignmentStrategy.calculateAlignment()

    val decorationRenderers: List<ComponentDecorationRenderer>
        get() = props.decorationRenderers

    val componentRenderer: ComponentRenderer<out T>
        get() = props.componentRenderer

    val size: Size
        get() {
            val calculatedSize = decorationRenderers
                    .map { it.occupiedSize }
                    .fold(DEFAULT_SIZE, Size::plus)
            return Sizes.create(
                    xLength = max(calculatedSize.width, preferredSize.width),
                    yLength = max(calculatedSize.height, preferredSize.height))
        }

    val title: String
        get() = decorationRenderers
                .filterIsInstance<BoxDecorationRenderer>()
                .firstOrNull()?.title ?: ""

    private val logger = LoggerFactory.getLogger(this::class)

    protected abstract val props: CommonComponentProperties<T>
    protected var preferredSize: Size = DEFAULT_SIZE

    override fun withComponentStyleSet(componentStyleSet: ComponentStyleSet): U {
        props.componentStyleSet = componentStyleSet
        return this as U
    }

    override fun withTileset(tileset: TilesetResource): U {
        props.tileset = tileset
        return this as U
    }

    override fun withAlignment(alignmentStrategy: AlignmentStrategy): U {
        props.alignmentStrategy = alignmentStrategy
        return this as U
    }

    override fun withDecorations(vararg renderers: ComponentDecorationRenderer): U {
        props.decorationRenderers = renderers.toList()
        return this as U
    }

    override fun withComponentRenderer(componentRenderer: ComponentRenderer<T>): U {
        props.componentRenderer = componentRenderer
        return this as U
    }

    override fun withSize(width: Int, height: Int) = withSize(Sizes.create(width, height))

    override fun withWidth(width: Int) = withSize(preferredSize.withWidth(width))

    override fun withHeight(height: Int) = withSize(preferredSize.withHeight(height))

    override fun withSize(size: Size): U {
        preferredSize = size
        return this as U
    }

    protected fun copyProps() = props.copy()

    @Deprecated(
            "use ComponentBuilder.withAlignment",
            replaceWith = ReplaceWith(
                    "withAlignment(positionalAlignment(position))",
                    "org.hexworks.zircon.api.extensions.positionalAlignment"))
    fun withPosition(position: Position) = withAlignment(PositionalAlignmentStrategy(position))

    @Deprecated(
            "use ComponentBuilder.withAlignment",
            replaceWith = ReplaceWith(
                    "withAlignment(positionalAlignment(x, y))",
                    "org.hexworks.zircon.api.extensions.positionalAlignment"))
    fun withPosition(x: Int, y: Int) = withAlignment(positionalAlignment(Positions.create(x, y)))

    @Deprecated(
            "use ComponentBuilder.withDecorations",
            replaceWith = ReplaceWith(
                    "withDecorations(box())",
                    "org.hexworks.zircon.api.extensions.box"))
    fun wrapWithBox(wrapWithBox: Boolean = true) = if (wrapWithBox) {
        throw UnsupportedOperationException("use withDecorations() instead")
    } else this as U

    @Deprecated(
            "use ComponentBuilder.withDecorations",
            replaceWith = ReplaceWith(
                    "withDecorations(box(boxType=boxType))",
                    "org.hexworks.zircon.api.extensions.box"))
    fun withBoxType(boxType: BoxType): U {
        throw UnsupportedOperationException("use withDecorations() instead")
    }

    @Deprecated(
            "use ComponentBuilder.withDecorations",
            replaceWith = ReplaceWith(
                    "withDecorations(shadow())",
                    "org.hexworks.zircon.api.extensions.shadow"))
    fun wrapWithShadow(wrapWithShadow: Boolean = true) = if (wrapWithShadow) {
        throw UnsupportedOperationException("use withDecorations() instead")
    } else this as U

    @Deprecated(
            "use ComponentBuilder.withDecorations",
            replaceWith = ReplaceWith(
                    "withDecorations(box(title=title))",
                    "org.hexworks.zircon.api.extensions.box"))
    fun withTitle(@Suppress("UNUSED_PARAMETER") title: String): U {
        throw UnsupportedOperationException("use withDecorations() instead")
    }

    @Deprecated(
            "use ComponentBuilder.withAlignment",
            replaceWith = ReplaceWith(
                    "withAlignment(alignmentWithin(tileGrid, alignmentType))",
                    "org.hexworks.zircon.api.extensions.alignmentWithin"))
    fun withAlignmentWithin(tileGrid: TileGrid, alignmentType: ComponentAlignment): U {
        return withAlignment(ComponentAlignments.alignmentWithin(tileGrid, alignmentType))
    }

    @Deprecated(
            "use ComponentBuilder.withAlignment",
            replaceWith = ReplaceWith(
                    "withAlignment(alignmentWithin(container, alignmentType))",
                    "org.hexworks.zircon.api.extensions.alignmentWithin"))
    fun withAlignmentWithin(container: Container, alignmentType: ComponentAlignment): U {
        return withAlignment(ComponentAlignments.alignmentWithin(container, alignmentType))
    }

    @Deprecated(
            "use ComponentBuilder.withAlignment",
            replaceWith = ReplaceWith(
                    "withAlignment(alignmentAround(component, alignmentType))",
                    "org.hexworks.zircon.api.extensions.alignmentAround"))
    fun withAlignmentAround(component: Component, alignmentType: ComponentAlignment): U {
        return withAlignment(ComponentAlignments.alignmentAround(component, alignmentType))
    }

    companion object {

        private val DEFAULT_SIZE = Sizes.one()
    }

}
