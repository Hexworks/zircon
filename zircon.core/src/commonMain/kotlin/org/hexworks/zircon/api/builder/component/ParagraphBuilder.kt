package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Paragraph
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.internal.component.impl.DefaultParagraph
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.DefaultParagraphRenderer
import org.hexworks.zircon.internal.component.renderer.TypingEffectPostProcessor
import org.hexworks.zircon.internal.component.withNewLinesStripped
import kotlin.math.max

@Suppress("UNCHECKED_CAST")
class ParagraphBuilder(
        internal var text: String = "",
        private var typingEffectSpeedInMs: Long = 0)
    : BaseComponentBuilder<Paragraph, ParagraphBuilder>(DefaultParagraphRenderer()) {

    fun withText(text: String) = also {
        this.text = text.withNewLinesStripped()
        contentSize = contentSize
                .withWidth(max(this.text.length, contentSize.width))
    }

    fun withTypingEffect(typingEffectSpeedInMs: Long) = also {
        this.typingEffectSpeedInMs = typingEffectSpeedInMs
    }

    override fun build(): Paragraph {
        val postProcessors = if (typingEffectSpeedInMs > 0) {
            listOf(TypingEffectPostProcessor<Paragraph>(typingEffectSpeedInMs))
        } else {
            listOf()
        }
        return DefaultParagraph(
                componentMetadata = ComponentMetadata(
                        relativePosition = position,
                        size = size,
                        tileset = tileset,
                        componentStyleSet = componentStyleSet),
                initialText = text,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = componentRenderer as ComponentRenderer<Paragraph>,
                        componentPostProcessors = postProcessors)).apply {
            colorTheme.map {
                theme = it
            }
        }
    }

    override fun createCopy() = newBuilder().withProps(props.copy())
            .withText(text)
            .withTypingEffect(typingEffectSpeedInMs)

    companion object {

        fun newBuilder() = ParagraphBuilder()
    }
}
