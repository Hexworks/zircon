package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.modal.Modal
import org.hexworks.zircon.api.component.modal.ModalResult
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.kotlin.onClosed
import org.hexworks.zircon.internal.component.InternalComponentContainer
import org.hexworks.zircon.platform.factory.ThreadSafeQueueFactory

/**
 * This [InternalComponentContainer] holds a "main" container which holds components
 * and a stack of modal containers which hold modals.
 */
class CompositeComponentContainer(private val mainContainer: InternalComponentContainer,
                                  private val modalContainer: InternalComponentContainer) : InternalComponentContainer {

    private var modalStack = ThreadSafeQueueFactory.create<Modal<out ModalResult>>()

    fun isMainContainerActive() = mainContainer.isActive()

    override fun isActive(): Boolean {
        return mainContainer.isActive()
    }

    override fun activate() {
        mainContainer.activate()
    }

    override fun deactivate() {
        mainContainer.deactivate()
        clearModals()
    }

    override fun toFlattenedLayers(): Iterable<Layer> {
        return mainContainer.toFlattenedLayers().plus(modalContainer.toFlattenedLayers())
    }

    override fun addComponent(component: Component) {
        mainContainer.addComponent(component)
    }

    override fun removeComponent(component: Component): Boolean {
        return mainContainer.removeComponent(component)
    }

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return mainContainer.applyColorTheme(colorTheme)
    }

    fun addModal(modal: Modal<out ModalResult>): Boolean {
        return if (modalStack.isNotEmpty()) {
            false
        } else {
            mainContainer.deactivate()
            modalContainer.activate()
            modalStack.add(modal)
            modalContainer.addComponent(modal)
            modal.onClosed {
                clearModals()
                mainContainer.activate()
            }
            true
        }
    }

    private fun clearModals() {
        modalStack.drainAll().forEach {
            modalContainer.removeComponent(it)
            modalContainer.deactivate()
        }
    }
}
