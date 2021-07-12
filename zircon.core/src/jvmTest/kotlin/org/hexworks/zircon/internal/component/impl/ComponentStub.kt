package org.hexworks.zircon.internal.component.impl

import kotlinx.collections.immutable.persistentListOf
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.core.platform.factory.UUIDFactory
import org.hexworks.cobalt.databinding.api.binding.bindTransform
import org.hexworks.cobalt.databinding.api.collection.ObservableList
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
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
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource

class ComponentStub(
    override val position: Position,
    override val size: Size,
    override var tileset: TilesetResource = BuiltInCP437TilesetResource.REX_PAINT_20X20,
    override val relativePosition: Position = position,
    override val contentOffset: Position = position,
    override val contentSize: Size = size,
    override val id: UUID = UUIDFactory.randomUUID(),
) : InternalComponent {

    override val disabledProperty: Property<Boolean> = false.toProperty()
    override var isDisabled: Boolean by disabledProperty.asDelegate()

    override val themeProperty: Property<ColorTheme> = RuntimeConfig.config.defaultColorTheme.toProperty()
    override var theme: ColorTheme by themeProperty.asDelegate()

    override fun asInternalComponent() = this

    override val absolutePosition: Position
        get() = position

    override var root: Maybe<RootContainer>
        get() = error("not implemented")
        set(_) {}
    override val rootValue: ObservableValue<Maybe<RootContainer>>
        get() = error("not implemented")

    override val tilesetProperty = RuntimeConfig.config.defaultTileset.toProperty()
    override val hasFocus: Boolean
        get() = false
    override val hasFocusValue: ObservableValue<Boolean>
        get() = false.toProperty()

    override val parentProperty = Maybe.empty<InternalContainer>().toProperty()
    override var componentState: ComponentState
        get() = error("not implemented")
        set(_) {}
    override var parent: Maybe<InternalContainer> by parentProperty.asDelegate()
    override val hasParent = parentProperty.bindTransform { it.isPresent }

    override val rectValue: ObservableValue<Rect>
        get() = error("not implemented")

    override val isAttached: Boolean
        get() = parent.isPresent
    override val updateOnAttach: Boolean
        get() = true

    override val children: ObservableList<InternalComponent>
        get() = persistentListOf<InternalComponent>().toProperty()
    override val relativeBounds: Rect = Rect.create(size = Size.zero())
    override val componentStateValue: ObservableValue<ComponentState>
        get() = error("not implemented")

    override val hiddenProperty = false.toProperty()

    override var isHidden: Boolean by hiddenProperty.asDelegate()

    override val componentStyleSetProperty: Property<ComponentStyleSet> =
        ComponentStyleSet.defaultStyleSet().toProperty()
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

    override fun clearCustomStyle() {

    }

    override fun onActivated(fn: (ComponentEvent) -> Unit): Subscription {
        error("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDeactivated(fn: (ComponentEvent) -> Unit): Subscription {
        error("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFocusGiven(fn: (ComponentEvent) -> Unit): Subscription {
        error("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFocusTaken(fn: (ComponentEvent) -> Unit): Subscription {
        error("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleMouseEvents(
        eventType: MouseEventType,
        handler: (event: MouseEvent, phase: UIEventPhase) -> UIEventResponse
    ): Subscription {
        error("not implemented")
    }

    override fun processMouseEvents(
        eventType: MouseEventType,
        handler: (event: MouseEvent, phase: UIEventPhase) -> Unit
    ): Subscription {
        error("not implemented")
    }

    override fun handleKeyboardEvents(
        eventType: KeyboardEventType,
        handler: (event: KeyboardEvent, phase: UIEventPhase) -> UIEventResponse
    ): Subscription {
        error("not implemented")
    }

    override fun processKeyboardEvents(
        eventType: KeyboardEventType,
        handler: (event: KeyboardEvent, phase: UIEventPhase) -> Unit
    ): Subscription {
        error("not implemented")
    }

    override fun handleComponentEvents(
        eventType: ComponentEventType,
        handler: (event: ComponentEvent) -> UIEventResponse
    ): Subscription {
        error("not implemented")
    }

    override fun processComponentEvents(
        eventType: ComponentEventType,
        handler: (event: ComponentEvent) -> Unit
    ): Subscription {
        error("not implemented")
    }

    override val name: String
        get() = TODO("Not yet implemented")

    override fun close() {
        error("not implemented")
    }

    override fun process(event: UIEvent, phase: UIEventPhase): UIEventResponse {
        error("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override val closedValue: ObservableValue<Boolean>
        get() = TODO("Not yet implemented")

    override fun convertColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        this.theme = colorTheme
        return componentStyleSet
    }

    override fun moveTo(position: Position): Boolean {
        rect = rect.withPosition(position)
        movedToPositions.add(position)
        return true
    }

    override fun intersects(boundable: Boundable): Boolean {
        error("not implemented")
    }

    override fun containsPosition(position: Position): Boolean {
        error("not implemented")
    }

    override fun containsBoundable(boundable: Boundable): Boolean {
        error("not implemented")
    }

    override fun acceptsFocus(): Boolean {
        error("not implemented")
    }

    override fun focusGiven(): UIEventResponse {
        error("not implemented")
    }

    override fun focusTaken(): UIEventResponse {
        error("not implemented")
    }

    override fun render(graphics: TileGraphics) {
        error("not implemented")
    }

    override fun requestFocus(): Boolean {
        error("not implemented")
    }

    override fun clearFocus() {
        error("not implemented")
    }

    companion object {

        @JvmStatic
        fun create(position: Position, size: Size) = ComponentStub(position, size)
    }
}
