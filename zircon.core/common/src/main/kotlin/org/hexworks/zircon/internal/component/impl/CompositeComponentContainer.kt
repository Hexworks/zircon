package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.modal.Modal
import org.hexworks.zircon.api.component.modal.ModalResult
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.kotlin.onClosed
import org.hexworks.zircon.internal.component.InternalComponentContainer
import org.hexworks.zircon.platform.factory.ThreadSafeQueueFactory

class CompositeComponentContainer(private val componentContainer: InternalComponentContainer,
                                  private val modalContainer: InternalComponentContainer) : InternalComponentContainer {

    private var modalStack = ThreadSafeQueueFactory.create<Modal<out ModalResult>>()
    private val logger = LoggerFactory.getLogger(this::class)

    override fun isActive(): Boolean {
        return componentContainer.isActive().or(modalContainer.isActive())
    }

    override fun activate() {
        componentContainer.activate()
    }

    override fun deactivate() {
        componentContainer.deactivate()
        clearModal()
    }

    override fun toFlattenedLayers(): Iterable<Layer> {
        return componentContainer.toFlattenedLayers().plus(modalContainer.toFlattenedLayers())
    }

    override fun addComponent(component: Component) {
        componentContainer.addComponent(component)
    }

    override fun removeComponent(component: Component): Boolean {
        return componentContainer.removeComponent(component)
    }

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return componentContainer.applyColorTheme(colorTheme)
    }

    fun addModal(modal: Modal<out ModalResult>): Boolean {
        return if (modalStack.isNotEmpty()) {
            false
        } else {
            componentContainer.deactivate()
            modalContainer.activate()
            modalStack.add(modal)
            modalContainer.addComponent(modal)
            modal.onClosed {
                clearModal()
                componentContainer.activate()
            }
            true
        }
    }

    private fun clearModal() {
        modalStack.drainAll().forEach {
            modalContainer.removeComponent(it)
            modalContainer.deactivate()
        }
    }
}
