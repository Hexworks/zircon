package org.hexworks.zircon.internal.component.modal


import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.modal.Modal
import org.hexworks.zircon.api.component.modal.ModalResult
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.internal.behavior.Observable
import org.hexworks.zircon.internal.behavior.impl.DefaultObservable
import org.hexworks.zircon.internal.component.impl.DefaultContainer
import kotlin.jvm.Synchronized

open class DefaultModal<T : ModalResult>(
    componentMetadata: ComponentMetadata,
    override val darkenPercent: Double,
    renderingStrategy: ComponentRenderingStrategy<Modal<out ModalResult>>
) : Modal<T>, DefaultContainer(
    metadata = componentMetadata,
    renderer = renderingStrategy
), Observable<T> by DefaultObservable() {

    final override var closed = false
        private set

    @Synchronized
    override fun close(result: T) {
        if (closed.not()) {
            closed = true
            notifyObservers(result)
        }
    }

    override fun onClosed(fn: (T) -> Unit) {
        addObserver(fn)
    }

    override fun acceptsFocus() = true

    override fun convertColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return ComponentStyleSetBuilder.newBuilder()
            .withDefaultStyle(
                StyleSetBuilder.newBuilder()
                    .withForegroundColor(colorTheme.secondaryForegroundColor)
                    .withBackgroundColor(colorTheme.primaryBackgroundColor)
                    .build()
            )
            .build()
    }
}
