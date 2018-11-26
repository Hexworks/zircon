package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Paragraph
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.component.renderer.impl.TypingEffectPostProcessor
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultParagraph
import org.hexworks.zircon.internal.component.renderer.DefaultParagraphRenderer

@Suppress("UNCHECKED_CAST")
data class ParagraphBuilder(
        internal var text: String = "",
        private var typingEffectSpeedInMs: Long = 0,
        private val commonComponentProperties: CommonComponentProperties<Paragraph> = CommonComponentProperties(
                componentRenderer = DefaultParagraphRenderer()))
    : BaseComponentBuilder<Paragraph, ParagraphBuilder>(commonComponentProperties) {

    override fun withTitle(title: String) = also { }

    fun withText(text: String) = also {
        this.text = text
    }

    fun withTypingEffect(typingEffectSpeedInMs: Long) = also {
        this.typingEffectSpeedInMs = typingEffectSpeedInMs
    }

    override fun build(): Paragraph {
        require(text.isNotBlank()) {
            "A Label can't be blank!"
        }
        fillMissingValues()
        // TODO: calculate size based on text size
        val finalSize = if (size.isUnknown()) {
            decorationRenderers.asSequence()
                    .map { it.occupiedSize }
                    .fold(Size.create(text.length, 1), Size::plus)
        } else {
            size
        }
        val postProcessors = if (typingEffectSpeedInMs > 0) {
            listOf(TypingEffectPostProcessor<Paragraph>(typingEffectSpeedInMs))
        } else {
            listOf()
        }
        return DefaultParagraph(
                componentMetadata = ComponentMetadata(
                        position = fixPosition(finalSize),
                        size = finalSize,
                        tileset = tileset,
                        componentStyleSet = componentStyleSet),
                text = text,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = commonComponentProperties.componentRenderer as ComponentRenderer<Paragraph>,
                        componentPostProcessors = postProcessors))
    }

    override fun createCopy() = copy(commonComponentProperties = commonComponentProperties.copy())

    companion object {

        fun newBuilder() = ParagraphBuilder()
    }
}
