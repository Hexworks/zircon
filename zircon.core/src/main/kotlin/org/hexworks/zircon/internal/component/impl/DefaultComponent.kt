package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.behavior.Subscription
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.kotlin.addObserver
import org.hexworks.zircon.api.kotlin.map
import org.hexworks.zircon.api.listener.InputListener
import org.hexworks.zircon.api.util.Identifier
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.behavior.Observable
import org.hexworks.zircon.internal.behavior.impl.DefaultObservable
import org.hexworks.zircon.internal.component.InternalComponent

@Suppress("UNCHECKED_CAST")
abstract class DefaultComponent(
        componentMetadata: ComponentMetadata,
        override val graphics: TileGraphics = TileGraphicsBuilder
                .newBuilder()
                .withTileset(componentMetadata.tileset)
                .withSize(componentMetadata.size)
                .build(),
        private val renderer: ComponentRenderingStrategy<out Component>,
        private val layer: Layer = LayerBuilder.newBuilder()
                .withOffset(componentMetadata.position)
                .withTileGraphics(graphics)
                .build())
    : InternalComponent,
        Layer by layer {

    // identifiable
    final override val id = Identifier.randomIdentifier()

    // component
    final override val contentPosition: Position
        get() = renderer.calculateContentPosition()

    final override val absolutePosition: Position
        get() = position + parent.map { it.absolutePosition }.orElse(Position.zero())

    final override val contentSize: Size
        get() = renderer.calculateContentSize(size)

    final override var componentStyleSet = componentMetadata.componentStyleSet
        set(value) {
            field = value
            render()
        }

    private val observable: Observable<Input> = DefaultObservable()
    private var parent = Maybe.empty<Container>()

    final override fun inputEmitted(input: Input) {
        observable.notifyObservers(input)
    }

    final override fun onInput(listener: InputListener): Subscription {
        return observable.addObserver { input ->
            listener.inputEmitted(input)
        }
    }

    override fun mouseEntered(action: MouseAction) {
        componentStyleSet.applyMouseOverStyle()
        render()
    }

    override fun mouseExited(action: MouseAction) {
        componentStyleSet.reset()
        render()
    }

    override fun mousePressed(action: MouseAction) {
        componentStyleSet.applyActiveStyle()
        render()
    }

    override fun mouseReleased(action: MouseAction) {
        componentStyleSet.applyMouseOverStyle()
        render()
    }

    final override fun fetchParent() = parent

    final override fun attachTo(parent: Container) {
        this.parent.map {
            it.removeComponent(this)
        }
        this.parent = Maybe.of(parent)
    }

    override fun fetchComponentByPosition(position: Position): Maybe<out InternalComponent> {
        return if (containsPosition(position)) {
            Maybe.of(this)
        } else {
            Maybe.empty()
        }
    }

    // TODO: should this be part of the public api?
    open fun transformToLayers() =
            listOf(LayerBuilder.newBuilder()
                    .withTileGraphics(graphics)
                    .withOffset(position)
                    .withTileset(currentTileset())
                    .build())

    override fun toString(): String {
        return "${this::class.simpleName}(id=${id.toString().substring(0, 4)}," +
                "position=$position," +
                "size=$size)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (this::class != other!!::class) return false
        other as DefaultComponent
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
