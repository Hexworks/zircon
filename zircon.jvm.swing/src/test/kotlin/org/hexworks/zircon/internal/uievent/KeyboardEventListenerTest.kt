package org.hexworks.zircon.internal.uievent

import org.hexworks.zircon.api.uievent.UIEvent
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.*

class KeyboardEventListenerTest {

    lateinit var target: KeyboardEventListener

    @Mock
    lateinit var tileGridMock: InternalTileGrid

    val inputs = LinkedList<UIEvent>()

    @Before
    fun setUp() {
        //TODO ;
        MockitoAnnotations.initMocks(this)
        target = KeyboardEventListener(tileGridMock)
    }

    companion object {
        val FONT_SIZE = 16
    }
}
