package org.hexworks.zircon.api.component.theme

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.internal.component.theme.neobrutalism.NeobrutalismTheme

/**
 * Builds the Neobrutalism [ComponentTheme]. [mode] picks the palette ([ThemeMode.SYSTEM] asks
 * the platform); [palette] overrides it for a custom color set.
 */
@ZirconDsl
public class NeobrutalismBuilder : Builder<ComponentTheme> {

    public var mode: ThemeMode = ThemeMode.LIGHT
    public var palette: NeobrutalismPalette? = null

    override fun build(): ComponentTheme {
        val resolved = mode.resolve()
        return NeobrutalismTheme(
            mode = resolved,
            palette = palette ?: NeobrutalismPalette.forMode(resolved),
        )
    }
}
