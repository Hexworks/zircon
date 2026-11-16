package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.config.ZirconDefaults
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.dsl.AnyContainerBuilder
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.dsl.buildFragmentFor
import org.hexworks.zircon.api.fragment.Card
import org.hexworks.zircon.api.fragment.builder.ThemedFragmentBuilder
import org.hexworks.zircon.internal.fragment.impl.DefaultCard

/**
 * Builds a [Card]: [width] is the outer width, the height follows from the wrapped texts.
 */
@ZirconDsl
public class CardBuilder : ThemedFragmentBuilder<Card> {

    override var position: Position = Position.ZERO
    override var theme: ComponentTheme = ZirconDefaults.current.componentTheme
    public var width: Int = 40
    public var icon: Char? = null
    public var subtitle: String = ""
    public var title: String = ""
    public var body: String = ""

    override fun build(): Card {
        require(width >= 6) { "A card needs at least 6 columns" }
        return DefaultCard(
            width = width,
            icon = icon,
            initialSubtitle = subtitle,
            initialTitle = title,
            initialBody = body,
            theme = theme,
        ).also { it.root.moveTo(position) }
    }
}

public fun card(init: CardBuilder.() -> Unit): Card = CardBuilder().apply(init).build()

public fun AnyContainerBuilder.card(init: CardBuilder.() -> Unit): Card = buildFragmentFor(this, CardBuilder(), init)
