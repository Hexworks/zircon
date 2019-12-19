package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.Identifier
import org.hexworks.cobalt.databinding.api.binding.Binding
import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Group
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.extensions.disposeAll
import kotlin.jvm.Synchronized

class DefaultGroup(
        initialIsDisabled: Boolean,
        initialIsHidden: Boolean,
        initialTheme: ColorTheme,
        initialTileset: TilesetResource) : Group {

    private val componentBindings: ComponentBindings = mutableMapOf()

    override val disabledProperty = createPropertyFrom(initialIsDisabled)
    override val hiddenProperty = createPropertyFrom(initialIsHidden)
    override val themeProperty = createPropertyFrom(initialTheme)
    override val tilesetProperty = createPropertyFrom(initialTileset) {
        it.isCompatibleWith(initialTileset)
    }

    override var isDisabled: Boolean by disabledProperty.asDelegate()
    override var isHidden: Boolean by hiddenProperty.asDelegate()
    override var theme: ColorTheme by themeProperty.asDelegate()
    override var tileset: TilesetResource by tilesetProperty.asDelegate()

    @Synchronized
    override fun add(component: Component) {
        componentBindings[component.id] ?: run {
            componentBindings[component.id] = component to mutableListOf(
                    component.disabledProperty.updateFrom(disabledProperty),
                    component.hiddenProperty.updateFrom(hiddenProperty),
                    component.themeProperty.updateFrom(themeProperty),
                    component.tilesetProperty.updateFrom(tilesetProperty))
        }
    }

    @Synchronized
    override fun remove(component: Component) {
        componentBindings.remove(component)
    }
}

private fun ComponentBindings.remove(component: Component) {
    remove(component.id)?.let { (_, bindings) ->
        bindings.disposeAll()
    }
}

private typealias ComponentBindings = MutableMap<Identifier, Pair<Component, MutableList<Binding<Any>>>>