package org.hexworks.zircon.internal.uievent.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.internal.behavior.ComponentFocusHandler
import org.hexworks.zircon.internal.component.InternalContainer
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations.*
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
class UIEventToComponentDispatcherTest {

    lateinit var target: UIEventToComponentDispatcher

    @Mock
    lateinit var rootMock: InternalContainer

    @Mock
    lateinit var child0Mock: InternalContainer

    @Mock
    lateinit var child1Mock: InternalContainer

    @Mock
    lateinit var focusHandlerMock: ComponentFocusHandler

    @Before
    fun setUp() {
        initMocks(this)
        Mockito.`when`(rootMock.calculatePathFromRoot()).thenReturn(listOf(rootMock))
        target = UIEventToComponentDispatcher(rootMock, focusHandlerMock)
    }

    @Test
    fun dispatchShouldReturnPassWhenThereIsNoTarget() {
        Mockito.`when`(focusHandlerMock.focusedComponent).thenReturn(rootMock)

        Mockito.`when`(rootMock.process(KEY_A_PRESSED_EVENT, UIEventPhase.TARGET)).thenReturn(Pass)
        Mockito.`when`(rootMock.keyPressed(KEY_A_PRESSED_EVENT, UIEventPhase.TARGET)).thenReturn(Pass)

        val result = target.dispatch(KEY_A_PRESSED_EVENT)

        assertThat(result).isEqualTo(Pass)
    }

    @Test
    fun dispatchShouldReturnProcessedWhenTargetsDefaultIsRun() {

        Mockito.`when`(focusHandlerMock.focusedComponent).thenReturn(rootMock)
        Mockito.`when`(rootMock.process(KEY_A_PRESSED_EVENT, UIEventPhase.TARGET)).thenReturn(Pass)
        Mockito.`when`(rootMock.keyPressed(KEY_A_PRESSED_EVENT, UIEventPhase.TARGET)).thenReturn(Processed)

        val result = target.dispatch(KEY_A_PRESSED_EVENT)

        assertThat(result).isEqualTo(Processed)
    }

    @Test
    fun dispatchShouldReturnPreventDefaultWhenChildPreventedDefault() {

        Mockito.`when`(focusHandlerMock.focusedComponent).thenReturn(child1Mock)

        Mockito.`when`(child1Mock.calculatePathFromRoot()).thenReturn(listOf(rootMock, child0Mock, child1Mock))

        Mockito.`when`(rootMock.process(KEY_A_PRESSED_EVENT, UIEventPhase.CAPTURE)).thenReturn(Pass)
        Mockito.`when`(rootMock.keyPressed(KEY_A_PRESSED_EVENT, UIEventPhase.CAPTURE)).thenReturn(Pass)

        Mockito.`when`(child0Mock.process(KEY_A_PRESSED_EVENT, UIEventPhase.CAPTURE)).thenReturn(PreventDefault)
        Mockito.`when`(child0Mock.keyPressed(KEY_A_PRESSED_EVENT, UIEventPhase.CAPTURE))
                .thenThrow(IllegalStateException("Child mock 0 shouldn't have been called with key pressed in the capture phase"))

        Mockito.`when`(child1Mock.process(KEY_A_PRESSED_EVENT, UIEventPhase.TARGET)).thenReturn(Pass)
        Mockito.`when`(child1Mock.keyPressed(KEY_A_PRESSED_EVENT, UIEventPhase.TARGET))
                .thenThrow(IllegalStateException("Child mock 1 shouldn't have been called with key pressed in the target phase"))


        Mockito.`when`(child0Mock.process(KEY_A_PRESSED_EVENT, UIEventPhase.BUBBLE)).thenReturn(Pass)
        Mockito.`when`(rootMock.process(KEY_A_PRESSED_EVENT, UIEventPhase.BUBBLE)).thenReturn(Pass)
        Mockito.`when`(rootMock.keyPressed(KEY_A_PRESSED_EVENT, UIEventPhase.BUBBLE))
                .thenThrow(IllegalStateException("Root mock shouldn't have been called with key pressed in the bubble phase"))

        val result = target.dispatch(KEY_A_PRESSED_EVENT)

        assertThat(result).isEqualTo(PreventDefault)
    }

    @Test
    fun dispatchShouldReturnStopPropagationWhenFirstChildStoppedPropagation() {

        Mockito.`when`(focusHandlerMock.focusedComponent).thenReturn(child1Mock)
        Mockito.`when`(child1Mock.calculatePathFromRoot()).thenReturn(listOf(rootMock, child0Mock, child1Mock))

        Mockito.`when`(rootMock.process(KEY_A_PRESSED_EVENT, UIEventPhase.CAPTURE)).thenReturn(Pass)
        Mockito.`when`(rootMock.keyPressed(KEY_A_PRESSED_EVENT, UIEventPhase.CAPTURE)).thenReturn(Pass)

        Mockito.`when`(child0Mock.process(KEY_A_PRESSED_EVENT, UIEventPhase.CAPTURE)).thenReturn(StopPropagation)

        Mockito.`when`(child1Mock.process(KEY_A_PRESSED_EVENT, UIEventPhase.TARGET))
                .thenThrow(IllegalStateException("Child mock 1 shouldn't have been called with process in the target phase"))
        Mockito.`when`(child0Mock.process(KEY_A_PRESSED_EVENT, UIEventPhase.BUBBLE))
                .thenThrow(IllegalStateException("Child mock 0 shouldn't have been called with process in the bubble phase"))
        Mockito.`when`(rootMock.process(KEY_A_PRESSED_EVENT, UIEventPhase.BUBBLE))
                .thenThrow(IllegalStateException("Root mock shouldn't have been called with process in the bubble phase"))


        val result = target.dispatch(KEY_A_PRESSED_EVENT)

        assertThat(result).isEqualTo(StopPropagation)
    }

    companion object {
        val KEY_A_PRESSED_EVENT = KeyboardEvent(
                type = KeyboardEventType.KEY_PRESSED,
                key = "a",
                code = KeyCode.KEY_A)
    }
}
