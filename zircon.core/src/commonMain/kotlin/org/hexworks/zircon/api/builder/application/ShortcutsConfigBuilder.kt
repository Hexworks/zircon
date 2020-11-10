package org.hexworks.zircon.api.builder.application

import org.hexworks.zircon.api.application.ShortcutsConfig
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEventMatcher
import org.hexworks.zircon.api.uievent.KeyboardEventType
import kotlin.jvm.JvmStatic

/**
 * [Builder] for creating [ShortcutsConfig]s.
 */
@Suppress("KDocUnresolvedReference")
data class ShortcutsConfigBuilder(
        /**
         * Default is `<Space>` press.
         */
        var activateFocused: KeyboardEventMatcher = prototype.activateFocused,
        /**
         * Default is `<Space>` release.
         */
        var deactivateActivated: KeyboardEventMatcher = prototype.deactivateActivated,
        /**
         * Default is `<Tab>` press.
         */
        var focusNext: KeyboardEventMatcher = prototype.focusNext,
        /**
         * Default is `<Shift>`+`<Tab>` press.
         */
        var focusPrevious: KeyboardEventMatcher = prototype.focusPrevious
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

        private val prototype = ShortcutsConfig()

        @JvmStatic
        fun newBuilder() = ShortcutsConfigBuilder()

    }
}
