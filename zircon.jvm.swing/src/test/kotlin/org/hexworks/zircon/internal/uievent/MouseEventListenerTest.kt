package org.hexworks.zircon.internal.uievent

import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.UIEvent
import org.junit.Before
import org.mockito.MockitoAnnotations
import java.util.*

@Suppress("DEPRECATION")
class MouseEventListenerTest {

    lateinit var target: MouseEventListener
    lateinit var operations: Map<(MouseEvent) -> Unit, MouseEventType>

    val inputs = LinkedList<UIEvent>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        target = MouseEventListener(FONT_SIZE, FONT_SIZE)
    }

    companion object {
        val FONT_SIZE = 16
    }
}
