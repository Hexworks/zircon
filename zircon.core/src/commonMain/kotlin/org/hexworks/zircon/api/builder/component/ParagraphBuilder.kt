package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Paragraph
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.component.builder.base.ComponentWithTextBuilder
import org.hexworks.zircon.api.dsl.buildChildFor
import org.hexworks.zircon.api.graphics.TextWrap
import org.hexworks.zircon.internal.component.impl.DefaultParagraph
import org.hexworks.zircon.internal.component.renderer.DefaultParagraphRenderer
import org.hexworks.zircon.internal.component.renderer.TypingEffectPostProcessor
import org.hexworks.zircon.internal.dsl.ZirconDsl

@ZirconDsl
class ParagraphBuilder : ComponentWithTextBuilder<Paragraph>(
    initialRenderer = DefaultParagraphRenderer(),
    initialText = ""
) {

    var textWrap: TextWrap = TextWrap.WORD_WRAP
        set(value) {
            field = value
            componentRenderer = DefaultParagraphRenderer(value)
        }

    var typingEffectSpeedInMs: Long = 0

    override fun build(): Paragraph {
        props.postProcessors = props.postProcessors + if (typingEffectSpeedInMs > 0) {
            listOf(TypingEffectPostProcessor(typingEffectSpeedInMs))
        } else {
            listOf()
        }
        return DefaultParagraph(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
            initialText = text,
        ).attachListeners()
    }
}

/**
 * Creates a new [Paragraph] using the component builder DSL and returns it.
 */
fun buildParagraph(init: ParagraphBuilder.() -> Unit): Paragraph =
    ParagraphBuilder().apply(init).build()

/**
 * Creates a new [Paragraph] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [Paragraph].
 */
fun <T : BaseContainerBuilder<*>> T.paragraph(
    init: ParagraphBuilder.() -> Unit
): Paragraph = buildChildFor(this, ParagraphBuilder(), init)
