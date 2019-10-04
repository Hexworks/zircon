package org.hexworks.zircon.api.component.data

import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import org.junit.Test

class ComponentMetadataTest {

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionWhenCreatingAndPositionIsNegative() {
        ComponentMetadata(
                relativePosition = Position.create(-1, 0),
                size = Size.zero(),
                tileset = BuiltInCP437TilesetResource.WANDERLUST_16X16,
                componentStyleSet = ComponentStyleSet.defaultStyleSet())
    }
}
