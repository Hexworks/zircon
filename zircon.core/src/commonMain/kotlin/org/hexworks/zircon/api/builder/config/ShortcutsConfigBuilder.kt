package org.hexworks.zircon.api.builder.config

import org.hexworks.zircon.api.config.ShortcutsConfig
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEventMatcher
import org.hexworks.zircon.api.uievent.KeyboardEventType

/**
 * [Builder] for creating [ShortcutsConfig]s.
 */
@ZirconDsl
public class ShortcutsConfigBuilder : Builder<ShortcutsConfig> {

    /**
     * Default is `<Space>` press.
     */
    public var activateFocused: KeyboardEventMatcher = KeyboardEventMatcher(
        type = KeyboardEventType.KEY_PRESSED,
        code = KeyCode.SPACE
    )

    /**
     * Default is `<Space>` release.
     */
    public var deactivateActivated: KeyboardEventMatcher = KeyboardEventMatcher(
        type = KeyboardEventType.KEY_RELEASED,
        code = KeyCode.SPACE
    )

    /**
     * Default is `<Tab>` press.
     */
    public var focusNext: KeyboardEventMatcher = KeyboardEventMatcher(
        type = KeyboardEventType.KEY_PRESSED,
        code = KeyCode.TAB,
        shiftDown = false
    )

    /**
     * Default is `<Shift>`+`<Tab>` press.
     */
    public var focusPrevious: KeyboardEventMatcher = KeyboardEventMatcher(
        type = KeyboardEventType.KEY_PRESSED,
        code = KeyCode.TAB,
        shiftDown = true
    )

    override fun build(): ShortcutsConfig = ShortcutsConfig(
        activateFocused = activateFocused,
        deactivateActivated = deactivateActivated,
        focusNext = focusNext,
        focusPrevious = focusPrevious
    )
}