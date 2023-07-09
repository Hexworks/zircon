package org.hexworks.zircon.api.builder.application

import org.hexworks.zircon.api.application.ShortcutsConfig
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEventMatcher
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.internal.dsl.ZirconDsl
import kotlin.jvm.JvmStatic

/**
 * [Builder] for creating [ShortcutsConfig]s.
 */
@ZirconDsl
class ShortcutsConfigBuilder : Builder<ShortcutsConfig> {

    /**
     * Default is `<Space>` press.
     */
    var activateFocused: KeyboardEventMatcher = KeyboardEventMatcher(
        type = KeyboardEventType.KEY_PRESSED,
        code = KeyCode.SPACE
    )

    /**
     * Default is `<Space>` release.
     */
    var deactivateActivated: KeyboardEventMatcher = KeyboardEventMatcher(
        type = KeyboardEventType.KEY_RELEASED,
        code = KeyCode.SPACE
    )

    /**
     * Default is `<Tab>` press.
     */
    var focusNext: KeyboardEventMatcher = KeyboardEventMatcher(
        type = KeyboardEventType.KEY_PRESSED,
        code = KeyCode.TAB,
        shiftDown = false
    )

    /**
     * Default is `<Shift>`+`<Tab>` press.
     */
    var focusPrevious: KeyboardEventMatcher = KeyboardEventMatcher(
        type = KeyboardEventType.KEY_PRESSED,
        code = KeyCode.TAB,
        shiftDown = true
    )

    override fun build() = ShortcutsConfig(
        activateFocused = activateFocused,
        deactivateActivated = deactivateActivated,
        focusNext = focusNext,
        focusPrevious = focusPrevious
    )
}
