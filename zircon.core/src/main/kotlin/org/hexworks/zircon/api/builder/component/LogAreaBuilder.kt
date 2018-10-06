package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.BaseComponentBuilder
import org.hexworks.zircon.api.component.CommonComponentProperties
import org.hexworks.zircon.api.component.LogArea
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultLogArea
import org.hexworks.zircon.internal.component.renderer.DefaultLogAreaRenderer

data class LogAreaBuilder(
        private var logRowHistorySize: Int = 100,
        private var delayInMsForTypewriterEffect: Int = 0,
        private val commonComponentProperties: CommonComponentProperties = CommonComponentProperties())
    : BaseComponentBuilder<LogArea, LogAreaBuilder>(commonComponentProperties) {


    override fun build(): LogArea {
        fillMissingValues()
        val size = decorationRenderers().asSequence()
                .map { it.occupiedSize }
                .fold(size, Size::plus)
        return DefaultLogArea(
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers(),
                        componentRenderer = DefaultLogAreaRenderer()),
                position = position,
                size = size,
                tileset = tileset(),
                componentStyleSet = componentStyleSet,
                delayInMsForTypewriterEffect = delayInMsForTypewriterEffect
        )
    }

    fun logRowHistorySize(numberOfRows: Int) = also {
        logRowHistorySize = numberOfRows
    }

    fun delayInMsForTypewriterEffect(delayInMs: Int) = also {
        delayInMsForTypewriterEffect = delayInMs
    }

    override fun createCopy() = copy()

    companion object {

        fun newBuilder() = LogAreaBuilder()
    }
}
