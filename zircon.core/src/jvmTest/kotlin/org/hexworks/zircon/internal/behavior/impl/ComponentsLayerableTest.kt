package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.component.ComponentContainer
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.behavior.InternalLayerable
import org.hexworks.zircon.internal.component.InternalComponentContainer
import org.hexworks.zircon.internal.component.impl.DefaultComponentContainer
import org.hexworks.zircon.internal.component.impl.DefaultRootContainer
import org.hexworks.zircon.internal.component.impl.RootContainer
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.RootContainerRenderer
import org.hexworks.zircon.internal.graphics.Renderable
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals


@Suppress("TestFunctionName")
class ComponentsLayerableTest {

    lateinit var componentContainer: InternalComponentContainer
    lateinit var layerable: InternalLayerable
    lateinit var rootContainer: RootContainer
    lateinit var target: ComponentsLayerable

    @Before
    fun setUp() {
        rootContainer = DefaultRootContainer(
            componentMetadata = ComponentMetadata(
                relativePosition = Position.zero(),
                size = SIZE_4X2,
                tileset = CP437TilesetResources.bisasam16x16(),
                ComponentStyleSet.defaultStyleSet(),
                theme = ColorThemes.adriftInDreams()
            ),
            renderingStrategy = DefaultComponentRenderingStrategy(
                decorationRenderers = listOf(),
                componentRenderer = RootContainerRenderer()
            )
        )
        componentContainer = DefaultComponentContainer(rootContainer)
        layerable = ThreadSafeLayerable(SIZE_4X2)
        target = ComponentsLayerable(
            componentContainer = componentContainer,
            layerable = layerable
        )
    }

    @Test
    fun Given_a_components_layerable_When_fetching_its_layers_Then_they_are_returned_in_the_proper_order() {
        val layer = LayerBuilder.newBuilder().withSize(Size.create(1, 1)).build().asInternalLayer()
        layerable.addLayer(layer)

        assertEquals(
            expected = listOf<Renderable>(rootContainer, layer),
            actual = target.renderables.toList()
        )
    }

    companion object {
        val SIZE_4X2 = Size.create(4, 3)
    }

}
