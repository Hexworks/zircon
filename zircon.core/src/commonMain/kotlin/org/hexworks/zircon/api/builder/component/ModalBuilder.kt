package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.modal.Modal
import org.hexworks.zircon.api.component.modal.ModalResult
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.modal.DefaultModal
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.DefaultModalRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmOverloads

@Suppress("UNCHECKED_CAST")
@ZirconDsl
class ModalBuilder<T : ModalResult> private constructor() :
    BaseComponentBuilder<Modal<T>, ModalBuilder<T>>(DefaultModalRenderer()) {

    var darkenPercent: Double = 0.0
    var centeredDialog: Boolean = false
    var contentComponent: Component? = null
    var onClosed: (T) -> Unit = {}

    private var shouldCloseWith: T? = null

    @JvmOverloads
    fun withCenteredDialog(centeredDialog: Boolean = true) = also {
        this.centeredDialog = centeredDialog
    }

    fun withComponent(dialogComponent: Component) = also {
        this.contentComponent = dialogComponent
    }

    fun withDarkenPercent(percentage: Double) = also {
        this.darkenPercent = percentage
    }

    fun withOnClosed(onClosed: (T) -> Unit) = also {
        this.onClosed = onClosed
    }

    fun close(result: T) {
        shouldCloseWith = result
    }

    @Deprecated("Use withPreferredSize instead", replaceWith = ReplaceWith("withPreferredSize(size)"))
    fun withParentSize(size: Size) = also {
        super.withPreferredSize(size)
    }

    override fun build(): Modal<T> {
        require(tileset.isNotUnknown) {
            "Since a Modal has no parent it must have its own tileset"
        }
        require(colorTheme.isNotUnknown || componentStyleSet.isNotUnknown) {
            "Since a Modal has no parent it must have its own color theme or component style set"
        }
        require(size.isNotUnknown) {
            "Can't build a modal without knowing the size of the parent."
        }
        require(contentComponent != null) {
            "Can't build a modal without a content component."
        }
        val component = contentComponent!!
        require(size.toRect().containsBoundable(component.rect)) {
            "The component $component doesn't fit within the modal of size $size."
        }
        if (centeredDialog) {
            component.moveTo(
                Position.create(
                    x = (size.width - component.size.width) / 2,
                    y = (size.height - component.size.height) / 2
                )
            )
        }
        val modalRenderer = DefaultComponentRenderingStrategy(
            decorationRenderers = decorations,
            componentRenderer = componentRenderer as ComponentRenderer<Modal<out ModalResult>>
        )
        val modal = DefaultModal<T>(
            darkenPercent = darkenPercent,
            componentMetadata = createMetadata(),
            renderingStrategy = modalRenderer
        )
        modal.addComponent(component)
        return modal.apply {
            onClosed(onClosed)
            shouldCloseWith?.let { result ->
                close(result)
            }
        }
    }

    override fun createCopy() = newBuilder<T>()
        .withProps(props.copy())
        .withCenteredDialog(centeredDialog).apply {
            contentComponent?.let { component ->
                withComponent(component)
            }
        }.withDarkenPercent(darkenPercent)
        .withPreferredSize(size)

    companion object {

        @JvmStatic
        fun <T : ModalResult> newBuilder() = ModalBuilder<T>()
    }
}
