package org.hexworks.zircon.api;

import org.hexworks.zircon.api.uievent.KeyboardEventType;
import org.junit.Test;

public class SomeTest {

    @Test
    public void test() {
        Components.button()
                .build()
                .onKeyboardEvent(KeyboardEventType.KEY_PRESSED, (event, phase) -> UIEventResponses.pass());
    }

}
