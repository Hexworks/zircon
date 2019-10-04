package org.hexworks.zircon.api.component.base

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.AlignmentStrategy
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.builder.ComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.BoxDecorationRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.resource.TilesetResource

@Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
abstract class BaseComponentBuilder<T : Component, U : ComponentBuilder<T, U>>()
    : ComponentBuilder<T, U>, Builder<T> {

    private val logger = LoggerFactory.getLogger(this::class)

    protected abstract val props: CommonComponentProperties<T>

    val position: Position
        get() = props.alignmentStrategy.calculatePosition(size)

    val componentStyleSet: ComponentStyleSet
        get() = props.componentStyleSet

    val tileset: TilesetResource
        get() = props.tileset

    val decorationRenderers: List<ComponentDecorationRenderer>
        get() = props.decorationRenderers

    val componentRenderer: ComponentRenderer<out T>
        get() = props.componentRenderer

    val size: Size
        get() = preferredSize.orElse(decorationRenderers
                .map { it.occupiedSize }
                .fold(contentSize, Size::plus))


    val title: String
        get() = decorationRenderers
                .filterIsInstance<BoxDecorationRenderer>()
                .firstOrNull()?.title ?: ""

    /**
     * The size which is set by the user. This takes precedence
     * over [contentSize] when set by the user.
     */
    protected var preferredSize = Maybe.empty<Size>()
        private set

    /**
     * The size which is needed to properly display current contents.
     * This field is ignored if the user explicitly sets [size].
     */
    protected var contentSize = Sizes.one()

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
        props.decorationRenderers = renderers.reversed()
        return this as U
    }

    override fun withComponentRenderer(componentRenderer: ComponentRenderer<T>): U {
        props.componentRenderer = componentRenderer
        return this as U
    }

    override fun withRendererFunction(fn: (TileGraphics, ComponentRenderContext<T>) -> Unit): U {
        return withComponentRenderer(object : ComponentRenderer<T> {
            override fun render(tileGraphics: TileGraphics, context: ComponentRenderContext<T>) {
                fn(tileGraphics, context)
            }
        })
    }

    override fun withSize(size: Size): U {
        preferredSize = Maybe.of(size)
        return this as U
    }

    protected fun copyProps() = props.copy()

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
}
