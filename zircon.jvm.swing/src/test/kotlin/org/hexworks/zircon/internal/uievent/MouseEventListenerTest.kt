package org.hexworks.zircon.internal.uievent

import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.UIEvent
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.*

class MouseEventListenerTest {

    lateinit var target: MouseEventListener
    lateinit var operations: Map<(MouseEvent) -> Unit, MouseEventType>

    @Mock
    lateinit var tileGridMock: InternalTileGrid

    val inputs = LinkedList<UIEvent>()

    @Before
    fun setUp() {
        //TODO ;
        MockitoAnnotations.initMocks(this)
        target = MouseEventListener(FONT_SIZE, FONT_SIZE, tileGridMock)
    }

    companion object {
        val FONT_SIZE = 16
    }
}
