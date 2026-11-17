@file:Suppress("DEPRECATION")

package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.BinarySelector
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ExclusiveSelectionGroup
import org.hexworks.zircon.api.config.ZirconDefaults
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.impl.DefaultExclusiveSelectionGroup

/**
 * Builds an [ExclusiveSelectionGroup] of the given [members]; more can be added later with
 * [ExclusiveSelectionGroup.addComponent].
 */
@ZirconDsl
public open class ExclusiveSelectionGroupBuilder<T> : Builder<ExclusiveSelectionGroup<T>> where T : Component, T : Selectable {

    public var isDisabled: Boolean = false
    public var isHidden: Boolean = false
    public var theme: ColorTheme = ZirconDefaults.current.theme
    public var tileset: TilesetResource = ZirconDefaults.current.tileset
    public var members: List<T> = listOf()

    override fun build(): ExclusiveSelectionGroup<T> = DefaultExclusiveSelectionGroup<T>(
        initialIsDisabled = isDisabled,
        initialIsHidden = isHidden,
        initialTheme = theme,
        initialTileset = tileset
    ).apply {
        members.forEach { addComponent(it) }
    }
}

/**
 * Creates a new [ExclusiveSelectionGroup] using the builder DSL and returns it.
 */
public fun <T> exclusiveSelectionGroup(init: ExclusiveSelectionGroupBuilder<T>.() -> Unit): ExclusiveSelectionGroup<T>
        where T : Component, T : Selectable = ExclusiveSelectionGroupBuilder<T>().apply(init).build()

/**
 * Creates a new [ExclusiveSelectionGroup] of [BinarySelector]s using the builder DSL and returns it.
 */
public fun selectorGroup(init: ExclusiveSelectionGroupBuilder<BinarySelector>.() -> Unit): ExclusiveSelectionGroup<BinarySelector> =
    ExclusiveSelectionGroupBuilder<BinarySelector>().apply(init).build()
