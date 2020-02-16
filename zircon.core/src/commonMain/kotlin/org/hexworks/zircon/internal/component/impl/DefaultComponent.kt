package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.binding.bindTransform
import org.hexworks.cobalt.databinding.api.extension.createPropertyFrom
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.behavior.Movable
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.whenEnabled
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.uievent.*
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
        private val uiEventProcessor: DefaultUIEventProcessor = UIEventProcessor.createDefault()
) : InternalComponent,
        UIEventProcessor by uiEventProcessor,
        Identifiable by contentLayer,
        Movable by contentLayer,
        ComponentEventSource by uiEventProcessor {

    private val logger = LoggerFactory.getLogger(this::class)

    final override val parentProperty = Maybe.empty<InternalContainer>().toProperty()
    final override var parent: Maybe<InternalContainer> by parentProperty.asDelegate()
    final override val hasParent: ObservableValue<Boolean> = parentProperty.bindTransform { it.isPresent }

    override val isAttached: Boolean
        get() = parent.isPresent

    final override val hasFocus = false.toProperty()

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
    final override var componentStyleSet
        get() = styleOverride.orElse(themeStyle)
        set(value) {
            componentStyleSetProperty.value = value
            styleOverride = Maybe.of(value)
        }

    override val children: Iterable<InternalComponent> = listOf()
    override val descendants: Iterable<InternalComponent> = listOf()
    override val layerStates: Iterable<LayerState>
        @Synchronized
        get() = listOf(contentLayer.state)
    override val graphics: TileGraphics
        get() = contentLayer

    final override val disabledProperty = false.toProperty()
    final override var isDisabled: Boolean by disabledProperty.asDelegate()

    final override val hiddenProperty = false.toProperty()
    final override var isHidden: Boolean by hiddenProperty.asDelegate()

    final override val themeProperty = RuntimeConfig.config.defaultColorTheme.toProperty()
    final override var theme: ColorTheme by themeProperty.asDelegate()

    final override val tilesetProperty = componentMetadata.tileset.toProperty {
        tileset.isCompatibleWith(it)
    }
    final override var tileset: TilesetResource by tilesetProperty.asDelegate()

    private var styleOverride = Maybe.ofNullable(if (componentMetadata.componentStyleSet.isDefault) {
        null
    } else componentMetadata.componentStyleSet)
    private var themeStyle = componentMetadata.componentStyleSet

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
                    event = ZirconEvent.ComponentMoved(this),
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

    final override fun requestFocus(): Boolean {
        Zircon.eventBus.publish(
                event = RequestFocusFor(this, this),
                eventScope = ZirconScope)
        return hasFocus.value
    }


    final override fun clearFocus() {
        Zircon.eventBus.publish(
                event = ClearFocus(this, this),
                eventScope = ZirconScope)
    }

    override fun focusGiven() = whenEnabled {
        logger.debug("$this was given focus.")
        if (componentState != ComponentState.ACTIVE) {
            componentStyleSet.applyFocusedStyle()
        }
        render()
        hasFocus.value = true
    }

    override fun focusTaken() = whenEnabled {
        logger.debug("$this lost focus.")
        if (componentState != ComponentState.ACTIVE) {
            componentStyleSet.reset()
        }
        render()
        hasFocus.value = false
    }

    override fun acceptsFocus() = isDisabled.not()

    override fun mouseEntered(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            logger.debug("$this was mouse entered.")
            componentStyleSet.applyMouseOverStyle()
            render()
            Processed
        } else Pass
    }

    override fun mouseExited(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            logger.debug("$this was mouse exited.")
            if (hasFocus.value) {
                componentStyleSet.applyFocusedStyle()
            } else {
                componentStyleSet.reset()
            }
            render()
            Processed
        } else Pass
    }

    override fun mousePressed(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            logger.debug("$this was mouse pressed.")
            componentStyleSet.applyActiveStyle()
            render()
            Processed
        } else Pass
    }

    override fun mouseReleased(event: MouseEvent, phase: UIEventPhase) = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET) {
            logger.debug("$this was mouse released.")
            componentStyleSet.applyMouseOverStyle()
            render()
            Processed
        } else Pass
    }

    final override fun render() {
        logger.trace("$this was rendered.")
        (renderer as ComponentRenderingStrategy<Component>).render(this, graphics)
    }

    @Synchronized
    override fun calculatePathFromRoot(): List<InternalComponent> {
        return parent.map { it.calculatePathFromRoot() }.orElse(listOf()).plus(this)
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
        if (other == null) return false
        if (this::class != other::class) return false
        other as DefaultComponent
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}
