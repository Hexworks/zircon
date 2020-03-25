package org.hexworks.zircon.api.builder.application

import org.hexworks.zircon.api.application.ShortcutsConfig
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEventMatcher
import org.hexworks.zircon.api.uievent.KeyboardEventType
import kotlin.jvm.JvmStatic

@Suppress("KDocUnresolvedReference")
data class ShortcutsConfigBuilder(
        /**
         * Default is `[Space]` press.
         */
        var activateFocused: KeyboardEventMatcher = KeyboardEventMatcher(
                type = KeyboardEventType.KEY_PRESSED,
                code = KeyCode.SPACE),
        /**
         * Default is `[Space]` release.
         */
        var deactivateActivated: KeyboardEventMatcher = KeyboardEventMatcher(
                type = KeyboardEventType.KEY_RELEASED,
                code = KeyCode.SPACE),
        /**
         * Default is `[Tab]` press.
         */
        var focusNext: KeyboardEventMatcher = KeyboardEventMatcher(
                type = KeyboardEventType.KEY_PRESSED,
                code = KeyCode.TAB,
                shiftDown = false),
        /**
         * Default is `[Shift]+[Tab]` press.
         */
        var focusPrevious: KeyboardEventMatcher = KeyboardEventMatcher(
                type = KeyboardEventType.KEY_PRESSED,
                code = KeyCode.TAB,
                shiftDown = true)
) : Builder<ShortcutsConfig> {

    override fun build() = ShortcutsConfig(
            activateFocused = activateFocused,
            deactivateActivated = deactivateActivated,
            focusNext = focusNext,
            focusPrevious = focusPrevious)

    override fun createCopy() = copy()

    fun withActivateFocused(activateFocused: KeyboardEventMatcher) = also {
        this.activateFocused = activateFocused
    }

    fun withDeactivateActivated(deactivateActivated: KeyboardEventMatcher) = also {
        this.deactivateActivated = deactivateActivated
    }

    fun withFocusNext(focusNext: KeyboardEventMatcher) = also {
        this.focusNext = focusNext
    }

    fun withFocusPrevious(focusPrevious: KeyboardEventMatcher) = also {
        this.focusPrevious = focusPrevious
    }

    companion object {

        @JvmStatic
        fun newBuilder() = ShortcutsConfigBuilder()

    }
}
