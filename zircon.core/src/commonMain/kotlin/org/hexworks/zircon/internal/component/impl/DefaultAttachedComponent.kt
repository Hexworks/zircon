package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.component.InternalAttachedComponent
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalContainer
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.event.ZirconEvent.*
import org.hexworks.zircon.internal.event.ZirconScope
import kotlin.jvm.Synchronized

class DefaultAttachedComponent(
        override val component: InternalComponent,
        override val parentContainer: InternalContainer
) : InternalAttachedComponent, InternalComponent by component {

    init {
        component.parent = Maybe.of(parentContainer)
        component.disabledProperty.updateFrom(
                observable = parentContainer.disabledProperty).keepWhile(component.hasParent)
        component.hiddenProperty.updateFrom(
                observable = parentContainer.hiddenProperty).keepWhile(component.hasParent)
        component.themeProperty.updateFrom(
                observable = parentContainer.themeProperty,
                updateWhenBound = theme == ColorThemes.defaultTheme()).keepWhile(component.hasParent)
        component.tilesetProperty.updateFrom(
                observable = parentContainer.tilesetProperty).keepWhile(component.hasParent)
    }

    // TODO: regression test ComponentDetached -> ComponentRemoved event stream
    @Synchronized
    override fun detach(): Component {
        component.parent = Maybe.empty()
        Zircon.eventBus.publish(
                event = ComponentDetached(
                        parent = parentContainer,
                        component = component,
                        emitter = this),
                eventScope = ZirconScope)
        return component
    }
}