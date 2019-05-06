package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.cobalt.datatypes.factory.IdentifierFactory
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Visibility
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Snapshot
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.uievent.ComponentEventSource
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.api.uievent.UIEventResponse
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalContainer
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.internal.uievent.UIEventProcessor
import org.hexworks.zircon.internal.uievent.impl.DefaultUIEventProcessor

@Suppress("UNCHECKED_CAST")
abstract class DefaultComponent(
        componentMetadata: ComponentMetadata,
        override val graphics: TileGraphics = TileGraphicsBuilder
                .newBuilder()
                .withTileset(componentMetadata.tileset)
                .withSize(componentMetadata.size)
                .build(),
        private val renderer: ComponentRenderingStrategy<out Component>,
        private val layer: Layer = LayerBuilder.newBuilder()
                .withOffset(componentMetadata.position)
                .withTileGraphics(graphics)
                .build(),
        private val uiEventProcessor: DefaultUIEventProcessor = UIEventProcessor.createDefault())
    : InternalComponent,
        Layer by layer,
        UIEventProcessor by uiEventProcessor,
        ComponentEventSource by uiEventProcessor {

    // identifiable
    final override val id = IdentifierFactory.randomIdentifier()

    // component
    final override val contentPosition: Position
        get() = renderer.calculateContentPosition()

    final override val absolutePosition: Position
        get() = position + parent.map { it.absolutePosition }.orElse(Position.zero())

    final override val contentSize: Size
        get() = renderer.calculateContentSize(size)

    final override val componentStyleSetProperty: Property<ComponentStyleSet> = createPropertyFrom(componentMetadata.componentStyleSet)

    final override var componentStyleSet: ComponentStyleSet by componentStyleSetProperty.asDelegate()


    private var parent = Maybe.empty<InternalContainer>()

    final override val hiddenProperty: Property<Boolean> = layer.hiddenProperty

    final override val visibilityProperty: Property<Visibility> = createPropertyFrom(Visibility.Visible)

    final override var isVisible: Visibility by visibilityProperty.asDelegate()

    init {
        hiddenProperty.onChange {
            isVisible = if (it.newValue) {
                Visibility.Hidden
            } else {
                Visibility.Visible
            }
        }
        visibilityProperty.onChange {
            render()
        }

        componentStyleSetProperty.onChange {
            render()
        }
    }

    final override fun createSnapshot(): Snapshot {
        return graphics.createSnapshot().let { snapshot ->
            Snapshot.create(
                    cells = snapshot.cells.map { it.withPosition(it.position + absolutePosition) },
                    tileset = snapshot.tileset)
        }
    }

    override fun focusGiven(): UIEventResponse = Pass

    override fun focusTaken(): UIEventResponse = Pass

    final override fun requestFocus() {
        Zircon.eventBus.publish(
                event = ZirconEvent.RequestFocusFor(this),
                eventScope = ZirconScope)
    }


    override fun clearFocus() {
        Zircon.eventBus.publish(
                event = ZirconEvent.ClearFocus(this),
                eventScope = ZirconScope)
    }

    override fun mouseEntered(event: MouseEvent, phase: UIEventPhase): UIEventResponse {
        return if (phase == UIEventPhase.TARGET) {
            componentStyleSet.applyMouseOverStyle()
            render()
            Processed
        } else Pass
    }

    override fun mouseExited(event: MouseEvent, phase: UIEventPhase): UIEventResponse {
        return if (phase == UIEventPhase.TARGET) {
            componentStyleSet.reset()
            render()
            Processed
        } else Pass
    }

    override fun mousePressed(event: MouseEvent, phase: UIEventPhase): UIEventResponse {
        return if (phase == UIEventPhase.TARGET) {
            componentStyleSet.applyActiveStyle()
            render()
            Processed
        } else Pass
    }

    override fun mouseReleased(event: MouseEvent, phase: UIEventPhase): UIEventResponse {
        return if (phase == UIEventPhase.TARGET) {
            componentStyleSet.applyMouseOverStyle()
            render()
            Processed
        } else Pass
    }

    final override fun fetchParent() = parent

    override fun calculatePathFromRoot(): Iterable<InternalComponent> {
        return parent.map { it.calculatePathFromRoot() }.orElse(listOf()).plus(this)
    }

    final override fun attachTo(parent: InternalContainer) {
        LOGGER.debug("Attaching Component ($this) to parent ($parent).")
        this.parent.map {
            it.removeComponent(this)
        }
        this.parent = Maybe.of(parent)
    }

    final override fun detach() {
        LOGGER.debug("Attaching Component ($this) from parent (${fetchParent()}).")
        fetchParent().map {
            it.removeComponent(this)
            this.parent = Maybe.empty()
        }
    }

    override fun fetchComponentByPosition(position: Position): Maybe<out InternalComponent> {
        return if (containsPosition(position)) {
            Maybe.of(this)
        } else {
            Maybe.empty()
        }
    }

    override fun clear() {
        // no-op, by default clear does nothing on a Component, since components by default
        // have no notion of "clear". This might be different for specific components like
        // a TextArea.
    }

    override fun toFlattenedLayers(): Iterable<Layer> {
        return listOf(this)
    }

    override fun toFlattenedComponents(): Iterable<InternalComponent> {
        return listOf(this)
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
