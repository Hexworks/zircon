package org.hexworks.zircon.api.component

/**
 * Represents an object which can hold gui [Component]s.
 * **Note that** a [ComponentContainer] **will always** hold a "root" [Container]
 * which will have the [org.hexworks.zircon.api.data.Size] of its parent.
 * @see Component for more info
 */
interface ComponentContainer {

    /**
     * Adds a child [Component] to this [Container]. It can either be
     * a leaf component (like a label) or another container which can itself
     * contain components within itself.
     */
    fun addComponent(component: Component)

    /**
     * Removes the given [Component] from this [Container].
     * *Note that* this function is applied recursively until
     * it either traverses the whole component tree or finds
     * the component to remove.
     * @return `true` if change happened, `false` if not
     */
    fun removeComponent(component: Component): Boolean

    /**
     * Applies the [ColorTheme] to this component and recursively to all its children (if any).
     */
    fun applyColorTheme(colorTheme: ColorTheme)

}
