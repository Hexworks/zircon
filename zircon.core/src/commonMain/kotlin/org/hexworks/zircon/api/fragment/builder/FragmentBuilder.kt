package org.hexworks.zircon.api.fragment.builder

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Position

/**
 * @param T type of the fragment that will be built
 */
interface FragmentBuilder<T> : Builder<T> {

    var position: Position

}
