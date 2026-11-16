package org.hexworks.zircon.api.component.theme

/**
 * Which looks of a component its builder set explicitly. A [ComponentTheme] supplies the
 * aspects that are not pinned and leaves pinned ones alone, on the first application and on
 * every switch, so `button { componentRenderer = Mine }` keeps `Mine` under any theme.
 */
public data class PinnedAspects(
    val componentRenderer: Boolean = false,
    val decorationRenderers: Boolean = false,
    val componentStyleSet: Boolean = false,
) {
    public companion object {
        public val NONE: PinnedAspects = PinnedAspects()
    }
}
