package org.hexworks.zircon.internal.component.impl

import kotlinx.collections.immutable.persistentListOf
import org.hexworks.cobalt.databinding.api.binding.bindFlatten
import org.hexworks.cobalt.databinding.api.binding.bindMap
import org.hexworks.cobalt.databinding.api.collection.ObservableList
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.modal.Modal
import org.hexworks.zircon.api.component.modal.ModalResult
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.UIEvent
import org.hexworks.zircon.api.uievent.UIEventResponse
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalComponentContainer
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.RootContainerRenderer
import org.hexworks.zircon.internal.data.LayerState
import org.hexworks.zircon.internal.graphics.Renderable
import kotlin.jvm.Synchronized

/**
 * This [InternalComponentContainer] implements the logic of using Modal containers.
 * This means that it maintains a stack of modals on top of a [mainContainer], each blocking
 * all other containers below it. There can be only one container active at a given time.
 * If no modals are open, the [mainContainer] is active.
 */
class ModalComponentContainer(
        private val metadata: ComponentMetadata,
        private val mainContainer: InternalComponentContainer = buildContainer(
                metadata = metadata
        )
) : InternalComponentContainer {

    private val logger = LoggerFactory.getLogger(this::class)
    private val containerStack = persistentListOf<InternalComponentContainer>().toProperty()
    private val trees = containerStack.bindMap { it.flattenedTree }

    override val flattenedTree = trees.bindFlatten()

    override val renderables: ObservableList<out Renderable>
        get() = flattenedTree

    override val isActive = mainContainer.isActive.value.toProperty()

    override var theme: ColorTheme
        get() = mainContainer.theme
        set(value) {
            mainContainer.theme = value
        }

    override val themeProperty: Property<ColorTheme>
        get() = mainContainer.themeProperty

    init {
        containerStack.add(mainContainer)
    }

    fun isMainContainerActive() = mainContainer.isActive.value

    @Synchronized
    override fun dispatch(event: UIEvent): UIEventResponse {
        return containerStack.lastOrNull()?.dispatch(event) ?: Pass
    }

    @Synchronized
    override fun activate() {
        mainContainer.activate()
        updateIsActive()
    }

    @Synchronized
    override fun deactivate() {
        containerStack.forEach {
            it.deactivate()
        }
        containerStack.clear()
        containerStack.add(mainContainer)
        updateIsActive()
    }

    @Synchronized
    override fun addComponent(component: Component): AttachedComponent {
        return mainContainer.addComponent(component)
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
            updateIsActive()
        }
        modalContainer.activate()
        modalContainer.addComponent(modal)
        modal.requestFocus()
        updateIsActive()
    }

    private fun updateIsActive() {
        isActive.value = containerStack.any { it.isActive.value }
    }

    private fun List<InternalComponentContainer>.fetchLast(): InternalComponentContainer {
        return lastOrNull() ?: run {
            logger.warn("No containers were present in container stack. Re-adding main container.")
            containerStack.add(mainContainer)
            isActive.value = mainContainer.isActive.value
            return mainContainer
        }
    }

    companion object {

        private fun buildContainer(metadata: ComponentMetadata): InternalComponentContainer {
            val renderingStrategy = DefaultComponentRenderingStrategy(
                    decorationRenderers = listOf(),
                    componentRenderer = RootContainerRenderer()
            )
            val container = DefaultComponentContainer(
                    root = DefaultRootContainer(
                            componentMetadata = metadata,
                            renderingStrategy = renderingStrategy)
            )
            container.theme = ColorThemes.empty()
            return container
        }
    }
}
