package org.hexworks.zircon.api.application

import org.hexworks.zircon.api.builder.application.ShortcutsConfigBuilder
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEventMatcher
import org.hexworks.zircon.api.uievent.KeyboardEventType
import kotlin.jvm.JvmStatic

/**
 * These are the shortcuts that Zircon will use for component activation / deactivation
 * and focus handling (next / previous).
 */
class ShortcutsConfig internal constructor(
    /**
     * Default is `<Space>` press.
     */
    val activateFocused: KeyboardEventMatcher,
    /**
     * Default is `<Space>` release.
     */
    val deactivateActivated: KeyboardEventMatcher,
    /**
     * Default is `<Tab>` press.
     */
    val focusNext: KeyboardEventMatcher,
    /**
     * Default is `<Shift>`+`<Tab>` press.
     */
    val focusPrevious: KeyboardEventMatcher
) {
    companion object {

        @JvmStatic
        fun newBuilder() = ShortcutsConfigBuilder.newBuilder()

        @JvmStatic
        fun defaultConfig() = ShortcutsConfigBuilder.newBuilder().build()
    }
}
