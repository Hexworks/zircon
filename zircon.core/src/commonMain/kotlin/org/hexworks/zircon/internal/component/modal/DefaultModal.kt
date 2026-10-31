package org.hexworks.zircon.internal.component.modal


import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.modal.Modal
import org.hexworks.zircon.api.component.modal.ModalResult
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.behavior.Observable
import org.hexworks.zircon.internal.behavior.impl.DefaultObservable
import org.hexworks.zircon.internal.component.impl.DefaultContainer

public open class DefaultModal<T : ModalResult> internal constructor(
    componentMetadata: ComponentMetadata,
    override val darkenPercent: Double,
    renderingStrategy: ComponentRenderingStrategy<Modal<out ModalResult>>
) : Modal<T>, DefaultContainer(
    metadata = componentMetadata,
    renderer = renderingStrategy
), Observable<T> by DefaultObservable() {

    final override var closed: Boolean = false
        private set

    /**
     * What the screen showed when this modal was opened, cropped to the modal's bounds and
     * darkened by [darkenPercent]; drawn behind the modal's content. Set by the screen that
     * opens the modal, `null` until then.
     */
    internal var backdrop: TileGraphics? = null

    override fun close(result: T) {
        if (closed.not()) {
            closed = true
            notifyObservers(result)
        }
    }

    override fun onClosed(fn: (T) -> Unit) {
        addObserver(fn)
    }

    override fun acceptsFocus(): Boolean = true

    override fun convertColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return componentStyleSet {
            defaultStyle = styleSet {
                foregroundColor = colorTheme.secondaryForegroundColor
                backgroundColor = colorTheme.primaryBackgroundColor
            }
        }
    }
}
