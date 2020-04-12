package org.hexworks.zircon.internal.component

import org.hexworks.cobalt.databinding.api.collection.ObservableList
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.component.impl.RootContainer
import org.hexworks.zircon.internal.data.LayerState
import org.hexworks.zircon.internal.uievent.ComponentEventAdapter
import org.hexworks.zircon.internal.uievent.KeyboardEventAdapter
import org.hexworks.zircon.internal.uievent.MouseEventAdapter
import org.hexworks.zircon.internal.uievent.UIEventProcessor

/**
 * A [InternalComponent] represents the internal API of the [Component] interface which adds
 * functionality which will be used by Zircon internally. This makes it possible to have
 * a clean API for [Component]s but enables Zircon and the developers of custom [Component]s
 * to interact with them in a more meaningful manner.
 */
interface InternalComponent :
        Component, ComponentEventAdapter, KeyboardEventAdapter, MouseEventAdapter, UIEventProcessor {

    var root: Maybe<RootContainer>
    val rootValue: ObservableValue<Maybe<RootContainer>>

    var parent: Maybe<InternalContainer>
    val parentProperty: Property<Maybe<InternalContainer>>
    val hasParent: ObservableValue<Boolean>

    val isAttached: Boolean
        get() = parent.isPresent
    val isAttachedToRoot: Boolean
        get() = root.isPresent

    override var componentState: ComponentState

    val layerStates: List<LayerState>

    /**
     * The [TileGraphics] through which this [InternalComponent] can be drawn upon.
     */
    val graphics: TileGraphics

    /**
     * The immediate child [Component]s of this [Component].
     */
    val children: ObservableList<InternalComponent>

    /**
     * Renders this component to the underlying [TileGraphics].
     */
    fun render()

    /**
     * Converts the given [ColorTheme] to the equivalent [ComponentStyleSet] representation.
     */
    fun convertColorTheme(colorTheme: ColorTheme): ComponentStyleSet

}
