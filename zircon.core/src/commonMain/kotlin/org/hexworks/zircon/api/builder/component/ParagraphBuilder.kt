package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Paragraph
import org.hexworks.zircon.api.component.builder.base.ComponentWithTextBuilder
import org.hexworks.zircon.api.graphics.TextWrap
import org.hexworks.zircon.internal.component.impl.DefaultParagraph
import org.hexworks.zircon.internal.component.renderer.DefaultParagraphRenderer
import org.hexworks.zircon.internal.component.renderer.TypingEffectPostProcessor
import org.hexworks.zircon.internal.dsl.ZirconDsl
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
@ZirconDsl
class ParagraphBuilder : ComponentWithTextBuilder<Paragraph, ParagraphBuilder>(
    initialRenderer = DefaultParagraphRenderer(),
    initialText = ""
) {

    var textWrap: TextWrap = TextWrap.WORD_WRAP
        set(value) {
            field = value
            componentRenderer = DefaultParagraphRenderer(value)
        }

    var typingEffectSpeedInMs: Long = 0

    fun withTextWrap(textWrap: TextWrap) = also {
        this.textWrap = textWrap
    }

    fun withTypingEffect(typingEffectSpeedInMs: Long) = also {
        this.typingEffectSpeedInMs = typingEffectSpeedInMs
    }

    override fun build(): Paragraph {
        postProcessors = postProcessors + if (typingEffectSpeedInMs > 0) {
            listOf(TypingEffectPostProcessor(typingEffectSpeedInMs))
        } else {
            listOf()
        }
        return DefaultParagraph(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
            initialText = text,
        )
    }

    override fun createCopy() = newBuilder()
        .withProps(props.copy())
        .withText(text)
        .withTypingEffect(typingEffectSpeedInMs)

    companion object {

        @JvmStatic
        fun newBuilder() = ParagraphBuilder()
    }
}
