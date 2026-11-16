package org.hexworks.zircon.api.fragment

import org.hexworks.zircon.api.component.Fragment

/**
 * A short label in a frame, with an optional status dot on the left and a dismiss button
 * on the right. Dismissing hides the badge and calls the `onDismissed` handlers.
 */
public interface Badge : Fragment {
    public var text: String
    public val status: BadgeStatus
    public val isDismissed: Boolean
    public fun onDismissed(fn: () -> Unit)
}

public enum class BadgeStatus {
    NONE,
    SUCCESS,
    ERROR,
}
