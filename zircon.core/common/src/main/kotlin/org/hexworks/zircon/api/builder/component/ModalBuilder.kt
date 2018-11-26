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
        private var centeredDialog: Boolean = true,
        private var dialogComponent: Maybe<Component> = Maybe.empty(),
        private val commonComponentProperties: CommonComponentProperties<Modal<T>> = CommonComponentProperties(
                componentRenderer = DefaultModalRenderer()))
    : BaseComponentBuilder<Modal<T>, ModalBuilder<T>>(commonComponentProperties) {

    fun withTileGridSize(size: Size) = also {
        super.withSize(size)
    }

    @JvmOverloads
    fun withCenteredDialog(centeredDialog: Boolean = true) {
        this.centeredDialog = centeredDialog
    }

    fun withDialogComponent(dialogComponent: Component) = also {
        this.dialogComponent = Maybe.of(dialogComponent)
    }

    override fun withSize(size: Size): ModalBuilder<T> {
        throw UnsupportedOperationException("Can't set the Size of a Modal by hand.")
    }

    override fun build(): Modal<T> {
        require(size.isUnknown().not()) {
            "Can't build a modal without knowing the size of the parent TileGrid."
        }
        require(dialogComponent.isPresent) {
            "Can't build a modal without a dialog component."
        }
        val component = dialogComponent.get()
        require(component.size < size) {
            "Can't build a modal which has a dialog which is bigger than the modal."
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
