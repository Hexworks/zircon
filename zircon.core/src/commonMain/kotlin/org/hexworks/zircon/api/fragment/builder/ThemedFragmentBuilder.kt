package org.hexworks.zircon.api.fragment.builder

import org.hexworks.zircon.api.component.theme.ComponentFactory
import org.hexworks.zircon.api.component.theme.ComponentTheme

/**
 * A fragment builder that builds its parts for a [ComponentTheme]. [theme] is set by the
 * [ComponentFactory] (`null` without one); [components] is the factory to build the parts with.
 */
public interface ThemedFragmentBuilder<T> : FragmentBuilder<T> {

    public var theme: ComponentTheme?

    public val components: ComponentFactory
        get() = ComponentFactory { theme }
}
