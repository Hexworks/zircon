package org.hexworks.zircon.api.builder.application

import org.hexworks.zircon.api.application.ShortcutsConfig
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.uievent.KeyboardEventMatcher
import kotlin.jvm.JvmStatic

/**
 * [Builder] for creating [ShortcutsConfig]s.
 */
@Suppress("KDocUnresolvedReference")
data class ShortcutsConfigBuilder(
    private var shortcutsConfig: ShortcutsConfig = ShortcutsConfig.defaultConfig()
) : Builder<ShortcutsConfig> {

    fun withActivateFocused(activateFocused: KeyboardEventMatcher) = also {
        shortcutsConfig = shortcutsConfig.copy(activateFocused = activateFocused)
    }

    fun withDeactivateActivated(deactivateActivated: KeyboardEventMatcher) = also {
        shortcutsConfig = shortcutsConfig.copy(deactivateActivated = deactivateActivated)
    }

    fun withFocusNext(focusNext: KeyboardEventMatcher) = also {
        shortcutsConfig = shortcutsConfig.copy(focusNext = focusNext)
    }

    fun withFocusPrevious(focusPrevious: KeyboardEventMatcher) = also {
        shortcutsConfig = shortcutsConfig.copy(focusPrevious = focusPrevious)
    }

    override fun build() = shortcutsConfig

    override fun createCopy() = copy()

    companion object {

        @JvmStatic
        fun newBuilder() = ShortcutsConfigBuilder()

    }
}
