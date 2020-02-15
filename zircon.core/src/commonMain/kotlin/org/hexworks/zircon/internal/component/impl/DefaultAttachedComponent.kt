package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.component.InternalAttachedComponent
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalContainer
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.event.ZirconScope
import kotlin.jvm.Synchronized

class DefaultAttachedComponent(
        override val component: InternalComponent,
        override val parentContainer: InternalContainer
) : InternalAttachedComponent, InternalComponent by component {

    init {
        component.parent = Maybe.of(parentContainer)
        val hasNoCustomTheme = theme == ColorThemes.default()
        component.disabledProperty.updateFrom(
                observable = parentContainer.disabledProperty,
                updateWhenBound = hasNoCustomTheme).keepWhile(component.hasParent)
        component.hiddenProperty.updateFrom(
                observable = parentContainer.hiddenProperty,
                updateWhenBound = hasNoCustomTheme).keepWhile(component.hasParent)
        component.themeProperty.updateFrom(
                observable = parentContainer.themeProperty,
                updateWhenBound = hasNoCustomTheme).keepWhile(component.hasParent)
        component.tilesetProperty.updateFrom(
                observable = parentContainer.tilesetProperty,
                updateWhenBound = hasNoCustomTheme).keepWhile(component.hasParent)
    }

    // TODO: regression test ComponentDetached -> ComponentRemoved event stream
    @Synchronized
    override fun detach(): Component {
        component.parent = Maybe.empty()
        Zircon.eventBus.publish(
                event = ZirconEvent.ComponentDetached(
                        parent = parentContainer,
                        component = component,
                        emitter = this),
                eventScope = ZirconScope)
        return component
    }
}