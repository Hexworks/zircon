package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.datatypes.Identifier
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.datatypes.factory.IdentifierFactory
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.graphics.DrawSurface
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.listener.InputListener
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.InternalComponent

class ComponentStub(
        override val position: Position,
        override val size: Size,
        val tileset: TilesetResource = BuiltInCP437TilesetResource.REX_PAINT_20X20,
        override var componentStyleSet: ComponentStyleSet = ComponentStyleSet.defaultStyleSet(),
        override val absolutePosition: Position = position,
        override val contentPosition: Position = position,
        override val contentSize: Size = size,
        override val id: Identifier = IdentifierFactory.randomIdentifier(),
        override val graphics: TileGraphics = TileGraphicsBuilder.newBuilder()
                .withSize(size)
                .withTileset(tileset)
                .build()) : InternalComponent {

    override val width: Int
        get() = size.width
    override val height: Int
        get() = size.height
    override val rect: Rect
        get() = Rect.create(position, size)
    override val x: Int
        get() = position.x
    override val y: Int
        get() = position.y

    lateinit var colorTheme: ColorTheme
        private set

    val movedToPositions = mutableListOf<Position>()
    val attachedToContainers = mutableListOf<Container>()

    private lateinit var parent: Container

    override fun isAttached(): Boolean {
        TODO("This operation is unsupported for a Stub")
    }

    override fun detach() {
        TODO("This operation is unsupported for a Stub")
    }

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        this.colorTheme = colorTheme
        return componentStyleSet
    }

    override fun onInput(listener: InputListener): Subscription {
        TODO("This operation is unsupported for a Stub")
    }

    override fun getAbsoluteTileAt(position: Position): Maybe<Tile> {
        TODO("This operation is unsupported for a Stub")
    }

    override fun setAbsoluteTileAt(position: Position, tile: Tile) {
        TODO("This operation is unsupported for a Stub")
    }

    override fun createCopy(): Layer {
        TODO("This operation is unsupported for a Stub")
    }

    override fun fill(filler: Tile): Layer {
        TODO("This operation is unsupported for a Stub")
    }

    override fun setTileAt(position: Position, tile: Tile) {
        TODO("This operation is unsupported for a Stub")
    }

    override fun createSnapshot(): Snapshot {
        TODO("This operation is unsupported for a Stub")
    }

    override fun getTileAt(position: Position): Maybe<Tile> {
        TODO("This operation is unsupported for a Stub")
    }

    override fun currentTileset(): TilesetResource {
        return tileset
    }

    override fun useTileset(tileset: TilesetResource) {
        TODO("This operation is unsupported for a Stub")
    }

    override fun drawOnto(surface: DrawSurface, position: Position) {
        TODO("This operation is unsupported for a Stub")
    }

    override fun moveTo(position: Position): Boolean {
        movedToPositions.add(position)
        return true
    }

    override fun intersects(boundable: Boundable): Boolean {
        TODO("This operation is unsupported for a Stub")
    }

    override fun containsPosition(position: Position): Boolean {
        TODO("This operation is unsupported for a Stub")
    }

    override fun containsBoundable(boundable: Boundable): Boolean {
        TODO("This operation is unsupported for a Stub")
    }

    override fun fetchComponentByPosition(position: Position): Maybe<out InternalComponent> {
        return Maybe.ofNullable(if (rect.containsPosition(position)) {
            this
        } else null)
    }

    override fun fetchParent(): Maybe<Container> {
        return Maybe.of(parent)
    }

    override fun attachTo(parent: Container) {
        attachedToContainers.add(parent)
        this.parent = parent
    }

    override fun render() {
        TODO("This operation is unsupported for a Stub")
    }

    override fun acceptsFocus(): Boolean {
        TODO("This operation is unsupported for a Stub")
    }

    override fun giveFocus(input: Maybe<Input>): Boolean {
        TODO("This operation is unsupported for a Stub")
    }

    override fun takeFocus(input: Maybe<Input>) {
        TODO("This operation is unsupported for a Stub")
    }

    override fun toFlattenedLayers(): Iterable<Layer> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toFlattenedComponents(): Iterable<InternalComponent> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun requestFocus() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearFocus() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {

        @JvmStatic
        fun create(position: Position, size: Size) = ComponentStub(position, size)
    }
}
