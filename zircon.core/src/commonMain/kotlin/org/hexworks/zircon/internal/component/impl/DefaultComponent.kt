package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.binding.Binding
import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.behavior.Movable
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.whenEnabled
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.uievent.ComponentEventSource
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.behavior.Identifiable
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalContainer
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.data.LayerState
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.event.ZirconEvent.ClearFocus
import org.hexworks.zircon.internal.event.ZirconEvent.RequestFocusFor
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.internal.graphics.ComponentLayer
import org.hexworks.zircon.internal.graphics.InternalLayer
import org.hexworks.zircon.internal.uievent.UIEventProcessor
import org.hexworks.zircon.internal.uievent.impl.DefaultUIEventProcessor
import kotlin.jvm.Synchronized

@Suppress("UNCHECKED_CAST")
abstract class DefaultComponent(
        componentMetadata: ComponentMetadata,
        private val renderer: ComponentRenderingStrategy<out Component>,
        private val contentLayer: InternalLayer = ComponentLayer(
                initialPosition = componentMetadata.relativePosition,
                initialContents = TileGraphicsBuilder
                        .newBuilder()
                        .withTileset(componentMetadata.tileset)
                        .withSize(componentMetadata.size)
                        .buildThreadSafeTileGraphics()),
        private val uiEventProcessor: DefaultUIEventProcessor = UIEventProcessor.createDefault())
    : InternalComponent,
        UIEventProcessor by uiEventProcessor,
        Identifiable by contentLayer,
        Movable by contentLayer,
        ComponentEventSource by uiEventProcessor {

    private val logger = LoggerFactory.getLogger(this::class)

    final override val absolutePosition: Position
        get() = position
    final override val relativePosition: Position
        @Synchronized
        get() = position - parent.map { it.position }.orElse(Position.zero())
    final override val relativeBounds: Rect
        @Synchronized
        get() = rect.withPosition(relativePosition)
    final override val contentOffset: Position
        @Synchronized
        get() = renderer.contentPosition
    final override val contentSize: Size
        @Synchronized
        get() = renderer.calculateContentSize(size)

    final override val componentStyleSetProperty = createPropertyFrom(componentMetadata.componentStyleSet)
    final override val disabledProperty = createPropertyFrom(false)
    final override val hiddenProperty = createPropertyFrom(false)
    final override val themeProperty = createPropertyFrom(RuntimeConfig.config.defaultColorTheme)
    final override val tilesetProperty = createPropertyFrom(componentMetadata.tileset) {
        tileset.isCompatibleWith(it)
    }

    private var styleOverride = Maybe.ofNullable(if (componentMetadata.componentStyleSet.isDefault) {
        null
    } else componentMetadata.componentStyleSet)
    private var themeStyle = componentMetadata.componentStyleSet

    final override var componentStyleSet
        get() = styleOverride.orElse(themeStyle)
        set(value) {
            componentStyleSetProperty.value = value
            styleOverride = Maybe.of(value)
        }

    final override var isDisabled: Boolean by disabledProperty.asDelegate()
    final override var isHidden: Boolean by hiddenProperty.asDelegate()
    final override var theme: ColorTheme by themeProperty.asDelegate()
    final override var tileset: TilesetResource by tilesetProperty.asDelegate()

    override val children: Iterable<InternalComponent> = listOf()
    override val descendants: Iterable<InternalComponent> = listOf()
    override val layerStates: Iterable<LayerState>
        @Synchronized
        get() = listOf(contentLayer.state)
    override val graphics: TileGraphics
        get() = contentLayer

    private var parent = Maybe.empty<InternalContainer>()
    private val bindings = mutableListOf<Binding<Any>>()

    init {
        contentLayer.hiddenProperty.updateFrom(hiddenProperty)
        contentLayer.tilesetProperty.updateFrom(tilesetProperty)
        disabledProperty.onChange {
            if (it.newValue) {
                componentStyleSet.applyDisabledStyle()
            } else {
                componentStyleSet.reset()
            }
        }
        themeProperty.onChange {
            themeStyle = convertColorTheme(it.newValue)
            render()
        }
        componentStyleSetProperty.onChange {
            styleOverride = Maybe.of(it.newValue) // TODO: add regression test for this line!
            render()
        }
    }

    @Synchronized
    override fun clearCustomStyle() {
        componentStyleSet = ComponentStyleSet.defaultStyleSet()
    }

    @Synchronized
    override fun moveTo(position: Position) {
        moveTo(position, true)
    }

    @Synchronized
    override fun moveTo(position: Position, signalComponentChange: Boolean) {
        parent.map {
            val newBounds = contentLayer.rect.withPosition(position)
            require(it.containsBoundable(newBounds)) {
                "Can't move Component ($this) with new bounds ($newBounds) out of its parent's bounds (${it})."
            }
        }
        contentLayer.moveTo(position)
        if (signalComponentChange) {
            Zircon.eventBus.publish(
                    event = ZirconEvent.ComponentMoved,
                    eventScope = ZirconScope)
        }
    }

    @Synchronized
    final override fun moveBy(position: Position) = moveTo(this.position + position)

    @Synchronized
    final override fun moveRightBy(delta: Int) = moveTo(position.withRelativeX(delta))

    @Synchronized
    final override fun moveLeftBy(delta: Int) = moveTo(position.withRelativeX(-delta))

    @Synchronized
    final override fun moveUpBy(delta: Int) = moveTo(position.withRelativeY(-delta))

    @Synchronized
    final override fun moveDownBy(delta: Int) = moveTo(position.withRelativeY(delta))

    @Synchronized
    final override fun requestFocus() {
        Zircon.eventBus.publish(
                event = RequestFocusFor(this),
                eventScope = ZirconScope)
    }


    @Synchronized
    final override fun clearFocus() {
        Zircon.eventBus.publish(
                event = ClearFocus(this),
                eventScope = ZirconScope)
    }

    @Synchronized
    override fun focusGiven() = whenEnabled {
        logger.debug("$this was given focus.")
        componentStyleSet.applyFocusedStyle()
        render()
    }

    override fun focusTaken() = whenEnabled {
        logger.debug("$this lost focus.")
        componentStyleSet.reset()
        render()
    }

    @Synchronized
    override fun acceptsFocus() = isDisabled.not()

    @Synchronized
    override fun mouseEntered(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            logger.debug("$this was mouse entered.")
            componentStyleSet.applyMouseOverStyle()
            render()
            Processed
        } else Pass
    }

    @Synchronized
    override fun mouseExited(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            logger.debug("$this was mouse exited.")
            componentStyleSet.reset()
            render()
            Processed
        } else Pass
    }

    @Synchronized
    override fun mousePressed(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            logger.debug("$this was mouse pressed.")
            componentStyleSet.applyActiveStyle()
            render()
            Processed
        } else Pass
    }

    @Synchronized
    override fun mouseReleased(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            logger.debug("$this was mouse released.")
            componentStyleSet.applyMouseOverStyle()
            render()
            Processed
        } else Pass
    }

    final override fun render() {
        logger.debug("$this was rendered.")
        (renderer as ComponentRenderingStrategy<Component>).render(this, graphics)
    }

    final override fun fetchParent() = parent

    @Synchronized
    override fun calculatePathFromRoot(): List<InternalComponent> {
        return parent.map { it.calculatePathFromRoot() }.orElse(listOf()).plus(this)
    }

    // TODO: test this thoroughly (regression)!
    @Synchronized
    override fun attachTo(parent: InternalContainer) {
        logger.debug("Attaching $this to parent ($parent).")

        val parentChanged = this.parent.map { oldParent ->
            if (parent !== oldParent) {
                oldParent.removeComponent(this)
                true
            } else false
        }.orElse(true)

        if (parentChanged) {
            this.parent = Maybe.of(parent)
            // TODO: test this check
            val hasNoCustomTheme = theme == ColorThemes.default()
            bindings.add(disabledProperty.updateFrom(
                    observable = parent.disabledProperty,
                    updateWhenBound = hasNoCustomTheme))
            bindings.add(hiddenProperty.updateFrom(
                    observable = parent.hiddenProperty,
                    updateWhenBound = hasNoCustomTheme))
            bindings.add(themeProperty.updateFrom(
                    observable = parent.themeProperty,
                    updateWhenBound = hasNoCustomTheme))
            bindings.add(tilesetProperty.updateFrom(
                    observable = parent.tilesetProperty,
                    updateWhenBound = hasNoCustomTheme))
        }
    }

    @Synchronized
    final override fun detach() {
        logger.debug("Detaching $this from parent (${fetchParent()}).")
        parent.map {
            it.removeComponent(this)
            bindings.unbindAll()
            this.parent = Maybe.empty()
        }
    }

    @Synchronized
    override fun fetchComponentByPosition(absolutePosition: Position): Maybe<out InternalComponent> {
        return if (containsPosition(absolutePosition)) {
            Maybe.of(this)
        } else {
            Maybe.empty()
        }
    }

    override fun toString(): String {
        return "${this::class.simpleName}(id=${id.toString().substring(0, 4)}, " +
                "pos=${position.x};${position.y}, size=${size.width};${size.height}, disabled=$isDisabled)"
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

    private fun MutableList<Binding<Any>>.unbindAll() {
        forEach {
            it.dispose()
        }
        clear()
    }
}
