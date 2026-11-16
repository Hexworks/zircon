package org.hexworks.zircon.api.fragment

import org.hexworks.zircon.api.component.Fragment

/**
 * A message with a severity: a framed panel holding an icon and the wrapped text.
 */
public interface Alert : Fragment {
    public val kind: AlertKind
    public var text: String
}

public enum class AlertKind {
    INFO,
    SUCCESS,
    ERROR,
}
