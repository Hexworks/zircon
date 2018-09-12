package org.hexworks.zircon.api.component.renderer.impl

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.resource.CP437TilesetResource
import org.hexworks.zircon.internal.component.impl.DefaultButton
import org.hexworks.zircon.internal.component.renderer.DefaultButtonRenderer
import org.hexworks.zircon.platform.factory.ThreadSafeQueueFactory
import org.junit.Before
import org.junit.Test

class DefaultComponentRenderingStrategyTest {

    lateinit var target: DefaultComponentRenderingStrategy<Button>

    @Before
    fun setUp() {
        target = DefaultComponentRenderingStrategy(
                decorationRenderers = listOf(
                        ShadowDecorationRenderer(),
                        BoxDecorationRenderer()),
                componentRenderer = DefaultButtonRenderer())
    }

    @Test
    fun test() {

        val size = Sizes.create(5, 5)
        val graphics = TileGraphicsBuilder.newBuilder()
                .size(size)
                .build()

        val button = DefaultButton(
                text = "foo",
                initialTileset = CP437TilesetResources.aduDhabi16x16(),
                wrappers = ThreadSafeQueueFactory.create(),
                initialSize = size,
                position = Position.defaultPosition(),
                componentStyleSet = ComponentStyleSet.defaultStyleSet())

        target.apply(button, graphics)

        println(graphics)
    }
}
