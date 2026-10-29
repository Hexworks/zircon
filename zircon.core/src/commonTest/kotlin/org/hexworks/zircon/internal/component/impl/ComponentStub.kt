package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.uievent.KeyboardEventMatcher
import org.hexworks.zircon.api.behavior.extensions.withPosition
import kotlinx.collections.immutable.persistentListOf
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.databinding.api.binding.bindTransform
import org.hexworks.cobalt.databinding.api.collection.ObservableList
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.value.ObservableValue

import org.hexworks.cobalt.databinding.api.value.BindingAction
import org.hexworks.cobalt.databinding.api.value.UpdateOnBind
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalContainer
import org.hexworks.zircon.api.config.ZirconDefaults
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource

class ComponentStub(
    initialPosition: Position,
    initialSize: Size,
    override var tileset: TilesetResource = BuiltInCP437TilesetResource.REX_PAINT_20X20,
    override val id: UUID = UUID.randomUUID(),
) : InternalComponent {

    override val boundsProperty: Property<Boundable> = Boundable.create(initialPosition, initialSize).toProperty()
    override val bounds: Boundable by boundsProperty.asDelegate()

    override val position: Position
        get() = bounds.position
    override val activationShortcut: KeyboardEventMatcher? = null

    override val absolutePosition: Position
        get() = position
    override val positionProperty: ObservableValue<Position> = boundsProperty.bindTransform { it.position }

    override val size: Size
        get() = bounds.size
    override val sizeProperty: ObservableValue<Size> = boundsProperty.bindTransform { it.size }

    override val contentOffset: Position = Position.ZERO
    override val contentSize: Size = initialSize
    override val contentBoundsProperty: ObservableValue<Boundable> =
        boundsProperty.bindTransform { Boundable.create(it.position + contentOffset, contentSize) }
    override val contentBounds: Boundable by contentBoundsProperty.asDelegate()

    override val originalPosition: Position = initialPosition

    override val disabledProperty: Property<Boolean> = false.toProperty()
    override var isDisabled: Boolean by disabledProperty.asDelegate()

    override val themeProperty: Property<ColorTheme> = ZirconDefaults.current.theme.toProperty()
    override var theme: ColorTheme by themeProperty.asDelegate()


    override var root: RootContainer?
        get() = error("not implemented")
        set(_) {}
    override val rootProperty: ObservableValue<RootContainer?>
        get() = error("not implemented")

    override val tilesetProperty = ZirconDefaults.current.tileset.toProperty()
    override val hasFocus: Boolean
        get() = false
    override val hasFocusProperty: ObservableValue<Boolean>
        get() = false.toProperty()

    override val parentProperty: Property<InternalContainer?> = null.toProperty()
    override val componentStateProperty: Property<ComponentState> = ComponentState.DEFAULT.toProperty()
    override var componentState: ComponentState by componentStateProperty.asDelegate()
    override var parent: InternalContainer? by parentProperty.asDelegate()
    override val hasParent = parentProperty.bindTransform { it != null }

    override val isAttached: Boolean
        get() = parent != null
    override val bindingAction: BindingAction
        get() = UpdateOnBind

    override val children: ObservableList<InternalComponent>
        get() = persistentListOf<InternalComponent>().toProperty()

    override val hiddenProperty = false.toProperty()

    override var isHidden: Boolean by hiddenProperty.asDelegate()

    override val componentStyleSetProperty: Property<ComponentStyleSet> =
        ComponentStyleSet.DEFAULT_STYLE.toProperty()
    override var componentStyleSet: ComponentStyleSet by componentStyleSetProperty.asDelegate()

    private val movedToPositions = mutableListOf<Position>()

    override fun clearCustomStyle() {

    }

    override fun resetState() {
        TODO("Not yet implemented")
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

    override val closedProperty: ObservableValue<Boolean>
        get() = TODO("Not yet implemented")

    override fun invalidateRender() {}

    override fun convertColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        this.theme = colorTheme
        return componentStyleSet
    }

    override fun moveTo(position: Position): Boolean {
        boundsProperty.value = bounds.withPosition(position)
        movedToPositions.add(position)
        return true
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

    override fun render(): TileGraphics {
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
        fun create(position: Position, size: Size) = ComponentStub(position, size)
    }
}
