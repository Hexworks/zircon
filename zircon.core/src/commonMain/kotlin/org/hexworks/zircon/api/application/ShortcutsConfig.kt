package org.hexworks.zircon.api.application

import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEventMatcher
import org.hexworks.zircon.api.uievent.KeyboardEventType
import kotlin.jvm.JvmStatic

/**
 * These are the shortcuts that Zircon will use for component activation / deactivation
 * and focus handling (next / previous).
 */
data class ShortcutsConfig(
        /**
         * Default is `<Space>` press.
         */
        val activateFocused: KeyboardEventMatcher = KeyboardEventMatcher(
                type = KeyboardEventType.KEY_PRESSED,
                code = KeyCode.SPACE
        ),
        /**
         * Default is `<Space>` release.
         */
        val deactivateActivated: KeyboardEventMatcher = KeyboardEventMatcher(
                type = KeyboardEventType.KEY_RELEASED,
                code = KeyCode.SPACE
        ),
        /**
         * Default is `<Tab>` press.
         */
        val focusNext: KeyboardEventMatcher = KeyboardEventMatcher(
                type = KeyboardEventType.KEY_PRESSED,
                code = KeyCode.TAB,
                shiftDown = false
        ),
        /**
         * Default is `<Shift>`+`<Tab>` press.
         */
        val focusPrevious: KeyboardEventMatcher = KeyboardEventMatcher(
                type = KeyboardEventType.KEY_PRESSED,
                code = KeyCode.TAB,
                shiftDown = true
        )
) {
    companion object {
        @JvmStatic
        fun defaultConfig() = ShortcutsConfig()
    }
}
