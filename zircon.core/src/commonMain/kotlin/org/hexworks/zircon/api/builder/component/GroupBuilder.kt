package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Group
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.impl.DefaultGroup
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.dsl.ZirconDsl

@ZirconDsl
class GroupBuilder<T : Component> : Builder<Group<T>> {

    var isDisabled: Boolean = false
    var isHidden: Boolean = false
    var theme: ColorTheme = RuntimeConfig.config.defaultColorTheme
    var tileset: TilesetResource = RuntimeConfig.config.defaultTileset
    var name: String = ""

    override fun build(): Group<T> = DefaultGroup(
        initialIsDisabled = isDisabled,
        initialIsHidden = isHidden,
        initialTheme = theme,
        initialTileset = tileset,
        name = name
    )
}

/**
 * Creates a new [Group] using the component builder DSL and returns it.
 */
fun <T : Component> buildGroup(init: GroupBuilder<T>.() -> Unit): Group<T> =
    GroupBuilder<T>().apply(init).build()
