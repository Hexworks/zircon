package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.Identifier
import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.cobalt.factory.IdentifierFactory
import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.uievent.ComponentEvent
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.UIEvent
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.api.uievent.UIEventResponse
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalContainer
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.data.LayerState
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource

class ComponentStub(
        override val position: Position,
        override val size: Size,
        override var tileset: TilesetResource = BuiltInCP437TilesetResource.REX_PAINT_20X20,
        override val relativePosition: Position = position,
        override val contentOffset: Position = position,
        override val contentSize: Size = size,
        override val id: Identifier = IdentifierFactory.randomIdentifier(),
        override val graphics: TileGraphics = TileGraphicsBuilder.newBuilder()
                .withSize(size)
                .withTileset(tileset)
                .build()) : InternalComponent {

    override var isDisabled: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(_) {}
    override val disabledProperty: Property<Boolean>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override var theme: ColorTheme
        get() = this.colorTheme
        set(value) {
            this.colorTheme = value
        }
    override val themeProperty: Property<ColorTheme>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override val layerStates: Iterable<LayerState>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun calculatePathFromRoot(): List<InternalComponent> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override val tilesetProperty: Property<TilesetResource>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override val children: Iterable<InternalComponent>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val descendants: Iterable<InternalComponent>
        get() = listOf()
    override val relativeBounds: Rect
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun clearCustomStyle() {
        TODO("not implemented")
    }

    override fun handleMouseEvents(eventType: MouseEventType, handler: (event: MouseEvent, phase: UIEventPhase) -> UIEventResponse): Subscription {
        TODO("not implemented")
    }

    override fun processMouseEvents(eventType: MouseEventType, handler: (event: MouseEvent, phase: UIEventPhase) -> Unit): Subscription {
        TODO("not implemented")
    }

    override fun handleKeyboardEvents(eventType: KeyboardEventType, handler: (event: KeyboardEvent, phase: UIEventPhase) -> UIEventResponse): Subscription {
        TODO("not implemented")
    }

    override fun processKeyboardEvents(eventType: KeyboardEventType, handler: (event: KeyboardEvent, phase: UIEventPhase) -> Unit): Subscription {
        TODO("not implemented")
    }

    override fun handleComponentEvents(eventType: ComponentEventType, handler: (event: ComponentEvent) -> UIEventResponse): Subscription {
        TODO("not implemented")
    }

    override fun processComponentEvents(eventType: ComponentEventType, handler: (event: ComponentEvent) -> Unit): Subscription {
        TODO("not implemented")
    }

    override val hiddenProperty: Property<Boolean>
        get() = TODO("not implemented")

    override var isHidden: Boolean
        get() = TODO("not implemented")
        set(_) {}

    override val componentStyleSetProperty: Property<ComponentStyleSet> = createPropertyFrom(ComponentStyleSet.defaultStyleSet())
    override var componentStyleSet: ComponentStyleSet by componentStyleSetProperty.asDelegate()

    override val width: Int
        get() = size.width
    override val height: Int
        get() = size.height
    override var rect: Rect = Rect.create(position, size)
    override val x: Int
        get() = position.x
    override val y: Int
        get() = position.y

    var colorTheme: ColorTheme = RuntimeConfig.config.defaultColorTheme
        private set

    private val movedToPositions = mutableListOf<Position>()
    private val attachedToContainers = mutableListOf<Container>()

    private lateinit var parent: InternalContainer

    override fun moveTo(position: Position, signalComponentChange: Boolean) {
        rect = rect.withPosition(position)
        movedToPositions.add(position)
    }

    override fun close() {
        TODO("not implemented")
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

    override fun convertColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        this.theme = colorTheme
        return componentStyleSet
    }

    override fun moveTo(position: Position) {
        moveTo(position, false)
        movedToPositions.add(position)
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

    override fun fetchComponentByPosition(absolutePosition: Position): Maybe<out InternalComponent> {
        return Maybe.ofNullable(if (rect.containsPosition(absolutePosition)) {
            this
        } else null)
    }

    override fun fetchParent(): Maybe<InternalContainer> {
        return Maybe.of(parent)
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
