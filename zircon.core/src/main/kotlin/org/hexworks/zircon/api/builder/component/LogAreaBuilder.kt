package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.BaseComponentBuilder
import org.hexworks.zircon.api.component.CommonComponentProperties
import org.hexworks.zircon.api.component.LogArea
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.TextWrap
import org.hexworks.zircon.internal.component.impl.log.DefaultLogArea
import org.hexworks.zircon.internal.component.renderer.DefaultLogAreaRenderer

data class LogAreaBuilder(
        private var textWrap: TextWrap = TextWrap.WORD_WRAP,
        private var logRowHistorySize: Int = 100,
        private val commonComponentProperties: CommonComponentProperties = CommonComponentProperties())
    : BaseComponentBuilder<LogArea, LogAreaBuilder>(commonComponentProperties) {


    override fun build(): LogArea {
        fillMissingValues()
        val size = decorationRenderers().asSequence()
                .map { it.occupiedSize() }
                .fold(size(), Size::plus)
        return DefaultLogArea(
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers(),
                        componentRenderer = DefaultLogAreaRenderer()),
                size = size,
                position = position(),
                componentStyleSet = componentStyleSet(),
                tileset = tileset(),
                textWrapMode = textWrap,
                logRowHistorySize = logRowHistorySize)
    }

    fun textWrap(textWrap: TextWrap) = also {
        if (textWrap == TextWrap.WRAP)
            throw NotImplementedError(TextWrap.WRAP.toString())
        this.textWrap = textWrap
    }

    fun logRowHistorySize(numberOfRows: Int) = also {
        logRowHistorySize = numberOfRows
    }

    override fun createCopy() = copy()

    companion object {

        fun newBuilder() = LogAreaBuilder()
    }
}
