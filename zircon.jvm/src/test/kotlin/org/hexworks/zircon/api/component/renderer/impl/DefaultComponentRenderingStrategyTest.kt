package org.hexworks.zircon.api.component.renderer.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.component.impl.DefaultButton
import org.hexworks.zircon.internal.component.impl.DefaultLabel
import org.hexworks.zircon.internal.component.renderer.DefaultButtonRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultLabelRenderer
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
    fun `Should properly render component without decorations`() {
        val size = Sizes.create(5, 5)
        val graphics = TileGraphicsBuilder.newBuilder()
                .size(size)
                .build()
                .fill(Tile.defaultTile().withCharacter('_'))

        val label = DefaultLabel(
                text = "Long text",
                initialTileset = CP437TilesetResources.aduDhabi16x16(),
                initialSize = size,
                position = Position.defaultPosition(),
                componentStyleSet = ComponentStyleSet.defaultStyleSet())

        val target = DefaultComponentRenderingStrategy(
                decorationRenderers = listOf(),
                componentRenderer = DefaultLabelRenderer())

        target.apply(label, graphics)

        println(graphics)
    }

    @Test
    fun `Should properly render decorations and the component on a filled TileGraphics`() {

        val size = Sizes.create(5, 5)
        val graphics = TileGraphicsBuilder.newBuilder()
                .size(size)
                .build()
                .fill(Tile.defaultTile().withCharacter('_'))

        val button = DefaultButton(
                text = "foo",
                initialTileset = CP437TilesetResources.aduDhabi16x16(),
                wrappers = ThreadSafeQueueFactory.create(),
                initialSize = size,
                position = Position.defaultPosition(),
                componentStyleSet = ComponentStyleSet.defaultStyleSet())

        target.apply(button, graphics)

        println(graphics)

        assertThat(graphics.fetchCells().map { it.tile }.map { it.asCharacterTile().get().character }).containsExactly(
                '┌', '─', '─', '┐', '_',
                '│', 'f', 'o', '│', '░',
                '│', '_', '_', '│', '░',
                '└', '─', '─', '┘', '░',
                '_', '░', '░', '░', '░')
    }

    @Test
    fun `Should properly render decorations and the component on a blank TileGraphics`() {

        val size = Sizes.create(4, 4)
        val graphics = TileGraphicsBuilder.newBuilder()
                .size(size)
                .build()

        val button = DefaultButton(
                text = "bar",
                initialTileset = CP437TilesetResources.aduDhabi16x16(),
                wrappers = ThreadSafeQueueFactory.create(),
                initialSize = size,
                position = Position.defaultPosition(),
                componentStyleSet = ComponentStyleSet.defaultStyleSet())

        target.apply(button, graphics)

        println(graphics)

        assertThat(graphics.fetchCells().map { it.tile }.map { it.asCharacterTile().get().character }).containsExactly(
                '┌', '─', '┐', ' ',
                '│', 'b', '│', '░',
                '└', '─', '┘', '░',
                ' ', '░', '░', '░')
    }
}
