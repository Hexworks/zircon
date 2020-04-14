package org.hexworks.zircon.internal.behavior.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.internal.behavior.InternalLayerable
import org.hexworks.zircon.internal.component.InternalComponentContainer
import org.hexworks.zircon.internal.data.LayerState
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@Suppress("TestFunctionName")
class ComponentsLayerableTest {

    lateinit var target: ComponentsLayerable

    @Mock
    lateinit var componentContainerMock: InternalComponentContainer

    @Mock
    lateinit var layerableMock: InternalLayerable

    @Mock
    lateinit var componentLayerStateMock0: LayerState

    @Mock
    lateinit var componentLayerStateMock1: LayerState

    @Mock
    lateinit var layerableLayerStateMock0: LayerState

    @Mock
    lateinit var layerableLayerStateMock1: LayerState


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        target = ComponentsLayerable(
                componentContainer = componentContainerMock,
                layerable = layerableMock)
    }

    @Test
    fun Given_a_components_layerable_When_fetching_its_layers_Then_they_are_returned_in_the_proper_order() {

        val componentLayers = listOf(componentLayerStateMock0, componentLayerStateMock1)
        val layerableLayers = listOf(layerableLayerStateMock0, layerableLayerStateMock1)

        Mockito.`when`(componentContainerMock.fetchLayerStates()).thenReturn(componentLayers.asSequence())
        Mockito.`when`(layerableMock.fetchLayerStates()).thenReturn(layerableLayers.asSequence())

        assertThat(target.fetchLayerStates().toList()).containsExactlyElementsOf(componentLayers + layerableLayers)
    }

}
