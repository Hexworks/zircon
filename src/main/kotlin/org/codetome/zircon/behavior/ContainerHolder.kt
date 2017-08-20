package org.codetome.zircon.behavior

import org.codetome.zircon.component.Container
import org.codetome.zircon.graphics.image.TextImage
import org.codetome.zircon.input.InputProvider

/**
 * Represents an object which can holds gui [org.codetome.zircon.component.Component]s.
 * **Note that** a [ContainerHolder] **will always** hold a [Container]
 * which will have the [org.codetome.zircon.Size] of its parent.
 * @see org.codetome.zircon.component.Component for more info
 */
interface ContainerHolder {

    /**
     * Returns the [Container] this [ContainerHolder] is holding.
     */
    fun getContainer(): Container

    /**
     * Sets a new [Container] for this [ContainerHolder].
     */
    fun setContainer(container: Container)

    /**
     * Creates a [TextImage] of the current contents of the [Container]
     * which this [ContainerHolder] is holding.
     */
    fun drawComponentsToImage(): TextImage

    /**
     * Sets an [InputProvider] from which the components will receive
     * events.
     */
    fun setInputProvider(inputProvider: InputProvider)
}