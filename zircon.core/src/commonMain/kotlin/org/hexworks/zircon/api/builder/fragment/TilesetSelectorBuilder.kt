package org.hexworks.zircon.api.builder.fragment

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Group
import org.hexworks.zircon.api.fragment.Selector
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.TilesetResources
import kotlin.jvm.JvmStatic

/**
 * Builder for a [Selector] to change the tileset of multiple [TilesetOverride]s or [Group]s at runtime.
 */
class TilesetSelectorBuilder private constructor(
    width: Int,
    tileset: TilesetResource
) : SelectorBuilder<TilesetResource>(width, TilesetResources.allTextTilesetsCompatibleWith(tileset)) {

    private var tilesetProperties = listOf<Property<TilesetResource>>()
    private var tilesetOverrides = listOf<TilesetOverride>()
    private var groups = listOf<Group<out Component>>()

    init {
        withDefaultSelected(tileset)
    }

    /**
     * Sets the given [tilesetOverrides] to be updated whenever the underlying [Selector]'s
     * [Selector.selectedValue] changes.
     */
    fun withTilesetOverrides(vararg tilesetOverrides: TilesetOverride) = also {
        this.tilesetOverrides = tilesetOverrides.toList()
    }

    fun withTilesetProperties(vararg tilesetProperties: Property<TilesetResource>) = also {
        this.tilesetProperties = tilesetProperties.toList()
    }

    /**
     * Sets the given [groups] to be updated whenever the underlying [Selector]'s
     * [Selector.selectedValue] changes.
     */
    fun withGroups(vararg groups: Group<out Component>) = also {
        this.groups = groups.toList()
    }

    override fun build(): Selector<TilesetResource> = super.build().apply {
        tilesetOverrides.forEach {
            it.tilesetProperty.updateFrom(selectedValue)
        }
        groups.forEach {
            it.tilesetProperty.updateFrom(selectedValue)
        }
        tilesetProperties.forEach {
            it.updateFrom(selectedValue)
        }
    }

    override fun createCopy(): Builder<Selector<TilesetResource>> = super.createCopy().apply {
        withTilesetOverrides(*tilesetOverrides.toTypedArray())
        withGroups(*groups.toTypedArray())
    }

    companion object {

        /**
         * Creates a new [TilesetSelectorBuilder] to build [Selector]s for [TilesetResource]s.
         */
        @JvmStatic
        fun newBuilder(width: Int, tileset: TilesetResource) = TilesetSelectorBuilder(width, tileset)
    }
}
