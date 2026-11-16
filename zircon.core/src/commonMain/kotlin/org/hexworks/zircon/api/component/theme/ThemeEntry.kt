package org.hexworks.zircon.api.component.theme

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.builder.base.DecorationsBuilder
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentPostProcessor
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.dsl.ZirconDsl

/**
 * What a [ComponentTheme] gives a [ComponentRole]: the renderer of the content, the
 * decorations around it, post processors over it, and the colors per state. A `null`
 * aspect means "whatever the next key of the lookup says", down to the default theme
 * (see [ComponentTheme.resolveEntry]).
 *
 * The renderer is typed loosely because an entry is selected by role, not type. A theme is
 * responsible for handing a `BUTTON` a renderer that can draw a button.
 */
public class ThemeEntry(
    public val componentRenderer: ComponentRenderer<out Component>? = null,
    public val decorationRenderers: List<ComponentDecorationRenderer>? = null,
    public val componentPostProcessors: List<ComponentPostProcessor<out Component>>? = null,
    public val componentStyleSet: ComponentStyleSet? = null,
) {
    /**
     * This entry with its `null` aspects taken from [base].
     */
    public infix fun over(base: ThemeEntry): ThemeEntry = ThemeEntry(
        componentRenderer = componentRenderer ?: base.componentRenderer,
        decorationRenderers = decorationRenderers ?: base.decorationRenderers,
        componentPostProcessors = componentPostProcessors ?: base.componentPostProcessors,
        componentStyleSet = componentStyleSet ?: base.componentStyleSet,
    )

    override fun toString(): String =
        "ThemeEntry(renderer=$componentRenderer, decorations=$decorationRenderers, styleSet=$componentStyleSet)"
}

@ZirconDsl
public class ThemeEntryBuilder : Builder<ThemeEntry> {

    public var componentRenderer: ComponentRenderer<out Component>? = null
    public var decorationRenderers: List<ComponentDecorationRenderer>? = null
    public var componentPostProcessors: List<ComponentPostProcessor<out Component>>? = null
    public var componentStyleSet: ComponentStyleSet? = null

    /**
     * The decorations, outermost first, the same order `decorations { }` takes on a builder.
     */
    public fun decorations(init: DecorationsBuilder.() -> Unit) {
        decorationRenderers = DecorationsBuilder().apply(init).build()
    }

    /**
     * No decorations at all (as opposed to `null`, which keeps the component's own).
     */
    public fun noDecorations() {
        decorationRenderers = emptyList()
    }

    override fun build(): ThemeEntry = ThemeEntry(
        componentRenderer = componentRenderer,
        decorationRenderers = decorationRenderers,
        componentPostProcessors = componentPostProcessors,
        componentStyleSet = componentStyleSet,
    )
}

public fun themeEntry(init: ThemeEntryBuilder.() -> Unit): ThemeEntry = ThemeEntryBuilder().apply(init).build()
