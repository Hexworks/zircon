package org.hexworks.zircon.internal.uievent.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.internal.behavior.ComponentFocusOrderList
import org.hexworks.zircon.internal.component.InternalContainer
import org.hexworks.zircon.internal.component.impl.RootContainer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.*
import org.mockito.quality.Strictness
import kotlin.contracts.ExperimentalContracts

@Suppress("TestFunctionName")
@ExperimentalContracts
class UIEventToComponentDispatcherTest {
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS)

    lateinit var target: UIEventToComponentDispatcher

    @Mock
    lateinit var rootMock: RootContainer

    @Mock
    lateinit var child0Mock: InternalContainer

    @Mock
    lateinit var child1Mock: InternalContainer

    @Mock
    lateinit var focusOrderListMock: ComponentFocusOrderList

    @Before
    fun setUp() {
        whenever(rootMock.calculatePathTo(anyOrNull())).thenReturn(listOf(rootMock))
        target = UIEventToComponentDispatcher(rootMock, focusOrderListMock)
    }

    @Test
    fun dispatchShouldReturnPassWhenThereIsNoTarget() {
        whenever(focusOrderListMock.focusedComponent).thenReturn(rootMock)

        whenever(rootMock.process(KEY_A_PRESSED_EVENT, UIEventPhase.TARGET)).thenReturn(Pass)
        whenever(rootMock.keyPressed(KEY_A_PRESSED_EVENT, UIEventPhase.TARGET)).thenReturn(Pass)

        val result = target.dispatch(KEY_A_PRESSED_EVENT)

        assertThat(result).isEqualTo(Pass)
    }

    @Test
    fun dispatchShouldReturnProcessedWhenTargetsDefaultIsRun() {

        whenever(focusOrderListMock.focusedComponent).thenReturn(rootMock)
        whenever(rootMock.process(KEY_A_PRESSED_EVENT, UIEventPhase.TARGET)).thenReturn(Pass)
        whenever(rootMock.keyPressed(KEY_A_PRESSED_EVENT, UIEventPhase.TARGET)).thenReturn(Processed)

        val result = target.dispatch(KEY_A_PRESSED_EVENT)

        assertThat(result).isEqualTo(Processed)
    }

    @Test
    fun dispatchShouldReturnPreventDefaultWhenChildPreventedDefault() {

        whenever(focusOrderListMock.focusedComponent).thenReturn(child1Mock)

        whenever(rootMock.calculatePathTo(child1Mock)).thenReturn(listOf(rootMock, child0Mock, child1Mock))

        whenever(rootMock.process(KEY_A_PRESSED_EVENT, UIEventPhase.CAPTURE)).thenReturn(Pass)
        whenever(rootMock.keyPressed(KEY_A_PRESSED_EVENT, UIEventPhase.CAPTURE)).thenReturn(Pass)

        whenever(child0Mock.process(KEY_A_PRESSED_EVENT, UIEventPhase.CAPTURE)).thenReturn(PreventDefault)

        whenever(child1Mock.process(KEY_A_PRESSED_EVENT, UIEventPhase.TARGET)).thenReturn(Pass)


        whenever(child0Mock.process(KEY_A_PRESSED_EVENT, UIEventPhase.BUBBLE)).thenReturn(Pass)
        whenever(rootMock.process(KEY_A_PRESSED_EVENT, UIEventPhase.BUBBLE)).thenReturn(Pass)

        val result = target.dispatch(KEY_A_PRESSED_EVENT)

        // Child mock 0 shouldn't be called with key pressed in the capture phase
        verify(child0Mock, never()).keyPressed(KEY_A_PRESSED_EVENT, UIEventPhase.CAPTURE)
        // Child mock 1 shouldn't be called with key pressed in the target phase
        verify(child1Mock, never()).keyPressed(KEY_A_PRESSED_EVENT, UIEventPhase.TARGET)
        // Root mock shouldn't be called with key pressed in the bubble phase
        verify(rootMock, never()).keyPressed(KEY_A_PRESSED_EVENT, UIEventPhase.BUBBLE)

        assertThat(result).isEqualTo(PreventDefault)
    }

    @Test
    fun dispatchShouldReturnStopPropagationWhenFirstChildStoppedPropagation() {

        whenever(focusOrderListMock.focusedComponent).thenReturn(child1Mock)
        whenever(rootMock.calculatePathTo(child1Mock)).thenReturn(listOf(rootMock, child0Mock, child1Mock))

        whenever(rootMock.process(KEY_A_PRESSED_EVENT, UIEventPhase.CAPTURE)).thenReturn(Pass)
        whenever(rootMock.keyPressed(KEY_A_PRESSED_EVENT, UIEventPhase.CAPTURE)).thenReturn(Pass)

        whenever(child0Mock.process(KEY_A_PRESSED_EVENT, UIEventPhase.CAPTURE)).thenReturn(StopPropagation)


        val result = target.dispatch(KEY_A_PRESSED_EVENT)

        // Child mock 1 shouldn't be called with process in the target phase
        verify(child1Mock, never()).keyPressed(KEY_A_PRESSED_EVENT, UIEventPhase.TARGET)
        // Child mock 0 shouldn't be called with process in the bubble phase
        verify(child0Mock, never()).keyPressed(KEY_A_PRESSED_EVENT, UIEventPhase.BUBBLE)
        // Root mock shouldn't be called with process in the bubble phase
        verify(rootMock, never()).keyPressed(KEY_A_PRESSED_EVENT, UIEventPhase.BUBBLE)

        assertThat(result).isEqualTo(StopPropagation)
    }

    @Test
    fun When_a_child_stops_propagation_of_the_tab_key_Then_component_events_shouldnt_be_performed() {

        whenever(focusOrderListMock.focusedComponent).thenReturn(child0Mock)
        whenever(rootMock.calculatePathTo(child0Mock)).thenReturn(listOf(rootMock, child0Mock))

        whenever(rootMock.process(TAB_PRESSED_EVENT, UIEventPhase.CAPTURE)).thenReturn(Pass)
        whenever(rootMock.keyPressed(TAB_PRESSED_EVENT, UIEventPhase.CAPTURE)).thenReturn(Pass)

        whenever(child0Mock.process(TAB_PRESSED_EVENT, UIEventPhase.TARGET)).thenReturn(StopPropagation)

        val result = target.dispatch(TAB_PRESSED_EVENT)

        verify(child0Mock, never()).focusGiven()

        assertThat(result).isEqualTo(StopPropagation)
    }


    companion object {
        val KEY_A_PRESSED_EVENT = KeyboardEvent(
                type = KeyboardEventType.KEY_PRESSED,
                key = "a",
                code = KeyCode.KEY_A)

        val TAB_PRESSED_EVENT = KeyboardEvent(
                type = KeyboardEventType.KEY_PRESSED,
                code = KeyCode.TAB,
                key = "\t")
    }
}
