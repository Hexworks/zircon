package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.BoxDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.ShadowDecorationRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.resource.TilesetResource

@Suppress("UNCHECKED_CAST")
abstract class BaseComponentBuilder<T : Component, U : ComponentBuilder<T, U>>(
        private val props: CommonComponentProperties = CommonComponentProperties()) : ComponentBuilder<T, U> {

    override fun componentStyleSet() = props.componentStyleSet

    override fun componentStyleSet(componentStyleSet: ComponentStyleSet): U {
        props.componentStyleSet = componentStyleSet
        return this as U
    }

    override fun tileset() = props.tileset

    override fun tileset(tileset: TilesetResource): U {
        props.tileset = tileset
        return this as U
    }

    override fun position() = props.position

    override fun position(position: Position): U {
        props.position = position
        return this as U
    }

    override fun size() = props.size

    override fun size(size: Size): U {
        props.size = size
        return this as U
    }

    override fun boxType() = props.boxType

    override fun boxType(boxType: BoxType): U {
        props.boxType = boxType
        return this as U
    }

    override fun wrapWithBox() = props.wrapWithBox

    override fun wrapWithBox(wrapWithBox: Boolean): U {
        props.wrapWithBox = wrapWithBox
        return this as U
    }

    override fun wrapWithShadow() = props.wrapWithShadow

    override fun wrapWithShadow(wrapWithShadow: Boolean): U {
        props.wrapWithShadow = wrapWithShadow
        return this as U
    }

    override fun decorationRenderers() = props.decorationRenderers

    override fun decorationRenderers(vararg renderers: ComponentDecorationRenderer): U {
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
                decorationRenderers.add(BoxDecorationRenderer())
            }
            props.decorationRenderers = decorationRenderers.toList()
        }
    }

}
