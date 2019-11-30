package org.hexworks.zircon.api.screen

import org.hexworks.zircon.api.behavior.Themeable
import org.hexworks.zircon.api.builder.screen.ScreenBuilder
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentContainer
import org.hexworks.zircon.api.component.modal.Modal
import org.hexworks.zircon.api.component.modal.ModalFragment
import org.hexworks.zircon.api.component.modal.ModalResult
import org.hexworks.zircon.api.grid.TileGrid
import kotlin.jvm.JvmStatic

/**
 * A [Screen] is an in-memory representation of a [TileGrid] which can be displayed using an
 * actual [TileGrid]. **Careful!** Only one [Screen] can be `display`ed at a given time. If
 * [Screen.display] is called on a non-active [Screen] it will become active and the previous one
 * will be deactivated.
 *
 * Use [Screen]s to have multiple views for your app, which can be displayed when within your app.
 *
 * [Screen]s also implement the [ComponentContainer] interface which means that if you want to use
 * [Component]s you'll have to use [Screen]s.
 */
interface Screen
    : ComponentContainer, Themeable, TileGrid {

    /**
     * Moves the contents of this [Screen] to the underlying [TileGrid],
     * effectively displaying them on the user's screen.
     */
    fun display()

    // TODO: move this to a ModalHandler interface
    /**
     * Opens a new [Modal] window on top of the [Screen]. A modal
     * window blocks access to all [Component]s on this [Screen] and
     * also stops component events until the modal window is closed.
     * A [Modal] returns a [ModalResult] which represents the result
     * of opening the [Modal] itself.
     */
    fun <T : ModalResult> openModal(modal: Modal<T>)

    /**
     * Opens a new [Modal] window on top of the [Screen] using the given
     * [ModalFragment].
     * @see [Screen.openModal]
     */
    fun <T : ModalResult> openModal(modalFragment: ModalFragment<T>) = openModal(modalFragment.root)

    companion object {

        @JvmStatic
        fun create(tileGrid: TileGrid) = ScreenBuilder.createScreenFor(tileGrid)
    }
}
