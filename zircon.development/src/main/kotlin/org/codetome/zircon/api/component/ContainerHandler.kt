package org.codetome.zircon.api.component

/**
 * Represents an object which can hold gui [Component]s.
 * **Note that** a [ContainerHandler] **will always** hold a "root" [Container]
 * which will have the [org.codetome.zircon.api.data.Size] of its parent.
 * @see Component for more info
 */
interface ContainerHandler<T: Any, S: Any> {

    /**
     * Adds a child [Component] to this [Container]. It can either be
     * a leaf component (like a label) or another container which can itself
     * contain components within itself.
     */
    fun addComponent(component: Component<T, S>)

    /**
     * Removes the given [Component] from this [Container].
     * *Note that* this function is applied recursively until
     * it either traverses the whole component tree or finds
     * the component to remove.
     * @return `true` if change happened, `false` if not
     */
    fun removeComponent(component: Component<T, S>): Boolean

    /**
     * Applies the [ColorTheme] to this component and recursively to all its children (if any).
     */
    fun applyColorTheme(colorTheme: ColorTheme)

}
