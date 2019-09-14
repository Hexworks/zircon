package org.hexworks.zircon.api.component

/**
 * Determines the Visibility of a component
 */
enum class Visibility {
    /**
     * Component is Visible
     */
    Visible,
    /**
     * Component is invisible but occupies the space
     */
    Hidden

    //Invisible...maybe an option for later (but makes only sense if a Layout Manager is present and
    //adapts the positions accordingly)
}
