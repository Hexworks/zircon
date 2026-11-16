package org.hexworks.zircon.api.fragment

import org.hexworks.zircon.api.component.Fragment

/**
 * A framed block with a meta row (icon and subtitle, a date for example), a title and a body.
 */
public interface Card : Fragment {
    public var subtitle: String
    public var title: String
    public var body: String
}
