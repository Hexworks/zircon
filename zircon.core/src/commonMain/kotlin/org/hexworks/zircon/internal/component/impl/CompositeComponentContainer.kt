package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.modal.Modal
import org.hexworks.zircon.api.component.modal.ModalResult
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.onClosed
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.UIEvent
import org.hexworks.zircon.api.uievent.UIEventResponse
import org.hexworks.zircon.internal.component.InternalComponentContainer
import org.hexworks.zircon.internal.component.renderer.RootContainerRenderer
import org.hexworks.zircon.internal.resource.ColorThemeResource
import org.hexworks.zircon.platform.factory.ThreadSafeQueueFactory

/**
 * This [InternalComponentContainer] holds a "main" container which holds components
 * and a stack of modal containers which hold modals.
 */
// TODO: test me pls
class CompositeComponentContainer(private val metadata: ComponentMetadata,
                                  private val mainContainer: InternalComponentContainer = buildContainer(metadata)) : InternalComponentContainer {

    private val containerStack = ThreadSafeQueueFactory.create<InternalComponentContainer>()

    init {
        containerStack.add(mainContainer)
    }

    fun isMainContainerActive() = mainContainer.isActive()

    override fun dispatch(event: UIEvent): UIEventResponse {
        return containerStack.peekLast().map { it.dispatch(event) }.orElse(Pass)
    }

    override fun isActive(): Boolean {
        return containerStack.any(InternalComponentContainer::isActive)
    }

    override fun activate() {
        mainContainer.activate()
    }

    override fun deactivate() {
        containerStack.drainAll().forEach {
            it.deactivate()
        }
        containerStack.add(mainContainer)
    }

    override fun toFlattenedLayers(): Iterable<Layer> {
        return containerStack.flatMap { it.toFlattenedLayers() }
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

    fun addModal(modal: Modal<out ModalResult>) {
        containerStack.peekLast().map {
            it.deactivate()
        }
        val modalContainer = buildContainer(metadata)
        modalContainer.activate()
        containerStack.add(modalContainer)
        modalContainer.addComponent(modal)
        modal.onClosed {
            containerStack.pollLast().map {
                it.deactivate()
            }
            containerStack.peekLast().map {
                it.activate()
            }
        }
        modal.requestFocus()
    }

    companion object {

        private fun buildContainer(metadata: ComponentMetadata): InternalComponentContainer {
            val renderingStrategy = DefaultComponentRenderingStrategy(
                    decorationRenderers = listOf(),
                    componentRenderer = RootContainerRenderer())
            val container = DefaultComponentContainer(
                    root = RootContainer(
                            componentMetadata = metadata,
                            renderingStrategy = renderingStrategy))
            container.applyColorTheme(ColorThemeResource.EMPTY.getTheme())
            return container
        }
    }
}
