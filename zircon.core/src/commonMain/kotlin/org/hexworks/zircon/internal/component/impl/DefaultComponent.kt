package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.behavior.Movable
import org.hexworks.zircon.api.behavior.Themeable
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.uievent.ComponentEventSource
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.api.uievent.UIEventResponse
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
        private val uiEventProcessor: DefaultUIEventProcessor = UIEventProcessor.createDefault(),
        private val themeable: Themeable = Themeable.create(RuntimeConfig.config.defaultColorTheme))
    : InternalComponent,
        UIEventProcessor by uiEventProcessor,
        Identifiable by contentLayer,
        Movable by contentLayer,
        TilesetOverride by contentLayer,
        Themeable by themeable,
        ComponentEventSource by uiEventProcessor {

    override val absolutePosition: Position
        get() = position

    final override val relativePosition: Position
        @Synchronized
        get() = position - parent.map { it.position }.orElse(Position.zero())

    override val relativeBounds: Rect
        @Synchronized
        get() = rect.withPosition(relativePosition)

    final override val contentOffset: Position
        @Synchronized
        get() = renderer.contentPosition

    final override val contentSize: Size
        @Synchronized
        get() = renderer.calculateContentSize(size)

    final override val componentStyleSetProperty: Property<ComponentStyleSet> = createPropertyFrom(componentMetadata.componentStyleSet)

    final override var componentStyleSet: ComponentStyleSet by componentStyleSetProperty.asDelegate()

    override var isHidden: Boolean
        get() = contentLayer.isHidden
        set(value) {
            contentLayer.isHidden = value
        }

    final override val hiddenProperty: Property<Boolean> = contentLayer.hiddenProperty

    override val children: Iterable<InternalComponent> = listOf()

    override val descendants: Iterable<InternalComponent> = listOf()

    override val layerStates: Iterable<LayerState>
        @Synchronized
        get() = listOf(contentLayer.state)

    override val graphics: TileGraphics
        get() = contentLayer

    private var parent = Maybe.empty<InternalContainer>()

    init {
        hiddenProperty.onChange {
            render()
        }
        componentStyleSetProperty.onChange {
            render()
        }
        @Suppress("LeakingThis")
        themeProperty.onChange {
            applyColorTheme(it.newValue)
        }
    }

    override fun focusGiven(): UIEventResponse = Pass

    override fun focusTaken(): UIEventResponse = Pass

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
    override fun clearFocus() {
        Zircon.eventBus.publish(
                event = ClearFocus(this),
                eventScope = ZirconScope)
    }

    @Synchronized
    override fun mouseEntered(event: MouseEvent, phase: UIEventPhase): UIEventResponse {
        return if (phase == UIEventPhase.TARGET) {
            componentStyleSet.applyMouseOverStyle()
            render()
            Processed
        } else Pass
    }

    @Synchronized
    override fun mouseExited(event: MouseEvent, phase: UIEventPhase): UIEventResponse {
        return if (phase == UIEventPhase.TARGET) {
            componentStyleSet.reset()
            render()
            Processed
        } else Pass
    }

    @Synchronized
    override fun mousePressed(event: MouseEvent, phase: UIEventPhase): UIEventResponse {
        return if (phase == UIEventPhase.TARGET) {
            componentStyleSet.applyActiveStyle()
            render()
            Processed
        } else Pass
    }

    @Synchronized
    override fun mouseReleased(event: MouseEvent, phase: UIEventPhase): UIEventResponse {
        return if (phase == UIEventPhase.TARGET) {
            componentStyleSet.applyMouseOverStyle()
            render()
            Processed
        } else Pass
    }

    final override fun fetchParent() = parent

    @Synchronized
    override fun calculatePathFromRoot(): List<InternalComponent> {
        return parent.map { it.calculatePathFromRoot() }.orElse(listOf()).plus(this)
    }

    @Synchronized
    override fun attachTo(parent: InternalContainer) {
        LOGGER.debug("Attaching Component ($this) to parent ($parent).")
        this.parent.map { oldParent ->
            if (parent !== oldParent) {
                oldParent.removeComponent(this)
            }
        }
        this.parent = Maybe.of(parent)
    }

    @Synchronized
    final override fun detach() {
        LOGGER.debug("Attaching Component ($this) from parent (${fetchParent()}).")
        parent.map {
            it.removeComponent(this)
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

    companion object {
        private val LOGGER = LoggerFactory.getLogger(Component::class)
    }
}
