package org.hexworks.zircon.internal.component.modal

import org.hexworks.cobalt.datatypes.sam.Consumer
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

open class DefaultModal<T : ModalResult>(componentMetadata: ComponentMetadata,
                                         override val darkenPercent: Double,
                                         private val renderingStrategy: ComponentRenderingStrategy<Modal<out ModalResult>>)
    : Modal<T>, DefaultContainer(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy), Observable<T> by DefaultObservable() {

    override fun close(result: T) {
        notifyObservers(result)
    }

    override fun onClosed(consumer: Consumer<T>) {
        addObserver(consumer)
    }

    init {
        render()
    }

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(StyleSetBuilder.newBuilder()
                        .withForegroundColor(colorTheme.secondaryForegroundColor)
                        .withBackgroundColor(colorTheme.primaryBackgroundColor)
                        .build())
                .build().also { css ->
                    componentStyleSet = css
                    render()
                    children.forEach {
                        it.applyColorTheme(colorTheme)
                    }
                }
    }

    final override fun render() {
        renderingStrategy.render(this, graphics)
    }
}
