package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.DrawSurface
import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.event.ComponentSubscription
import org.hexworks.zircon.api.event.Subscription
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.kotlin.map
import org.hexworks.zircon.api.listener.InputListener
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Identifier
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.behavior.impl.DefaultBoundable
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.platform.factory.ThreadSafeQueueFactory

@Suppress("UNCHECKED_CAST")
abstract class DefaultComponent(
        position: Position,
        size: Size,
        tileset: TilesetResource,
        private var componentStyles: ComponentStyleSet,
        private val renderer: ComponentRenderingStrategy<out Component>,
        private val graphics: TileGraphics = TileGraphicsBuilder
                .newBuilder()
                .tileset(tileset)
                .size(size)
                .build(),
        private val boundable: DefaultBoundable = DefaultBoundable(
                size = size,
                position = position))
    : InternalComponent,
        Drawable by graphics,
        TilesetOverride by graphics,
        Boundable by boundable {


    final override val id = Identifier.randomIdentifier()

    private val subscriptions = ThreadSafeQueueFactory.create<ComponentSubscription>()
    private var parent = Maybe.empty<Container>()

    final override fun inputEmitted(input: Input) {
        subscriptions.forEach {
            it.listener.inputEmitted(input)
        }
    }

    final override fun onInput(listener: InputListener): Subscription {
        return ComponentSubscription(listener, subscriptions).also {
            subscriptions.add(it)
        }
    }

    final override fun contentPosition() = renderer.contentPosition()

    final override fun contentSize() = renderer.contentSize(size())

    final override fun moveTo(position: Position): Boolean {
        return boundable.moveTo(position)
    }

    final override fun parent() = parent

    final override fun attachTo(parent: Container) {
        this.parent.map {
            it.removeComponent(this)
        }
        this.parent = Maybe.of(parent)
    }

    final override fun absolutePosition(): Position {
        // TODO: regression test this -> 3 nested components
        return position() + parent.map { it.absolutePosition() }.orElse(Position.zero())
    }

    // TODO: delegate these to behavior
    // TODO: move all tile getters / setters into their own behavior
    final override fun getRelativeTileAt(position: Position): Maybe<Tile> {
        return graphics.getTileAt(position)
    }

    final override fun setRelativeTileAt(position: Position, tile: Tile) {
        graphics.setTileAt(position, tile)
    }

    final override fun getTileAt(position: Position): Maybe<Tile> {
        return graphics.getTileAt(position.minus(position()))
    }

    final override fun setTileAt(position: Position, tile: Tile) {
        graphics.setTileAt(position.minus(position()), tile)
    }

    final override fun snapshot(): Map<Position, Tile> {
        return graphics.snapshot()
    }

    final override fun fill(filler: Tile): Layer {
        graphics.fill(filler)
        return this
    }

    final override fun componentStyleSet() = componentStyles

    final override fun setComponentStyleSet(componentStyleSet: ComponentStyleSet) {
        // TODO: render?
        this.componentStyles = componentStyleSet
    }

    final override fun applyStyle(styleSet: StyleSet) {
        // TODO: should the user be able to do this?
    }

    final override fun tileGraphics() = graphics

    override fun createCopy(): Layer {
        TODO("Creating copies of Components is not supported yet.")
    }

    override fun draw(drawable: Drawable, position: Position) {
        graphics.draw(drawable, position)
    }

    override fun drawOnto(surface: DrawSurface, position: Position) {
        surface.draw(graphics, position)
    }

    override fun fetchComponentByPosition(position: Position) =
            if (containsPosition(position)) {
                Maybe.of(this)
            } else {
                Maybe.empty<InternalComponent>()
            }

    open fun transformToLayers() =
            listOf(LayerBuilder.newBuilder()
                    .tileGraphic(graphics)
                    .offset(position())
                    .tileset(tileset())
                    .build())

    override fun toString(): String {
        return "${this::class.simpleName}(id=${id.toString().substring(0, 4)}," +
                "position=${position()}," +
                "size=${size()})"
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
