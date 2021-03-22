package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.IconBuilder
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.component.Icon

fun icon(init: IconBuilder.() -> Unit): Icon =
    IconBuilder().apply(init).build()

fun IconBuilder.iconTile(init: TileBuilder.() -> Unit) =
    withIcon(
        TileBuilder().apply(init).build()
    )