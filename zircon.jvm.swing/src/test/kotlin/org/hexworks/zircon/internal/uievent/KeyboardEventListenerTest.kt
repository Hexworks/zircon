package org.hexworks.zircon.internal.uievent

import org.hexworks.zircon.api.uievent.UIEvent
import org.junit.Before
import org.mockito.MockitoAnnotations
import java.util.*

class KeyboardEventListenerTest {

    lateinit var target: KeyboardEventListener

    val inputs = LinkedList<UIEvent>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        target = KeyboardEventListener()
    }

    companion object {
        val FONT_SIZE = 16
    }
}
