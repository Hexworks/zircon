package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.component.modal.Modal
import org.hexworks.zircon.api.component.modal.ModalResult
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.dsl.buildChildFor
import org.hexworks.zircon.internal.component.modal.DefaultModal
import org.hexworks.zircon.internal.component.modal.EmptyModalResult
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.DefaultModalRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl
import kotlin.jvm.JvmName

@Suppress("UNCHECKED_CAST")
@ZirconDsl
class ModalBuilder<T : ModalResult> :
    BaseComponentBuilder<Modal<T>>(DefaultModalRenderer()) {

    var darkenPercent: Double = 0.0
    var centeredDialog: Boolean = false
    var contentComponent: Component? = null
    var onClosed: (T) -> Unit = {}

    private var shouldCloseWith: T? = null

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
            decorationRenderers = decorationRenderers,
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
}

/**
 * Creates a new [Modal] using the component builder DSL and returns it.
 */
fun <T : ModalResult> buildModal(init: ModalBuilder<T>.() -> Unit): Modal<T> =
    ModalBuilder<T>().apply(init).build()

/**
 * Creates a new [Modal] using the component builder DSL and returns it.
 */
@JvmName("buildModalWithNoResult")
fun buildModal(init: ModalBuilder<EmptyModalResult>.() -> Unit): Modal<EmptyModalResult> =
    ModalBuilder<EmptyModalResult>().apply(init).build()

/**
 * Creates a new [Modal] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [Modal].
 */
fun <T : BaseContainerBuilder<*>, M : ModalResult> T.modal(
    init: ModalBuilder<M>.() -> Unit
): Modal<M> = buildChildFor(this, ModalBuilder(), init)

/**
 * Creates a new [Modal] with [EmptyModalResult] using the component builder DSL,
 * adds it to the receiver [BaseContainerBuilder] it and returns the [Modal].
 */
@JvmName("modalWithNoResult")
fun <T : BaseContainerBuilder<*>> T.modal(
    init: ModalBuilder<EmptyModalResult>.() -> Unit
): Modal<EmptyModalResult> = buildChildFor(this, ModalBuilder(), init)
