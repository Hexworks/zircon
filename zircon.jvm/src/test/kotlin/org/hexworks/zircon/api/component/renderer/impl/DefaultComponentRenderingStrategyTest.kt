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
    fun `Should render button with all possible decorations`() {
        val size = Sizes.create(8, 4)
        val graphics = TileGraphicsBuilder.newBuilder()
                .size(size)
                .build()
                .fill(Tile.defaultTile().withCharacter('_'))

        val target = DefaultComponentRenderingStrategy(
                decorationRenderers = listOf(
                        ShadowDecorationRenderer(),
                        BoxDecorationRenderer(),
                        ButtonSideDecorationRenderer()),
                componentRenderer = DefaultButtonRenderer())

        val btn = DefaultButton(
                text = "qux",
                tileset = CP437TilesetResources.aduDhabi16x16(),
                size = size,
                position = Position.defaultPosition(),
                componentStyleSet = ComponentStyleSet.defaultStyleSet(),
                renderingStrategy = target)

        target.render(btn, graphics)

        assertThat(graphics.fetchCells().map { it.tile }.map { it.asCharacterTile().get().character }).containsExactly(
                '┌', '─', '─', '─', '─', '─', '┐', '_',
                '│', '[', 'q', 'u', 'x', ']', '│', '░',
                '└', '─', '─', '─', '─', '─', '┘', '░',
                '_', '░', '░', '░', '░', '░', '░', '░')

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
                tileset = CP437TilesetResources.aduDhabi16x16(),
                size = size,
                position = Position.defaultPosition(),
                componentStyleSet = ComponentStyleSet.defaultStyleSet(),
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = listOf(),
                        componentRenderer = DefaultLabelRenderer()))

        val target = DefaultComponentRenderingStrategy(
                decorationRenderers = listOf(),
                componentRenderer = DefaultLabelRenderer())

        target.render(label, graphics)

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
                tileset = CP437TilesetResources.aduDhabi16x16(),
                size = size,
                position = Position.defaultPosition(),
                componentStyleSet = ComponentStyleSet.defaultStyleSet(),
                renderingStrategy = target)

        target.render(button, graphics)

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
                tileset = CP437TilesetResources.aduDhabi16x16(),
                size = size,
                position = Position.defaultPosition(),
                componentStyleSet = ComponentStyleSet.defaultStyleSet(),
                renderingStrategy = target)

        target.render(button, graphics)

        println(graphics)

        assertThat(graphics.fetchCells().map { it.tile }.map { it.asCharacterTile().get().character }).containsExactly(
                '┌', '─', '┐', ' ',
                '│', 'b', '│', '░',
                '└', '─', '┘', '░',
                ' ', '░', '░', '░')
    }
}
