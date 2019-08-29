package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.modal.Modal
import org.hexworks.zircon.api.component.modal.ModalResult
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.LayerState
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.UIEvent
import org.hexworks.zircon.api.uievent.UIEventResponse
import org.hexworks.zircon.internal.component.InternalComponentContainer
import org.hexworks.zircon.internal.component.renderer.RootContainerRenderer
import org.hexworks.zircon.internal.resource.ColorThemeResource
import kotlin.jvm.Synchronized

/**
 * This [InternalComponentContainer] implements the logic of using Modal
 * containers. This means that it maintains a stack of modals on top of a
 * [mainContainer], each blocking all other containers below it. There can
 * be only one container active at a given time. If no modals are open, the
 * [mainContainer] is active.
 */
class ModalComponentContainer(
        private val metadata: ComponentMetadata,
        private val mainContainer: InternalComponentContainer = buildContainer(
                metadata = metadata))
    : InternalComponentContainer {

    override val layerStates: Iterable<LayerState>
        @Synchronized
        get() = containerStack.flatMap { it.layerStates }

    private val logger = LoggerFactory.getLogger(this::class)
    private val containerStack = mutableListOf<InternalComponentContainer>()

    init {
        containerStack.add(mainContainer)
    }

    fun isMainContainerActive() = mainContainer.isActive()

    @Synchronized
    override fun dispatch(event: UIEvent): UIEventResponse {
        return containerStack.lastOrNull()?.dispatch(event) ?: Pass
    }

    @Synchronized
    override fun isActive(): Boolean {
        return containerStack.any(InternalComponentContainer::isActive)
    }

    @Synchronized
    override fun activate() {
        mainContainer.activate()
    }

    @Synchronized
    override fun deactivate() {
        containerStack.forEach {
            it.deactivate()
        }
        containerStack.clear()
        containerStack.add(mainContainer)
    }

    @Synchronized
    override fun addComponent(component: Component) {
        mainContainer.addComponent(component)
    }

    @Synchronized
    override fun removeComponent(component: Component): Boolean {
        return mainContainer.removeComponent(component)
    }

    @Synchronized
    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return mainContainer.applyColorTheme(colorTheme)
    }

    @Synchronized
    fun addModal(modal: Modal<out ModalResult>) {
        val previousContainer = containerStack.fetchLast()
        previousContainer.deactivate()
        val modalContainer = buildContainer(
                metadata = metadata)
        containerStack.add(modalContainer)
        modal.onClosed {
            modalContainer.deactivate()
            containerStack.remove(modalContainer)
            containerStack.fetchLast().activate()
        }
        modalContainer.activate()
        modalContainer.addComponent(modal)
        modal.requestFocus()
    }

    private fun List<InternalComponentContainer>.fetchLast(): InternalComponentContainer {
        return lastOrNull() ?: run {
            logger.warn("No containers were present in container stack. Re-adding main container.")
            containerStack.add(mainContainer)
            return mainContainer
        }
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
