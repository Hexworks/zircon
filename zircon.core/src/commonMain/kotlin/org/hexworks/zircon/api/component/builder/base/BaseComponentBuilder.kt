package org.hexworks.zircon.api.component.builder.base

import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.ComponentAlignments
import org.hexworks.zircon.api.component.AlignmentStrategy
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.builder.ComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentPostProcessor
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.decoration.BoxDecorationRenderer
import kotlin.jvm.JvmSynthetic

/**
 * This class can be used as a base class for creating [ComponentBuilder]s.
 * If your component has text use [ComponentWithTextBuilder] instead.
 */
@Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
abstract class BaseComponentBuilder<T : Component, U : ComponentBuilder<T, U>>(
        initialRenderer: ComponentRenderer<out T>
) : ComponentBuilder<T, U> {

    override var name: String = ""

    override var position: Position
        get() = props.alignmentStrategy.calculatePosition(size)
        set(value) {
            props.alignmentStrategy = ComponentAlignments.positionalAlignment(value)
        }

    override var alignment: AlignmentStrategy
        get() = props.alignmentStrategy
        set(value) {
            props.alignmentStrategy = value
        }

    override var preferredSize: Size = Size.unknown()
        set(value) {
            if (preferredContentSize.isNotUnknown) {
                preferredContentSize = Size.unknown()
            }
            field = value
        }

    override var preferredContentSize: Size = Size.unknown()
        set(value) {
            if (preferredSize.isNotUnknown) {
                preferredSize = Size.unknown()
            }
            field = value
        }

    override val contentSize: Size
        get() = if (preferredSize.isUnknown) {
            if (preferredContentSize.isUnknown) {
                Size.one()
            } else preferredContentSize
        } else preferredSize - decorations.occupiedSize

    override val size: Size
        get() = if (preferredSize.isUnknown) {
            if (preferredContentSize.isUnknown) {
                contentSize + decorations.occupiedSize
            } else preferredContentSize + decorations.occupiedSize
        } else preferredSize

    override var colorTheme: ColorTheme?
        get() = props.colorTheme
        set(value) {
            props.colorTheme = value
        }

    override var decoration: ComponentDecorationRenderer? = null
        set(value) {
            value?.let {
                decorations = listOf(value)
            }
        }

    override var decorations: List<ComponentDecorationRenderer>
        get() = props.decorationRenderers
        set(value) {
            props.decorationRenderers = value.reversed()
        }

    override val title: String
        get() = this.decorations
                .filterIsInstance<BoxDecorationRenderer>()
                .firstOrNull()?.title ?: ""

    override var componentStyleSet: ComponentStyleSet
        get() = props.componentStyleSet
        set(value) {
            props.componentStyleSet = value
        }

    override var tileset: TilesetResource
        get() = props.tileset
        set(value) {
            props.tileset = value
        }

    override var updateOnAttach: Boolean
        get() = props.updateOnAttach
        set(value) {
            props.updateOnAttach = value
        }

    override var componentRenderer: ComponentRenderer<out T>
        get() = props.componentRenderer
        set(value) {
            props.componentRenderer = value
        }

    override var renderFunction: (TileGraphics, ComponentRenderContext<T>) -> Unit
        get() = { g, c -> componentRenderer.render(g, c as ComponentRenderContext<Nothing>) }
        set(value) {
            componentRenderer = object : ComponentRenderer<T> {
                override fun render(tileGraphics: TileGraphics, context: ComponentRenderContext<T>) {
                    value(tileGraphics, context)
                }
            }
        }

    private val logger = LoggerFactory.getLogger(this::class)

    protected var postProcessors: List<ComponentPostProcessor<T>> = listOf()

    protected var props: CommonComponentProperties<T> = CommonComponentProperties(
            componentRenderer = initialRenderer
    )
        private set

    protected fun createRenderingStrategy() = DefaultComponentRenderingStrategy(
            decorationRenderers = decorations,
            componentRenderer = componentRenderer as ComponentRenderer<T>,
            componentPostProcessors = postProcessors
    )

    protected open fun createMetadata() = ComponentMetadata(
            relativePosition = position,
            size = size,
            tileset = tileset,
            componentStyleSet = componentStyleSet,
            theme = colorTheme,
            updateOnAttach = updateOnAttach,
            name = name
    )

    protected fun fixContentSizeFor(length: Int) {
        if (length doesntFitWithin contentSize) {
            this.preferredContentSize = Size.create(length, 1)
        }
    }

    protected infix fun Int.doesntFitWithin(size: Size) = this > size.width * size.height

    protected infix fun Size.fitsWithin(other: Size) = other.toRect().containsBoundable(toRect())

    protected val List<ComponentDecorationRenderer>.occupiedSize
        get() = this.map { it.occupiedSize }.fold(Size.zero(), Size::plus)

    @JvmSynthetic
    internal fun withProps(props: CommonComponentProperties<T>): U {
        this.props = props
        return this as U
    }
}
