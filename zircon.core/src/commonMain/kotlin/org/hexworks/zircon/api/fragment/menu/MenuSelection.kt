package org.hexworks.zircon.api.fragment.menu

import org.hexworks.zircon.api.component.modal.ModalResult

sealed class MenuSelection<out T : Any> : ModalResult

data class MenuItemSelected<T : Any>(
    val item: T,
) : MenuSelection<T>()

object SelectionCancelled : MenuSelection<Nothing>()