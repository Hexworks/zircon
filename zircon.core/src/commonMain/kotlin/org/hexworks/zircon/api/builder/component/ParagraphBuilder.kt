package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Paragraph
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.component.impl.DefaultParagraph
import org.hexworks.zircon.internal.component.renderer.DefaultParagraphRenderer
import org.hexworks.zircon.internal.component.renderer.TypingEffectPostProcessor
import org.hexworks.zircon.internal.component.withNewLinesStripped
import kotlin.jvm.JvmStatic
import kotlin.math.max

@Suppress("UNCHECKED_CAST")
@ZirconDsl
class ParagraphBuilder(
    internal var text: String = "",
    private var typingEffectSpeedInMs: Long = 0
) : BaseComponentBuilder<Paragraph, ParagraphBuilder>(DefaultParagraphRenderer()) {

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
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
            initialText = text,
        )
    }

    override fun createCopy() = newBuilder().withProps(props.copy())
        .withText(text)
        .withTypingEffect(typingEffectSpeedInMs)

    companion object {

        @JvmStatic
        fun newBuilder() = ParagraphBuilder()
    }
}
