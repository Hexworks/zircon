package org.hexworks.zircon.api.fragment

import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.component.ProgressBar

/**
 * A [ProgressBar] with a label and the percentage above it.
 */
public interface ProgressField : Fragment {
    public val progressBar: ProgressBar
    public var label: String
    public var progress: Double
        get() = progressBar.progress
        set(value) {
            progressBar.progress = value
        }
}
