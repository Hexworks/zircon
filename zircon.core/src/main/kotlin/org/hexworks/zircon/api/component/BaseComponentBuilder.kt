package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.BoxDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.ShadowDecorationRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Maybe

@Suppress("UNCHECKED_CAST")
abstract class BaseComponentBuilder<T : Component, U : ComponentBuilder<T, U>>(
        private val props: CommonComponentProperties) : ComponentBuilder<T, U> {

    override val position: Position
        get() = props.position

    override val size: Size
        get() = props.size

    override val componentStyleSet: ComponentStyleSet
        get() = props.componentStyleSet

    override fun title() = props.title

    override fun withTitle(title: String): U {
        props.title = Maybe.of(title)
        return this as U
    }

    override fun withComponentStyleSet(componentStyleSet: ComponentStyleSet): U {
        props.componentStyleSet = componentStyleSet
        return this as U
    }

    override fun tileset() = props.tileset

    override fun withTileset(tileset: TilesetResource): U {
        props.tileset = tileset
        return this as U
    }

    override fun withPosition(position: Position): U {
        props.position = position
        return this as U
    }

    override fun withSize(size: Size): U {
        props.size = size
        return this as U
    }

    override fun boxType() = props.boxType

    override fun withBoxType(boxType: BoxType): U {
        props.boxType = boxType
        return this as U
    }

    override fun isWrappedWithBox() = props.wrapWithBox

    override fun wrapWithBox(wrapWithBox: Boolean): U {
        props.wrapWithBox = wrapWithBox
        return this as U
    }

    override fun isWrappedWithShadow() = props.wrapWithShadow

    override fun wrapWithShadow(wrapWithShadow: Boolean): U {
        props.wrapWithShadow = wrapWithShadow
        return this as U
    }

    override fun decorationRenderers() = props.decorationRenderers

    override fun withDecorationRenderers(vararg renderers: ComponentDecorationRenderer): U {
        props.decorationRenderers = renderers.toList()
        return this as U
    }

    protected fun copyProps() = props.copy()

    protected fun fillMissingValues() {
        if (props.decorationRenderers.isEmpty()) {
            val decorationRenderers = mutableListOf<ComponentDecorationRenderer>()
            if (props.wrapWithShadow) {
                decorationRenderers.add(ShadowDecorationRenderer())
            }
            if (props.wrapWithBox) {
                decorationRenderers.add(BoxDecorationRenderer(
                        boxType = boxType(),
                        title = title()))
            }
            props.decorationRenderers = decorationRenderers.toList()
        }
    }

}
