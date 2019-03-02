package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.datatypes.Identifier
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.datatypes.factory.IdentifierFactory
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.Visibility
import org.hexworks.zircon.api.data.*
import org.hexworks.zircon.api.graphics.DrawSurface
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.graphics.TileImage
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalContainer

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

    override var visibility: Visibility = Visibility.Visible
    override val visibilityProperty: Property<Visibility> = createPropertyFrom(visibility)

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

    private val movedToPositions = mutableListOf<Position>()
    private val attachedToContainers = mutableListOf<Container>()

    private lateinit var parent: InternalContainer

    override fun close() {
        TODO("not implemented")
    }

    override fun onMouseEvent(eventType: MouseEventType, handler: MouseEventHandler): Subscription {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onKeyboardEvent(eventType: KeyboardEventType, handler: KeyboardEventHandler): Subscription {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onComponentEvent(eventType: ComponentEventType, handler: ComponentEventHandler): Subscription {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun process(event: UIEvent, phase: UIEventPhase): UIEventResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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

    override fun fetchParent(): Maybe<InternalContainer> {
        return Maybe.of(parent)
    }

    override fun calculatePathFromRoot(): Iterable<InternalComponent> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun attachTo(parent: InternalContainer) {
        attachedToContainers.add(parent)
        this.parent = parent
    }

    override fun render() {
        TODO("This operation is unsupported for a Stub")
    }

    override fun acceptsFocus(): Boolean {
        TODO("This operation is unsupported for a Stub")
    }

    override fun focusGiven(): UIEventResponse {
        TODO("This operation is unsupported for a Stub")
    }

    override fun focusTaken(): UIEventResponse {
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

    override fun clear() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toTileImage(): TileImage {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toTileGraphics(): TileGraphics {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {

        @JvmStatic
        fun create(position: Position, size: Size) = ComponentStub(position, size)
    }
}
