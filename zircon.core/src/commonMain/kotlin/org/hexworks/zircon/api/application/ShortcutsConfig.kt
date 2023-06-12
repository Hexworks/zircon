package org.hexworks.zircon.api.application

import org.hexworks.zircon.api.builder.application.ShortcutsConfigBuilder
import org.hexworks.zircon.api.uievent.KeyboardEventMatcher

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

        fun newBuilder() = ShortcutsConfigBuilder.newBuilder()

        fun defaultConfig() = ShortcutsConfigBuilder.newBuilder().build()
    }
}
