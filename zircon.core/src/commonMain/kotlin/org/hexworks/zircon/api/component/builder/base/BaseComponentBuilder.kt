package org.hexworks.zircon.api.component.builder.base

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.AlignmentStrategy
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.builder.ComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.renderer.decoration.BoxDecorationRenderer

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
    private var preferredSize = Maybe.empty<Size>()

    /**
     * The size which is needed to properly display current contents.
     * This field is ignored if the user explicitly sets [size].
     */
    protected var contentSize = Size.one()

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
}
