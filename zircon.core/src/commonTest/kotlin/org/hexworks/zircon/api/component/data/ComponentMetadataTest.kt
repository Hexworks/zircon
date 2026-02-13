package org.hexworks.zircon.api.component.data

import io.kotest.assertions.throwables.shouldThrow
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import kotlin.test.Test

class ComponentMetadataTest {

    @Test
    fun shouldThrowExceptionWhenCreatingAndPositionIsNegative() {
        shouldThrow<IllegalArgumentException> {
            ComponentMetadata(
                position = Position.create(-1, 0),
                size = Size.ZERO,
                tilesetProperty = BuiltInCP437TilesetResource.WANDERLUST_16X16.toProperty(),
                componentStyleSetProperty = ComponentStyleSet.DEFAULT_STYLE.toProperty()
            )
        }
    }
}
