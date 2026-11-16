package org.hexworks.zircon.api.fragment

import org.hexworks.zircon.api.component.Fragment

/**
 * A retro window: a title bar with window controls, a header and a body.
 */
public interface Window : Fragment {
    public var title: String
    public var header: String
    public var body: String
}
