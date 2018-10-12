package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.junit.Before

class DefaultTextBoxTest : ComponentImplementationTest<DefaultTextBox>() {

    override lateinit var target: DefaultTextBox

    override val expectedComponentStyles: ComponentStyleSet
        get() = ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(DEFAULT_THEME.secondaryForegroundColor)
                        .withBackgroundColor(TileColor.transparent())
                        .build())
                .build()

    @Before
    override fun setUp() {
        rendererStub = ComponentRendererStub()
        componentStub = ComponentStub(Position.create(1, 1), Size.create(2, 2))
        target = DefaultTextBox(
                componentMetadata = COMMON_COMPONENT_METADATA,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        componentRenderer = rendererStub))
    }

}
