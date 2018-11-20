package org.hexworks.zircon.api.screen

import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentContainer
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.modal.Modal
import org.hexworks.zircon.api.component.modal.ModalResult
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.behavior.Identifiable
import org.hexworks.zircon.internal.component.modal.DefaultModal

/**
 * A [Screen] is an in-memory representation of a [TileGrid] which can be displayed using an
 * actual [TileGrid]. **Careful!** Only one [Screen] can be `display`ed at a given time. If
 * [Screen.display] is called on a non-active [Screen] it will become active and the previous one
 * will be deactivated.
 *
 * Use [Screen]s to have multiple views for your app, which can be displayed when within your app.
 *
 * [Screen]s also implement the [ComponentContainer] interface which means that if you want to use
 * [org.hexworks.zircon.api.component.Component]s you'll have to use [Screen]s.
 */
interface Screen
    : TileGrid, ComponentContainer, Identifiable {

    /**
     * Moves the contents of this [Screen] to the underlying [org.hexworks.zircon.api.grid.TileGrid],
     * effectively displaying them on the user's screen.
     */
    fun display()

    /**
     * Applies a [ColorTheme] to this component and recursively to all its children (if any).
     * @return the [ComponentStyleSet] which the [ColorTheme] was converted to
     */
    // TODO: move this to a Themeable interface
    fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet

    fun <T: ModalResult> addModal(modal: Modal<T>)

}
