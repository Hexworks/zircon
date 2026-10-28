package org.hexworks.zircon.api.application

import org.hexworks.zircon.api.builder.application.ShortcutsConfigBuilder
import org.hexworks.zircon.api.uievent.KeyboardEventMatcher

/**
 * These are the shortcuts that Zircon will use for component activation / deactivation
 * and focus handling (next / previous).
 */
public class ShortcutsConfig internal constructor(
    /**
     * Default is `<Space>` press.
     */
    public val activateFocused: KeyboardEventMatcher,
    /**
     * Default is `<Space>` release.
     */
    public val deactivateActivated: KeyboardEventMatcher,
    /**
     * Default is `<Tab>` press.
     */
    public val focusNext: KeyboardEventMatcher,
    /**
     * Default is `<Shift>`+`<Tab>` press.
     */
    public val focusPrevious: KeyboardEventMatcher
) {
    public companion object {

        public val DEFAULT_SHORTCUTS_CONFIG: ShortcutsConfig = ShortcutsConfigBuilder().build()
    }
}
