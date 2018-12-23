package org.hexworks.zircon.api.builder.component

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.modal.Modal
import org.hexworks.zircon.api.component.modal.ModalResult
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.modal.DefaultModal
import org.hexworks.zircon.internal.component.renderer.DefaultModalRenderer
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
data class ModalBuilder<T : ModalResult>(
        private var darkenPercent: Double = .5,
        private var centeredDialog: Boolean = true,
        private var contentComponent: Maybe<Component> = Maybe.empty(),
        private val commonComponentProperties: CommonComponentProperties<Modal<T>> = CommonComponentProperties(
                componentRenderer = DefaultModalRenderer()))
    : BaseComponentBuilder<Modal<T>, ModalBuilder<T>>(commonComponentProperties) {

    fun withParentSize(size: Size) = also {
        super.withSize(size)
    }

    @JvmOverloads
    fun withCenteredDialog(centeredDialog: Boolean = true) = also {
        this.centeredDialog = centeredDialog
    }

    fun withComponent(dialogComponent: Component) = also {
        this.contentComponent = Maybe.of(dialogComponent)
    }

    fun withDarkenPercent(percentage: Double) = also {
        this.darkenPercent = percentage
    }

    override fun withSize(size: Size): ModalBuilder<T> {
        throw UnsupportedOperationException("Can't set the Size of a Modal by hand.")
    }

    override fun build(): Modal<T> {
        require(size.isUnknown().not()) {
            "Can't build a modal without knowing the size of the parent."
        }
        require(contentComponent.isPresent) {
            "Can't build a modal without a content component."
        }
        val component = contentComponent.get()
        require(component.size < size) {
            "Can't build a modal which has a component which is bigger than the modal."
        }
        if (centeredDialog) {
            component.moveTo(Position.create(
                    x = (size.width - component.size.width) / 2,
                    y = (size.height - component.size.height) / 2))
        }
        fillMissingValues()
        val componentRenderer = DefaultComponentRenderingStrategy(
                decorationRenderers = decorationRenderers,
                componentRenderer = commonComponentProperties.componentRenderer as ComponentRenderer<Modal<out ModalResult>>)
        val modal = DefaultModal<T>(
                darkenPercent = darkenPercent,
                componentMetadata = ComponentMetadata(
                        size = size,
                        position = fixPosition(size),
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                renderingStrategy = componentRenderer)
        modal.addComponent(component)
        return modal
    }

    override fun createCopy() = copy(commonComponentProperties = commonComponentProperties.copy())

    companion object {

        @JvmStatic
        fun <T : ModalResult> newBuilder() = ModalBuilder<T>()
    }
}
