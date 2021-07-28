package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.DrawSurfaces
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.LogArea
import org.hexworks.zircon.api.component.Paragraph
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.DefaultLogAreaRenderer
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import org.junit.Before
import org.junit.Test

@Suppress("UNCHECKED_CAST")
class DefaultLogAreaTest : ComponentImplementationTest<DefaultLogArea>() {

    override lateinit var target: DefaultLogArea
    override lateinit var graphics: TileGraphics

    override val expectedComponentStyles: ComponentStyleSet
        get() = ComponentStyleSetBuilder.newBuilder()
            .withDefaultStyle(
                StyleSetBuilder.newBuilder()
                    .withForegroundColor(DEFAULT_THEME.secondaryForegroundColor)
                    .withBackgroundColor(DEFAULT_THEME.primaryBackgroundColor)
                    .build()
            )
            .withDisabledStyle(
                StyleSetBuilder.newBuilder()
                    .withForegroundColor(DEFAULT_THEME.secondaryForegroundColor)
                    .withBackgroundColor(DEFAULT_THEME.secondaryBackgroundColor)
                    .build()
            )
            .withFocusedStyle(
                StyleSetBuilder.newBuilder()
                    .withForegroundColor(DEFAULT_THEME.primaryBackgroundColor)
                    .withBackgroundColor(DEFAULT_THEME.primaryForegroundColor)
                    .build()
            )
            .build()

    @Before
    override fun setUp() {
        componentStub = ComponentStub(Position.create(1, 1), Size.create(2, 1))
        rendererStub = ComponentRendererStub(DefaultLogAreaRenderer())
        graphics = DrawSurfaces.tileGraphicsBuilder().withSize(SIZE_40x10).build()
        target = DefaultLogArea(
            componentMetadata = ComponentMetadata(
                size = SIZE_40x10,
                relativePosition = POSITION_4_5,
                componentStyleSetProperty = COMPONENT_STYLES.toProperty(),
                tilesetProperty = TILESET_REX_PAINT_20X20.toProperty()
            ),
            renderingStrategy = DefaultComponentRenderingStrategy(
                decorationRenderers = listOf(),
                componentRenderer = rendererStub as ComponentRenderer<LogArea>
            )
        )
    }

    @Test
    fun shouldProperlyAddNewRow() {
        target.addNewRows(1)
        assertThat(target.children.size).isEqualTo(1)
    }

    @Test
    fun shouldProperlyAddNewText() {
        target.addParagraph(TEXT)
        val child = target.children.first()
        assertThat((child.children.first() as Paragraph).text).isEqualTo(TEXT)
    }

    @Test
    fun shouldProperlyAddComponent() {
        val testComponent = testComponent()
        target.addInlineComponent(testComponent)
        target.commitInlineElements()
        val child = target.children.first()
        assertThat(target.children.size).isEqualTo(1)
        assertThat(child.children.first()).isSameAs(testComponent)
    }

    @Test
    fun shouldProperlyRemoveComponentIfItGetsDisposedDueToHistorySize() {
        target.addInlineComponent(testComponent())
        target.commitInlineElements()
        target.addNewRows(ROW_HISTORY_SIZE)
        target.addParagraph(TEXT)

        assertThat(target.children as Iterable<InternalComponent>).doesNotContain(testComponent())
    }


    @Test
    fun logElementShouldProperlyScrollDownIfNecessary() {
        target.addParagraph(TEXT)
        target.addNewRows(10)
        target.addParagraph(ALTERNATE_TEXT)
        val child = (target.children.first())
        assertThat(child.children).isEmpty() // this means it has no paragraph as a child
    }

    @Test
    fun shouldProperlyClearLogArea() {
        target.clear()

        assertThat(target.children).isEmpty()
    }

    companion object {
        val POSITION_4_5 = Position.create(4, 5)
        val SIZE_40x10 = Size.create(40, 10)
        const val ROW_HISTORY_SIZE = 15
        const val TEXT = "This is my log row"
        const val ALTERNATE_TEXT = "This is my other log row"

        fun testComponent() = Components.button()
            .withDecorations()
            .withTileset(BuiltInCP437TilesetResource.TAFFER_20X20)
            .withText("Button")
            .build() as InternalComponent
    }
}
