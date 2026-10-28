package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Group
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.impl.DefaultGroup
import org.hexworks.zircon.internal.config.RuntimeConfig

@ZirconDsl
public class GroupBuilder<T : Component> : Builder<Group<T>> {

    public var isDisabled: Boolean = false
    public var isHidden: Boolean = false
    public var theme: ColorTheme = RuntimeConfig.config.defaultColorTheme
    public var tileset: TilesetResource = RuntimeConfig.config.defaultTileset
    public var name: String = ""

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
public fun <T : Component> group(init: GroupBuilder<T>.() -> Unit): Group<T> =
    GroupBuilder<T>().apply(init).build()
