package org.hexworks.zircon.api.fragment.builder

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Position

/**
 * @param T type of the fragment that will be built
 */
public interface FragmentBuilder<T> : Builder<T> {

    public var position: Position

}

