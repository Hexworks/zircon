package org.hexworks.zircon.api.fragment.menu

import org.hexworks.zircon.api.component.modal.ModalResult

public sealed class MenuSelection<out T : Any> : ModalResult

public data class MenuItemSelected<T : Any>(
    val item: T,
) : MenuSelection<T>()

public object SelectionCancelled : MenuSelection<Nothing>()