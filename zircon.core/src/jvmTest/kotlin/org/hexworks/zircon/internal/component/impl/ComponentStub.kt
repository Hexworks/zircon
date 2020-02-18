package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.core.platform.factory.UUIDFactory
import org.hexworks.cobalt.databinding.api.binding.bindTransform
import org.hexworks.cobalt.databinding.api.extension.createPropertyFrom
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.uievent.*
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
        override val id: UUID = UUIDFactory.randomUUID(),
        override val graphics: TileGraphics = TileGraphicsBuilder.newBuilder()
                .withSize(size)
                .withTileset(tileset)
                .build()) : InternalComponent {

    override val disabledProperty: Property<Boolean> = false.toProperty()
    override var isDisabled: Boolean by disabledProperty.asDelegate()

    override val themeProperty: Property<ColorTheme> = RuntimeConfig.config.defaultColorTheme.toProperty()
    override var theme: ColorTheme by themeProperty.asDelegate()

    override val layerStates: Iterable<LayerState>
        get() = listOf()

    override fun calculatePathFromRoot(): List<InternalComponent> = listOf()

    override val tilesetProperty = RuntimeConfig.config.defaultTileset.toProperty()
    override val hasFocus: ObservableValue<Boolean>
        get() = false.toProperty()

    override val parentProperty = Maybe.empty<InternalContainer>().toProperty()
    override var componentState: ComponentState
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var parent: Maybe<InternalContainer> by parentProperty.asDelegate()
    override val hasParent = parentProperty.bindTransform { it.isPresent }

    override val isAttached: Boolean
        get() = parent.isPresent

    override val children: Iterable<InternalComponent> = listOf()
    override val descendants: Iterable<InternalComponent>
        get() = listOf()
    override val relativeBounds: Rect = Rect.create(size = Size.zero())
    override val componentStateValue: ObservableValue<ComponentState>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override val hiddenProperty = false.toProperty()

    override var isHidden: Boolean by hiddenProperty.asDelegate()

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

    private val movedToPositions = mutableListOf<Position>()
    private val attachedToContainers = mutableListOf<Container>()

    override fun clearCustomStyle() {

    }

    override fun onActivated(fn: (ComponentEvent) -> Unit): Subscription {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFocusGiven(fn: (ComponentEvent) -> Unit): Subscription {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFocusTaken(fn: (ComponentEvent) -> Unit): Subscription {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    override val isClosed: ObservableValue<Boolean>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

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

    override fun requestFocus(): Boolean {
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
